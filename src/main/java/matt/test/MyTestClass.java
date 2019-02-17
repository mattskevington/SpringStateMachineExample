package matt.test;

import matt.test.enums.Events;
import matt.test.model.MyObject;
import matt.test.service.MyObjectPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by mskevington on 10/02/2019.
 */
@Component
public class MyTestClass {

    @Autowired
    private MyObjectPersistenceService persistenceService;

    public void runTest() throws Exception {
        MyObject newMyObject = persistenceService.createMyObject("Test object one");
        System.out.println("Current object state: " + newMyObject.getStateMachine().getState().getId());
        newMyObject.sendEvent(Events.E1);
        System.out.println("Current object state: " + newMyObject.getStateMachine().getState().getId());
        persistenceService.saveMyObject(newMyObject);

        //Try and get it back from the database
        MyObject savedObject = persistenceService.getCurrentMyObject(newMyObject.getId());
        System.out.println("Current object state: " + savedObject.getStateMachine().getState().getId());
    }
}
