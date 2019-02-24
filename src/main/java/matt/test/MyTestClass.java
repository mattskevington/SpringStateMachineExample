package matt.test;

import matt.test.enums.Events;
import matt.test.enums.PizzaOrderType;
import matt.test.enums.States;
import matt.test.model.PizzaOrder;
import matt.test.service.PizzaOrderPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.state.EnumState;
import org.springframework.statemachine.state.State;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by mskevington on 10/02/2019.
 */
@Component
public class MyTestClass {

    @Autowired
    private PizzaOrderPersistenceService persistenceService;

    /**
     * This is a sunny day test scenario that takes us through all the states and persists it at each step.
     * @throws Exception bad stuff happened.
     */
    public void sunnyDayTest() throws Exception {
        PizzaOrder newPizzaOrder = persistenceService.createNewPizzaOrder(PizzaOrderType.SINGLE);
        System.out.println("Current object state: " + newPizzaOrder.getStateMachine().getState().getId());
        System.out.println("Is Cancelled: " + newPizzaOrder.getCancelledState());
        persistenceService.savePizzaOrder(newPizzaOrder);

        newPizzaOrder.sendEvent(Events.RECEIVE_ORDER);
        System.out.println("Current object state: " + newPizzaOrder.getStateMachine().getState().getId());
        System.out.println("Is Cancelled: " + newPizzaOrder.getCancelledState());
        persistenceService.savePizzaOrder(newPizzaOrder);

        newPizzaOrder.sendEvent(Events.ACCEPT_ORDER);
        System.out.println("Current object state: " + newPizzaOrder.getStateMachine().getState().getId());
        System.out.println("Is Cancelled: " + newPizzaOrder.getCancelledState());
        persistenceService.savePizzaOrder(newPizzaOrder);

        newPizzaOrder.sendEvent(Events.START_ORDER);
        System.out.println("Current object state: " + newPizzaOrder.getStateMachine().getState().getId());
        System.out.println("Is Cancelled: " + newPizzaOrder.getCancelledState());
        persistenceService.savePizzaOrder(newPizzaOrder);

        newPizzaOrder.sendEvent(Events.COMPLETE_ORDER);
        System.out.println("Current object state: " + newPizzaOrder.getStateMachine().getState().getId());
        System.out.println("Is Cancelled: " + newPizzaOrder.getCancelledState());
        persistenceService.savePizzaOrder(newPizzaOrder);

        //Try and get it back from the database
        PizzaOrder savedOrder = persistenceService.getPizzaOrder(newPizzaOrder.getId());
        System.out.println("Final state after retrieving from database: " + savedOrder.getStateMachine().getState().getId());

    }

    /**
     * This shows a way of cancelling an order by using a sub-state CANCELLED.  However, by using sub-states
     * in this way you need to initiate it as NOT_CANCELLED as sub-states are intrinsically linked to it parent
     * state. Therefore, parent state cannot exist without and sub-state being set.  The other problem with
     * using this method of CANCELLED sub-state is that the EVENT you use has to be unique for that transition.
     * Therefore, you will have to have a CANCEL_STATE_A, CANCEL_STATE_B etc.
     *
     * @throws Exception
     */
    public void runCancelTestSubState() throws Exception {
        PizzaOrder newPizzaOrder = persistenceService.createNewPizzaOrder(PizzaOrderType.SINGLE);
        System.out.println("Current object state: " + newPizzaOrder.getStateMachine().getState().getId());
        newPizzaOrder.sendEvent(Events.RECEIVE_ORDER);
        System.out.println("Current object state: " + newPizzaOrder.getStateMachine().getState().getId());
        persistenceService.savePizzaOrder(newPizzaOrder);

        //Try and get it back from the database
        PizzaOrder savedObject = persistenceService.getPizzaOrder(newPizzaOrder.getId());
        System.out.println("Current object state: " + savedObject.getStateMachine().getState().getId());

        savedObject.sendEvent(Events.CANCEL_ORDER);
        persistenceService.savePizzaOrder(savedObject);

        System.out.println("Main State after cancellation is: " + savedObject.getStateMachine().getState().getId());
        System.out.println("Sub States after cancellation is: " + savedObject.getStateMachine().getState().getIds());
        //Try and change state to ACCEPT_ORDER now it's been cancelled.
        savedObject.sendEvent(Events.ACCEPT_ORDER);
        System.out.println("State after trying to move to ORDER_ACCEPTED is: " + savedObject.getStateMachine().getState().getId());
    }

    /**
     * This shows another way of Cancelling an order by using its Extended State to hold the Cancellation status.
     * We use an internalTransition to fire when a CANCEL_ORDER event is sent, this in turn runs the switchCancelAction()
     * Action to change the ExtendedState variable 'cancelled' to TRUE.
     * @throws Exception when something bad happens.
     */
    public void runCancelTestExtendedState() throws Exception {

        PizzaOrder newPizzaOrder = persistenceService.createNewPizzaOrder(PizzaOrderType.SINGLE);
        System.out.println("Current object state: " + newPizzaOrder.getStateMachine().getState().getId());
        newPizzaOrder.sendEvent(Events.RECEIVE_ORDER);
        System.out.println("Current object state: " + newPizzaOrder.getStateMachine().getState().getId());
        persistenceService.savePizzaOrder(newPizzaOrder);

        newPizzaOrder.sendEvent(Events.ACCEPT_ORDER);
        persistenceService.savePizzaOrder(newPizzaOrder);

        newPizzaOrder.sendEvent(Events.START_ORDER);
        persistenceService.savePizzaOrder(newPizzaOrder);

        newPizzaOrder.sendEvent(Events.CANCEL_ORDER);
        persistenceService.savePizzaOrder(newPizzaOrder);

        //Now try and move to COMPLETED; should not be allowed.
        System.out.println("Trying to move to COMPLETED STATE.");
        newPizzaOrder.sendEvent(Events.COMPLETE_ORDER);

        System.out.println("Main State after cancellation is: " + newPizzaOrder.getStateMachine().getState().getId());
        System.out.println("Sub States after cancellation is: " + newPizzaOrder.getStateMachine().getState().getIds());

    }
}
