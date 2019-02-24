package matt.test.app;

import matt.test.MyTestClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.query.Param;

import java.time.Instant;

@SpringBootApplication(scanBasePackages = "matt.test")
@EnableMongoRepositories("matt.test.data")
@Configuration
public class Application implements CommandLineRunner {


    @Autowired
    private MyTestClass myTestClass;


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        long start = Instant.now().toEpochMilli();



        myTestClass.runCancelTestExtendedState();
//        myTestClass.runCancelTestSubState();
//        myTestClass.sunnyDayTest();
        System.out.println("Run time: " + Instant.now().minusMillis(start).toEpochMilli());
    }
}
