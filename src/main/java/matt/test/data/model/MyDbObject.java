package matt.test.data.model;

import matt.test.enums.States;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

/**
 * This object is used as a dto for the MyObject class.
 */
@Document
public class MyDbObject {

    @Id
    private UUID id;

    private String details;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    private String state;

    @PersistenceConstructor
    public MyDbObject(UUID id, String details, String state) {
        this.details = details;
        this.id = id;
        this.state = state;
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
