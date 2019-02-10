package matt.test.data;

import matt.test.MyDbObject;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by mskevington on 09/02/2019.
 */
@Repository
public interface MyObjectRepository extends MongoRepository<MyDbObject, String> {

}
