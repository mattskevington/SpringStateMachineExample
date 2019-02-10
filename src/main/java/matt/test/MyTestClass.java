package matt.test;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by mskevington on 10/02/2019.
 */
public class MyTestClass {
    @Autowired
    private MyObjectPersistenceService persistenceService;

    public void runTest() throws Exception {
        MyObject newMyObject = persistenceService.createMyObject("1", "Test object one");
        System.out.println("Current object state: " + newMyObject.getStateMachine().getState());
        newMyObject.sendEvent("E1");
        System.out.println("Current object state: " + newMyObject.getStateMachine().getState());
        persistenceService.saveMyObject(newMyObject);

        //Try and get it back from the database
        MyObject savedObject = persistenceService.getCurrentMyObject("1");
        System.out.println("Current object state: " + savedObject.getStateMachine().getState());
    }
}
