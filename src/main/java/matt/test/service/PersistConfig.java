package matt.test.service;


import matt.test.enums.Events;
import matt.test.enums.States;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.data.mongodb.MongoDbPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.mongodb.MongoDbStateMachineRepository;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by mskevington on 10/02/2019.
 */
@Configuration
public class PersistConfig {

    @Bean
    public StateMachineRuntimePersister<States, Events, UUID> mongoPersist(
            MongoDbStateMachineRepository mongoRepository) {

        return new MongoDbPersistingStateMachineInterceptor<States, Events, UUID>(mongoRepository);
    }

    @Bean
    public StateMachinePersister<States, Events, UUID> persister(
            StateMachinePersist<States, Events, UUID> defaultPersist) {

        return new DefaultStateMachinePersister<>(defaultPersist);
    }


/*    @Override
    public void write(StateMachineContext<String, String> context, String contextObj) throws Exception {
        //It should return the object after saving so we don't get out of sync with the db.
        objectStateHandler.saveObjectState(new ObjectState(context,contextObj));
    }

    @Override
    public StateMachineContext<String, String> read(String contextObj) throws Exception {
        Optional<ObjectState> state = objectStateHandler.getObjectState(contextObj);
        ObjectState objState = state.orElseThrow(() -> {return new StateException("State for " + contextObj + " not found in DB");});
        return objState.getContext();
    }*/
}
