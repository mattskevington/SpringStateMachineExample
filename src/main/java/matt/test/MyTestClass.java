package matt.test;

import matt.test.enums.Events;
import matt.test.model.PizzaOrder;
import matt.test.service.PizzaOrderPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by mskevington on 10/02/2019.
 */
@Component
public class MyTestClass {

    @Autowired
    private PizzaOrderPersistenceService persistenceService;

    public void runTest() throws Exception {
        PizzaOrder newPizzaOrder = persistenceService.createMyObject("Test object one");
        System.out.println("Current object state: " + newPizzaOrder.getStateMachine().getState().getId());
        newPizzaOrder.sendEvent(Events.ACCEPT_ORDER);
        System.out.println("Current object state: " + newPizzaOrder.getStateMachine().getState().getId());
        persistenceService.saveMyObject(newPizzaOrder);

        //Try and get it back from the database
        PizzaOrder savedObject = persistenceService.getCurrentPizzaOrder(newPizzaOrder.getId());
        System.out.println("Current object state: " + savedObject.getStateMachine().getState().getId());
    }
}
