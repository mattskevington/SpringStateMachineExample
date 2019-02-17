package matt.test.service;

import matt.test.data.MyDbObjectHandler;
import matt.test.data.model.MyDbObject;
import matt.test.enums.Events;
import matt.test.enums.States;
import matt.test.model.MyObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.data.mongodb.MongoDbRepositoryStateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by mskevington on 09/02/2019.
 * This is a very crude service to retrieve and persist MyObject to the database.
 * It takes a MyObject object and extracts its StateContext when persisting to
 * the database.  When it's extracted from the database its state is restored
 * from the StateContext stored from the related ObjectState stored in the database.
 */
@Service
public class MyObjectPersistenceService {

    @Autowired
    private MyDbObjectHandler dbObjectHandler;

    @Autowired
    @Qualifier("enum-state-machine-factory")
    private StateMachineFactory<States, Events> enumMachineFactory;

    @Autowired
    private StateMachinePersister<States, Events, UUID> persister;






    public MyObject getCurrentMyObject(UUID id) throws Exception {
        return convert(dbObjectHandler.getMyDbObject(id));
    }

    public void saveMyObject(MyObject myObject) throws Exception {
        //StateMachinePersister<String, String, String> persist = new DefaultStateMachinePersister(objectStateService);
        //Extract State Context from machine
        persister.persist(myObject.getStateMachine(), myObject.getStateMachine().getUuid());
        //TODO: There could by race condition here between state save and object save.
        MyDbObject dbObject = new MyDbObject(myObject.getId(), myObject.getDetails(), myObject.getCurrentStateString());
        dbObjectHandler.saveMyObject(dbObject);
    }

    public MyObject createMyObject(String details) throws Exception {
       // StateMachinePersister<String, String, String> persist = new DefaultStateMachinePersister(objectStateService);
        StateMachine<States, Events> machine = enumMachineFactory.getStateMachine();
        machine.start();
        persister.persist(machine, machine.getUuid());
        MyDbObject dbObject = new MyDbObject(machine.getUuid(), details, machine.getState().getId().name());
        dbObjectHandler.addMyObject(dbObject);
        return new MyObject(machine, machine.getUuid(), details);
    }

    /**
     * Converts MyDbObject to MyObject and restores its state
     * @param dbObject
     * @return
     * @throws Exception
     */
    private MyObject convert(Optional<MyDbObject> dbObject) throws Exception {

        MyDbObject myDbObject = dbObject.get();

        StateMachine<States, Events> machine = enumMachineFactory.getStateMachine();
        persister.restore(machine, myDbObject.getId());

        MyObject object = new MyObject(machine,myDbObject.getId(), myDbObject.getDetails());
        return object;
    }


}
