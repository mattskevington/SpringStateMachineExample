package matt.test;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.statemachine.StateMachineContext;

@Document
public class MyDbObject {

    @Id
    private String id;

    private String details;
    public MyDbObject(String details){
        this.details = details;

    }

    @PersistenceConstructor
    public MyDbObject(String id, String details) {
        this.details = details;
    }

    public void restoreState(States state){

    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getId() {
        return id;
    }


}
