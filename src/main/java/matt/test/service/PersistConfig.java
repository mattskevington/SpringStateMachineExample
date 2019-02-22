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
 * This class wires calls to persist a StateMachine context into an insert into a Mongo database.<br>
 * <b>NOTE:</b> The MongoDbStateMachineRepository is autowired from MongoPizzaOrderStateMachineRepo.
 */
@Configuration
public class PersistConfig {

    @Bean
    public StateMachineRuntimePersister<States, Events, UUID> mongoPersist (
            MongoDbStateMachineRepository mongoRepository) {

        return new MongoDbPersistingStateMachineInterceptor<States, Events, UUID>(mongoRepository);
    }

    /**
     * Uses the mongoPersist bean to handle the actual persisting to the database.
     *
     * @param defaultPersist the mechanism that handles the persistence to storage.
     * @return A Persister that handles persisting StateMachine context to a persistence storage.
     */
    @Bean
    public StateMachinePersister<States, Events, UUID> persister (
            StateMachinePersist<States, Events, UUID> defaultPersist) {

        return new DefaultStateMachinePersister<>(defaultPersist);
    }

}
