package matt.test.data;

import org.springframework.statemachine.data.mongodb.MongoDbStateMachineRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by mskevington on 17/02/2019.
 */
@Repository
public interface MyMongoStateMachineRepo extends MongoDbStateMachineRepository {
}
