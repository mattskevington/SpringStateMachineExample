package matt.test;

import org.springframework.statemachine.StateMachine;

import java.util.Random;

public class MyObject implements ObjectState{

    private int id;

    private StateMachine<States, Events> stateMachine;
    private String details;

    public MyObject(StateMachine stateMachine, String details){
        this.id = new Random().nextInt();

        this.stateMachine = stateMachine;
        this.details = details;
        this.stateMachine.start();

    }

    public void restoreState(States state){

    }

    public StateMachine<States, Events> getStateMachine(){
        return this.stateMachine;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getId() {
        return id;
    }

    @Override
    public void sendEvent(Events event) {
        this.stateMachine.sendEvent(event);
    }
}
