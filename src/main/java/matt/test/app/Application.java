package matt.test.app;

import matt.test.MyTestClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.time.Instant;

@SpringBootApplication(scanBasePackages = "matt.test")
@EnableMongoRepositories("matt.test.data")
@Configuration
public class Application implements CommandLineRunner {

//    @Autowired
//    private StateMachineFactory<States, Events> machineFactory;

 /*   @Autowired
    private StateMachineFactory<String, String> stringMachineFactory;

    @Autowired
    private StateMachine<States, Events> stateMachine;*/

    @Autowired
    private MyTestClass myTestClass;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        long start = Instant.now().toEpochMilli();

        //Let's try and persist the state and then restore it.
/*        InMemoryStateMachinePersist persistMachine = new InMemoryStateMachinePersist();
        StateMachinePersister<String, String, String> persist = new DefaultStateMachinePersister<>(persistMachine);
        StateMachine<String, String> machine1 = stringMachineFactory.getStateMachine("m1");

        StateMachine<String, String> machine2 = stringMachineFactory.getStateMachine("m2");
        //Start machine1 and send an event
        machine1.start();
        machine1.sendEvent("E1");
        assert machine1.getState().equals("S1");
        //Now persist the machine
        System.out.println("Persisting Machine1");
        persist.persist(machine1, "ID1");
        //Now try and restore to machine2; hopefully without triggering events.
        System.out.println("Restoring to Machine2");
        persist.restore(machine2, "ID1");
        System.out.println("Sending Event E2 to Machine2");
        machine2.sendEvent("E2");
        //Check that it has state of S1
        assert machine2.getState().equals("S2");*/

        //Test for setting object state
//        for (int x = 0; x < 100; x++) {
//            StateMachine machine1 = machineFactory.getStateMachine();
//
//            MyObject obj1 = new MyObject(machine1, "TestOne");
//            System.out.println("Sending Factory Event 1");
//            obj1.sendEvent(Events.E1);
//        }
        //Normal setting of application state.
        //System.out.println("Sending Event 1");
        //stateMachine.sendEvent(Events.E1);
        //System.out.println("Sending Event 2");
        //stateMachine.sendEvent(Events.E2);
        //System.out.println("Sending Event 3");
        //stateMachine.sendEvent(Events.E3);

        myTestClass.runTest();
        System.out.println("Run time: " + Instant.now().minusMillis(start).toEpochMilli());
    }
}
