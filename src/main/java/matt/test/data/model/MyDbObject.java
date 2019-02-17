package matt.test.data.model;

import matt.test.enums.States;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document
public class MyDbObject {

    @Id
    private UUID id;

    private String details;
    public MyDbObject(String details){
        this.details = details;

    }

    @PersistenceConstructor
    public MyDbObject(UUID id, String details) {
        this.details = details;
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public UUID getId() {
        return id;
    }


}
