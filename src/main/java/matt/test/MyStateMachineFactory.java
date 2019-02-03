package matt.test;

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
@EnableStateMachineFactory
public class MyStateMachineFactory extends EnumStateMachineConfigurerAdapter<States, Events> {
    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
         states.withStates().initial(States.SI, action())
                 .state(States.SI, action(), action())
                 .state(States.S1, action(), action())
                 .state(States.S2, action(), action());
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
            //return context.getStage().equals(StateContext.Stage.STATE_ENTRY);
        };

    }
}