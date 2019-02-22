package matt.test.model;

import matt.test.enums.Events;
import matt.test.enums.PizzaOrderType;
import matt.test.enums.States;
import org.springframework.statemachine.StateMachine;

import java.util.UUID;

/**
 * Created by mskevington on 09/02/2019.
 * This class is used as an example of associating a State Machine with an object.  The object's
 * state context is maintained in a database, however the StateMachine object itself is never
 * persisted to the database but recreated from the context when retrieved from the database.
 */
public class PizzaOrder implements ObjectStateEvent {

    private StateMachine<States, Events> stateMachine;
    private UUID id;
    private PizzaOrderType pizzaOrderType;

    public PizzaOrder(StateMachine<States, Events> stateMachine, UUID id, PizzaOrderType pizzaOrderType) {
        this.stateMachine = stateMachine;
        this.id = id;
        this.pizzaOrderType = pizzaOrderType;
    }

    public StateMachine<States, Events> getStateMachine() {
        return stateMachine;
    }

    public UUID getId() {
        return id;
    }

    public PizzaOrderType getPizzaOrderType() {
        return pizzaOrderType;
    }

    @Override
    public void sendEvent(Events event) {
        this.stateMachine.sendEvent(event);
    }

    public String getCurrentStateString(){
        return this.stateMachine.getState().getId().name();
    }
}
