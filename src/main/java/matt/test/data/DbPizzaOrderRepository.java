package matt.test.data;

import matt.test.data.model.DbPizzaOrder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Created by mskevington on 09/02/2019.
 */
@Repository
public interface DbPizzaOrderRepository extends MongoRepository<DbPizzaOrder, UUID> {

}
