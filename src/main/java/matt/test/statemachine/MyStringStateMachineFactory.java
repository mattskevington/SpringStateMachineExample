package matt.test.statemachine;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.HashMap;

@Configuration
@EnableStateMachineFactory(name="string-state-machine-factory")
public class MyStringStateMachineFactory extends StateMachineConfigurerAdapter<String, String> {
    @Override
    public void configure(StateMachineStateConfigurer<String, String> states) throws Exception {
         states.withStates().initial("SI", action())
                 .state("SI", action(), action())
                 .state("S1", action(), action())
                 .state("S2", action(), action());
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions) throws Exception {
        transitions
                .withExternal()
                .source("SI")
                .target("S1")
                .event("E1")
                .action(action(), errorAction())
                .guard(guard()).and()
                .withExternal()
                .source("S1")
                .target("S2")
                .event("E2")
                .guard(guard()).and()
                .withExternal()
                .source("S2")
                .target("S1")
                .event("E3")
                .guard(guard());
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<String, String> config) throws Exception {
        config.withConfiguration().autoStartup(false).listener(listener());
    }
    @Bean
    public StateMachineListener<String, String> listener() {
        return new StateMachineListenerAdapter<String, String>() {
            @Override
            public void stateChanged(State<String, String> from, State<String, String> to) {
                System.out.println("State change to " + to.getId());
            }
        };
    }

    @Bean
    Action<String, String> action() {
        return (context) -> System.out.println("Doing an action: " + context.getStage().name());
    }

    @Bean
    Action<String, String> errorAction() {
        return (context) -> System.out.println("Exception thrown doing an action: " + context.getException().getMessage());
    }

    @Bean
    Guard<String, String> guard() {
        return (context) -> {
            System.out.println("Guarding against: " + context.getStage().name());
            return true;
            //return context.getStage().equals(StateContext.Stage.STATE_ENTRY);
        };

    }

}
