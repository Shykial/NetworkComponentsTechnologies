package config;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class CarRentApplication {
    public static void main(String[] args) {
        System.out.println("Hello!");
    }
}
