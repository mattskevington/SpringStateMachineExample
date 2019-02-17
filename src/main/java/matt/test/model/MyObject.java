package matt.test.model;

import matt.test.enums.Events;
import matt.test.enums.States;
import org.springframework.statemachine.StateMachine;

import java.util.UUID;

/**
 * Created by mskevington on 09/02/2019.
 */
public class MyObject implements ObjectStateEvent {

    private StateMachine<States, Events> stateMachine;
    private UUID id;
    private String details;

    public MyObject(StateMachine<States, Events> stateMachine, UUID id, String details) {
        this.stateMachine = stateMachine;
        this.id = id;
        this.details = details;
    }

    public StateMachine<States, Events> getStateMachine() {
        return stateMachine;
    }

    public UUID getId() {
        return id;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public void sendEvent(Events event) {
        this.stateMachine.sendEvent(event);
    }
}
