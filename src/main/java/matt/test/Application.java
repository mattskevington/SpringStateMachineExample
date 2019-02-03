package matt.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.state.State;

import java.time.Instant;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private StateMachineFactory<States, Events> machineFactory;

    @Autowired
    private StateMachine<States, Events> stateMachine;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        long start = Instant.now().toEpochMilli();

        for (int x = 0; x < 100; x++) {
            StateMachine machine1 = machineFactory.getStateMachine();
            MyObject obj1 = new MyObject(machine1, "TestOne");
            System.out.println("Sending Factory Event 1");
            obj1.getStateMachine().sendEvent(Events.E1);
        }

        //System.out.println("Sending Event 1");
        //stateMachine.sendEvent(Events.E1);
        //System.out.println("Sending Event 2");
        //stateMachine.sendEvent(Events.E2);
        //System.out.println("Sending Event 3");
        //stateMachine.sendEvent(Events.E3);
        System.out.println("Run time: " + Instant.now().minusMillis(start).toEpochMilli());
    }
}
