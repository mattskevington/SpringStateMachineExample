package matt.test.data;

import matt.test.data.model.DbPizzaOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Handler for performing persistence of DbPizzaOrder objects.
 */
@Component
public class DbPizzaOrderHandler {

    @Autowired
    private DbPizzaOrderRepository dbPizzaOrderRepository;

    /**
     * Add a new {@link DbPizzaOrder} to the database.
     * @param dbPizzaOrder The new DbPizzaOrder to persist.
     * @return Persisted DbPizzaOrder.
     */
    public DbPizzaOrder addDbPizzaOrder(DbPizzaOrder dbPizzaOrder){
        return dbPizzaOrderRepository.insert(dbPizzaOrder);
    }

    /**
     * Update an existing {@link matt.test.data.model.DbPizzaOrder}.
     * @param dbPizzaOrder The DbPizzaOrder to update.
     * @return Current DbPizzaOrder.
     */
    public DbPizzaOrder saveDbPizzaOrder(DbPizzaOrder dbPizzaOrder){
        return dbPizzaOrderRepository.save(dbPizzaOrder);
    }

    /**
     * Get an existing {@link matt.test.data.model.DbPizzaOrder} from the database.
     * @param id The unique ID of the DbPizzaOrder.
     * @return Optional containing either the DbPizzaOrder that was found or an Optional#empty.
     */
    public Optional<DbPizzaOrder> getDbPizzaOrder(UUID id){
        return dbPizzaOrderRepository.findById(id);
    }
}
