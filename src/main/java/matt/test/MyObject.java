package matt.test;

import org.springframework.statemachine.StateMachine;

/**
 * Created by mskevington on 09/02/2019.
 */
public class MyObject implements ObjectStateEvent {

    private StateMachine<String, String> stateMachine;
    private String id;
    private String details;

    public MyObject(StateMachine<String, String> stateMachine, String id, String details) {
        this.stateMachine = stateMachine;
        this.id = id;
        this.details = details;
    }

    public StateMachine<String, String> getStateMachine() {
        return stateMachine;
    }

    public String getId() {
        return id;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public void sendEvent(String event) {
        this.stateMachine.sendEvent(event);
    }
}
