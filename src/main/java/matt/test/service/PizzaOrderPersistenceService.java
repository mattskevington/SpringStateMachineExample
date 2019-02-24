package matt.test.service;

import matt.test.data.DbPizzaOrderHandler;
import matt.test.data.model.DbPizzaOrder;
import matt.test.enums.Events;
import matt.test.enums.PizzaOrderType;
import matt.test.enums.States;
import matt.test.model.PizzaOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by mskevington on 09/02/2019.
 * This is a very crude service to retrieve and persist PizzaOrder to the database.
 * It takes a PizzaOrder and extracts its StateContext when persisting to
 * the database.  When it's extracted from the database its state is restored
 * from the StateContext stored from the related ObjectState stored in the database.
 */
@Service
public class PizzaOrderPersistenceService {

    @Autowired
    private DbPizzaOrderHandler dbObjectHandler;

    @Autowired
    @Qualifier("enum-state-machine-factory")
    private StateMachineFactory<States, Events> enumMachineFactory;

    @Autowired
    private StateMachinePersister<States, Events, UUID> persister;


    public PizzaOrder getPizzaOrder(UUID id) throws Exception {
        return convert(dbObjectHandler.getDbPizzaOrder(id));
    }

    /**
     * Save a PizzaOrder to the database and persist its state.
     * @param pizzaOrder The PizzaOrder to save.
     * @throws Exception Problem persisting state to the database.
     */
    public void savePizzaOrder(PizzaOrder pizzaOrder) throws Exception {
        //Extract State Context from machine
        persister.persist(pizzaOrder.getStateMachine(), pizzaOrder.getStateMachine().getUuid());
        //TODO: There could by race condition here between state save and object save.
        DbPizzaOrder dbPizzaOrder = new DbPizzaOrder(pizzaOrder.getId(), pizzaOrder.getPizzaOrderType(), pizzaOrder.getCurrentStateString());
        dbObjectHandler.saveDbPizzaOrder(dbPizzaOrder);
    }

    /**
     * Create a new PizzaOrder and saves its state to the database.
     * @param pizzaOrderType The type of order to create.
     * @return a new PizzaOrder with its own state machine.
     * @throws Exception
     */
    public PizzaOrder createNewPizzaOrder(PizzaOrderType pizzaOrderType) throws Exception {
        StateMachine<States, Events> machine = enumMachineFactory.getStateMachine();
        machine.start();
        persister.persist(machine, machine.getUuid());
        DbPizzaOrder dbObject = new DbPizzaOrder(machine.getUuid(), pizzaOrderType, machine.getState().getIds().toString());
        dbObjectHandler.addDbPizzaOrder(dbObject);
        return new PizzaOrder(machine, machine.getUuid(), pizzaOrderType);
    }

    /**
     * Converts MyDbObject to MyObject and restores its state
     * @param optionalDbPizzaOrder The DbPizzaOrder to convert.
     * @return A new PizzaOrder constructed from the DbPizzaOrder obtained from the database.
     * @throws Exception If there was an error restoring the StateMachine.
     */
    private PizzaOrder convert(Optional<DbPizzaOrder> optionalDbPizzaOrder) throws Exception {

        //TODO: Check if we have DbPizzaOrder before getting it.
        DbPizzaOrder dbPizzaOrder = optionalDbPizzaOrder.get();
        //Create a new StateMachine
        StateMachine<States, Events> machine = enumMachineFactory.getStateMachine();
        //Restore the PizzaOrder state to created StateMachine.
        persister.restore(machine, dbPizzaOrder.getId());
        //Create a new PizzaOrder adding the restored StateMachine in the process.
        return new PizzaOrder(machine, dbPizzaOrder.getId(), dbPizzaOrder.getPizzaOrderType());

    }


}
