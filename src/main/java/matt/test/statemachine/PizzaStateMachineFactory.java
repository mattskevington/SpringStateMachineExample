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

@Configuration
@EnableStateMachineFactory(name = "enum-state-machine-factory")
public class PizzaStateMachineFactory extends EnumStateMachineConfigurerAdapter<States, Events> {
    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
         states.withStates().initial(States.NEW, action())
                 .state(States.RECEIVED, action(), action())
                 .state(States.ORDER_ACCEPTED, action(), action())
                 .state(States.IN_PROGRESS, action(), action())
                 .state(States.COMPLETE, action(), action());
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
                .withExternal()
                    .source(States.NEW)
                    .target(States.RECEIVED)
                    .event(Events.RECEIVE_ORDER)
                    .action(action(), errorAction())
                    .guard(guard()).and()
                .withExternal()
                    .source(States.RECEIVED)
                    .target(States.ORDER_ACCEPTED)
                    .target(States.ORDER_REJECTED)
                    .event(Events.ACCEPT_ORDER)
                    .guard(guard()).and()
                .withExternal()
                    .source(States.ORDER_ACCEPTED)
                    .target(States.IN_PROGRESS)
                    .event(Events.START_ORDER)
                    .guard(guard()).and()
                .withExternal()
                    .source(States.IN_PROGRESS)
                    .target(States.COMPLETE)
                    .event(Events.COMPLETE_ORDER);
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
    Action<States, Events> action() {
        return (context) -> System.out.println("Doing an action: " + context.getStage().name());
    }

    @Bean
    Action<States, Events> errorAction() {
        return (context) -> System.out.println("Exception thrown doing an action: " + context.getException().getMessage());
    }

    @Bean
    Guard<States, Events> guard() {
        return (context) -> {
            System.out.println("Guarding against: " + context.getStage().name());
            return true;
        };

    }
}
