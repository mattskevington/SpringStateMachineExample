package matt.test.service;

import matt.test.data.MyObjectStateRepository;
import matt.test.exception.StateException;
import matt.test.model.ObjectState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by mskevington on 10/02/2019.
 */
@Service
public class ObjectStateService implements StateMachinePersist<String, String, String> {

    @Autowired
    private MyObjectStateRepository repository;

    @Override
    public void write(StateMachineContext<String, String> context, String contextObj) throws Exception {
        //It should return the object after saving so we don't get out of sync with the db.
        repository.save(new ObjectState(context,contextObj));
    }

    @Override
    public StateMachineContext<String, String> read(String contextObj) throws Exception {
        Optional<ObjectState> state = repository.findById(contextObj);
        ObjectState objState = state.orElseThrow(() -> {return new StateException("State for " + contextObj + " not found in DB");});
        return objState.getContext();
    }
}
