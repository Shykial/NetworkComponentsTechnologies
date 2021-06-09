package p.lodz.tul;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication(scanBasePackages = {"p.lodz.tul"})
public class VehicleRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(VehicleRestApplication.class, args);
    }
}
