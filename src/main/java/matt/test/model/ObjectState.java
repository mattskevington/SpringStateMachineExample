package matt.test.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.statemachine.StateMachineContext;

/**
 * Created by mskevington on 10/02/2019.
 */
@Document

public class ObjectState {

    @Id
    private String id;

    private StateMachineContext<String, String> context;
    @PersistenceConstructor
    public ObjectState(StateMachineContext<String, String> context, String id){
        this.id = id;
        this.context = context;
    }

    public StateMachineContext<String, String> getContext() {
        return context;
    }

    public String getId() {
        return id;
    }
}
