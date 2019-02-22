package matt.test.data.model;

import matt.test.enums.PizzaOrderType;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

/**
 * This object is used as a dto for the PizzaOrder class.
 */
@Document
public class DbPizzaOrder {

    @Id
    private UUID id;

    private PizzaOrderType pizzaOrderType;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    private String state;

    @PersistenceConstructor
    public DbPizzaOrder(UUID id, PizzaOrderType pizzaOrderType, String state) {
        this.pizzaOrderType = pizzaOrderType;
        this.id = id;
        this.state = state;
    }

    public PizzaOrderType getPizzaOrderType() {
        return pizzaOrderType;
    }

    public void setPizzaOrderType(PizzaOrderType pizzaOrderType) {
        this.pizzaOrderType = pizzaOrderType;
    }

    public UUID getId() {
        return id;
    }


}
