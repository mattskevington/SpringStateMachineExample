package matt.test.data;

import matt.test.data.model.MyDbObject;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Created by mskevington on 09/02/2019.
 */
@Repository
public interface MyDbObjectRepository extends MongoRepository<MyDbObject, UUID> {

}
