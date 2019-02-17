package matt.test.statemachine;

import matt.test.enums.Events;
import matt.test.enums.States;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.action.StateDoActionPolicy;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;


@Configuration
@EnableStateMachine(contextEvents = false)
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {
    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {

        config.withConfiguration().autoStartup(true).listener(listener()).stateDoActionPolicy(StateDoActionPolicy.IMMEDIATE_CANCEL);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {

        transitions
                .withExternal()
                    .source(States.SI)
                    .target(States.S1)
                    .event(Events.E1)
                    .action(action(), errorAction())
                    .guard(guard()).and()
                .withExternal()
                    .source(States.S1)
                    .target(States.S2)
                    .event(Events.E2)
                    .guard(guard()).and()
                .withExternal()
                    .source(States.S2)
                    .target(States.S1)
                    .event(Events.E3)
                    .guard(guard());
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {

        //Action<States, Events> action = (context) -> System.out.println("Doing an action: " + context.getEvent().toString());
        states.withStates()
        .initial(States.SI, action())
                .state(States.SI, action(), action())
        .state(States.S1, action(), action())
                .state(States.S2, action(), action());

    }

    @Bean
    Action<States, Events> errorAction() {
        return (context) -> System.out.println("Exception thrown doing an action: " + context.getException().getMessage());
    }

    @Bean
    Action<States, Events> action() {
        return (context) -> System.out.println("Doing an action: " + context.getStage().name());
    }


    @Bean
    Guard<States, Events> guard() {
         return (context) -> {
             System.out.println("Guarding against: " + context.getStage().name());
             return true;
             //return context.getStage().equals(StateContext.Stage.STATE_ENTRY);
         };

    }

    @Bean
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<States, Events>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
                System.out.println("State change to " + to.getId());
            }

            @Override
            public void stateEntered(State<States, Events> state) {

            }

            @Override
            public void stateExited(State<States, Events> state) {

            }

            @Override
            public void eventNotAccepted(Message<Events> event) {

            }

            @Override
            public void transition(Transition<States, Events> transition) {

            }

            @Override
            public void transitionStarted(Transition<States, Events> transition) {

            }

            @Override
            public void transitionEnded(Transition<States, Events> transition) {

            }

            @Override
            public void stateMachineStarted(StateMachine<States, Events> stateMachine) {

            }

            @Override
            public void stateMachineStopped(StateMachine<States, Events> stateMachine) {

            }

            @Override
            public void stateMachineError(StateMachine<States, Events> stateMachine, Exception exception) {

            }

            @Override
            public void extendedStateChanged(Object key, Object value) {

            }

            @Override
            public void stateContext(StateContext<States, Events> stateContext) {

            }
        };
    }
}
