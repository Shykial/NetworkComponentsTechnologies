package p.lodz.tul.RestAdapter;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication(scanBasePackages = {"p.lodz.tul"})
public class AccountRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountRestApplication.class, args);
    }
}
