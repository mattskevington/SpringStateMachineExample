package matt.test.data;

import matt.test.data.model.MyDbObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by mskevington on 11/02/2019.
 */
@Component
public class MyDbObjectHandler {

    @Autowired
    private MyDbObjectRepository myObjectRepository;

    public MyDbObject addMyObject(MyDbObject myObject){
        return myObjectRepository.insert(myObject);
    }

    public MyDbObject saveMyObject(MyDbObject myDbObject){
        return myObjectRepository.save(myDbObject);
    }

    public Optional<MyDbObject> getMyDbObject(UUID id){
        return myObjectRepository.findById(id);
    }
}
