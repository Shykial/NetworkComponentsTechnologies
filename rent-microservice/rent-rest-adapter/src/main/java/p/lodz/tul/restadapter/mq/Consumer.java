package p.lodz.tul.restadapter.mq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import p.lodz.tul.applicationports.service.CreateCarUseCase;
import p.lodz.tul.applicationports.service.UpdateCarUseCase;
import p.lodz.tul.restadapter.dto.CarDTO;
import p.lodz.tul.restadapter.mappers.CarMapper;

@Component
@RequiredArgsConstructor
public class Consumer {
    private final CreateCarUseCase createCarUseCase;
    private final UpdateCarUseCase updateCarUseCase;

    @RabbitListener()
    public boolean receive(Object object, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String key) {
        try {
            switch (key.substring(ServerConfiguration.BASE_ROUTING_KEY.length())) {
                case "create" -> {
                    CarDTO car = (CarDTO) object;
                    createCarUseCase.createCar(car.getBaseLoanPrice(), car.getVin());
                }
                case "update" -> {
                    CarDTO car = (CarDTO) object;
                    updateCarUseCase.updateCar(CarMapper.toCar(car));
                }
                default -> {
                    return false;
                }

            }
            return true;
        } catch (Throwable throwable) {
            return false;
        }
    }
}
