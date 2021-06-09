package p.lodz.tul.restadapter;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication(scanBasePackages = {"p.lodz.tul"})
public class RentRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(RentRestApplication.class, args);
    }
}
