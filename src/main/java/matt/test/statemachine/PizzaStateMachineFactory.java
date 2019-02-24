package matt.test.statemachine;

import matt.test.enums.Events;
import matt.test.enums.States;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.Map;

@Configuration
@EnableStateMachineFactory(name = "enum-state-machine-factory")
public class PizzaStateMachineFactory extends EnumStateMachineConfigurerAdapter<States, Events> {
    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        //Defines states and any entry exit action associated with it.
         states.withStates().initial(States.NEW, switchCancelAction())
                 .state(States.RECEIVED, entryAction(), exitAction())
                 .and()
                 //With sub states you need to set an initial sub state.
                    .withStates()
                    .parent(States.RECEIVED)
                    .initial(States.NOT_CANCELLED)
                    .state(States.CANCELLED)
                    .and()
                 .withStates()
                 .state(States.ORDER_ACCEPTED, entryAction(), exitAction()).and()
                    .withStates()
                    .parent(States.ORDER_ACCEPTED)
                    .initial(States.NOT_CANCELLED)
                    .state(States.CANCELLED)
                    .and()
                 .withStates()
                 .state(States.IN_PROGRESS, entryAction(), exitAction())
                 .state(States.COMPLETE, entryAction(), exitAction());
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        //The first two guards use the Sub-state CANCELLED to guard against cancelled.
        //The rest use the Extended State 'cancelled' property to guard against cancelled.
        transitions
                .withExternal()
                    .source(States.NEW)
                    .target(States.RECEIVED)
                    .event(Events.RECEIVE_ORDER)
                    .action(transitionAction(), errorAction())
                    .guard(guard()).and()
                .withExternal()
                    .source(States.RECEIVED)
                    .target(States.CANCELLED)
                    .event(Events.CANCEL_ORDER)
                    .guard(guard())
                    .action(transitionAction()).and()
                .withExternal()
                    .source(States.RECEIVED)
                    .target(States.ORDER_ACCEPTED)
                    .event(Events.ACCEPT_ORDER)
                    .guard(guard())
                    .action(transitionAction()).and()
                .withExternal()
                    .source(States.ORDER_ACCEPTED)
                    .target(States.IN_PROGRESS)
                    .event(Events.START_ORDER)
                    .guard(cancelGuard()).action(transitionAction()).and()
                .withExternal()
                    .source(States.IN_PROGRESS)
                    .target(States.COMPLETE)
                    .event(Events.COMPLETE_ORDER)
                    .guard(cancelGuard()).action(transitionAction()).and()
                .withInternal()
                    .source(States.ORDER_ACCEPTED)
                    .event(Events.CANCEL_ORDER)
                    .action(switchCancelAction()).and()
                .withInternal()
                    .source(States.IN_PROGRESS)
                    .event(Events.CANCEL_ORDER)
                    .action(switchCancelAction());
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        config.withConfiguration().autoStartup(false).listener(listener());
    }
    @Bean
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<States, Events>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
                System.out.println("State change to " + to.getId());
            }
        };
    }

    @Bean
         Action<States, Events> transitionAction() {
        return (context) -> System.out.println("Doing a transition action: " + context.getStage().name());
    }

    @Bean
    Action<States, Events> entryAction() {
        return (context) -> System.out.println("Doing an entry action: " + context.getStage().name());
    }

    @Bean
    Action<States, Events> exitAction() {
        return (context) -> System.out.println("Doing an exit action: " + context.getStage().name());
    }

    @Bean
    Action<States, Events> errorAction() {
        return (context) -> System.out.println("Exception thrown doing an action: " + context.getException().getMessage());
    }

    @Bean
    Action<States, Events> switchCancelAction() {
        return (context) -> {
            Map<Object, Object> variables = context.getExtendedState().getVariables();
            Boolean cancelled = context.getExtendedState().get("cancelled", Boolean.class);
            if (cancelled == null){
                System.out.println("Initialising cancelled state to FALSE");
                variables.put("cancelled", Boolean.FALSE);
            } else if (!cancelled) {
                System.out.println("Switching cancelled to TRUE");
                variables.put("cancelled", Boolean.TRUE);
            }
        };
        //return (context) -> System.out.println("Doing an init action: " +  context.getStage().name());
    }

    @Bean
    Guard<States, Events> guard() {
        return (context) -> {
            System.out.println("Guarding against: " + context.getStage().name());
            context.getTarget().getIds().forEach(s -> System.out.println("Guard State:" + s.name()));
            if(context.getStateMachine().getState().getIds().contains(States.CANCELLED)){
                System.out.println("STATE CANNOT BE CHANGED AS IT IS CANCELLED!!!!");
                return false;
            }
            return true;
        };

    }

    @Bean
    Guard<States, Events> cancelGuard() {
        return (context) -> {
            System.out.println("Guarding against cancelled extended state.");
            Boolean cancelled = context.getExtendedState().get("cancelled", Boolean.class);
            context.getTarget().getIds().forEach(s -> System.out.println("Guard State:" + s.name()));
            return !cancelled;
        };

    }
}
