package matt.test;

import matt.test.data.MyObjectRepository;
import matt.test.data.MyObjectStateRepository;
import matt.test.service.ObjectStateService;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    private MyObjectRepository repository;

    @Autowired
    private ObjectStateService stateService;

    @Autowired
    private StateMachineFactory<String, String> stringMachineFactory;

    private StateMachinePersister<String, String, String> persist;


    public MyObjectPersistenceService() {
        //InMemoryStateMachinePersist persistMachine = new InMemoryStateMachinePersist();

        this.persist = new DefaultStateMachinePersister<>(stateService);
    }

    public MyObject getCurrentMyObject(String id) throws Exception {
        return convert(repository.findById(id));
    }

    public void saveMyObject(MyObject myObject) throws Exception {
        //Extract State Context from machine
        persist.persist(myObject.getStateMachine(), myObject.getId());
        //TODO: There could by race condition here between state save and object save.
        MyDbObject dbObject = new MyDbObject(myObject.getId(), myObject.getDetails());
        repository.save(dbObject);
    }

    public MyObject createMyObject(String id, String details) throws Exception {
        StateMachine<String, String> machine = stringMachineFactory.getStateMachine();
        machine.start();
        persist.persist(machine, id);
        MyDbObject dbObject = new MyDbObject(details, id);
        repository.insert(dbObject);
        return new MyObject(machine, id, details);
    }
    private MyObject convert(Optional<MyDbObject> dbObject) throws Exception {
        MyDbObject myDbObject = dbObject.get();

        StateMachine<String, String> machine = stringMachineFactory.getStateMachine();
        persist.restore(machine, myDbObject.getId());

        MyObject object = new MyObject(machine,myDbObject.getId(), myDbObject.getDetails());
        return object;
    }


}
