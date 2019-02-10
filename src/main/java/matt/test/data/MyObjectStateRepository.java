package matt.test.data;

import matt.test.model.ObjectState;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by mskevington on 10/02/2019.
 */
@Repository
public interface MyObjectStateRepository extends MongoRepository<ObjectState, String> {

}
