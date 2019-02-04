package matt.test;

import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;

import java.util.HashMap;

/**
 * Created by mskevington on 04/02/2019.
 */
public class InMemoryStateMachinePersist implements StateMachinePersist<String, String, String> {

    private final HashMap<String, StateMachineContext<String, String>> contexts = new HashMap<>();

    @Override
    public void write(StateMachineContext<String, String> context, String contextObj) throws Exception {
        contexts.put(contextObj, context);
    }

    @Override
    public StateMachineContext<String, String> read(String contextObj) throws Exception {
        return contexts.get(contextObj);
    }
}
