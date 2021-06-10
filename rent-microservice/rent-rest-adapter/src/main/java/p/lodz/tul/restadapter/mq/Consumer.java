package p.lodz.tul.restadapter.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.json.JSONObject;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import p.lodz.tul.applicationports.service.CreateCarUseCase;
import p.lodz.tul.applicationports.service.CreateClientUseCase;
import p.lodz.tul.applicationports.service.UpdateCarUseCase;
import p.lodz.tul.applicationports.service.UpdateClientUseCase;
import p.lodz.tul.restadapter.dto.CarDTO;
import p.lodz.tul.restadapter.dto.ClientDTO;
import p.lodz.tul.restadapter.mappers.CarMapper;
import p.lodz.tul.restadapter.mappers.ClientMapper;

@Component
@RequiredArgsConstructor
@Log
public class Consumer {
    private final CreateCarUseCase createCarUseCase;
    private final UpdateCarUseCase updateCarUseCase;
    private final CreateClientUseCase createClientUseCase;
    private final UpdateClientUseCase updateClientUseCase;
    private final ObjectMapper mapper;

    @RabbitListener(queues = ConsumerConfiguration.VEHICLE_QUEUE_NAME)
    public boolean receiveForVehicle(Object message, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String key) {
        log.info("received message");
        try {
            CarDTO car = mapper.readValue(((Message) message).getBody(), CarDTO.class);

            switch (key.substring(ConsumerConfiguration.VEHICLE_BASE_ROUTING_KEY.length() - 1)) {

                case "create" -> createCarUseCase.createCar(car.getBaseLoanPrice(), car.getVin());
                case "update" -> updateCarUseCase.updateCar(CarMapper.toCar(car));
                default -> {
                    return false;
                }
            }
            return true;
        } catch (Throwable throwable) {
            return false;
        }
    }

    @RabbitListener(queues = ConsumerConfiguration.ACCOUNT_QUEUE_NAME)
    public boolean receiveForAccount(Object message, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String key) {
        log.info("received message");
        try {
            JSONObject clientJson = new JSONObject(new String(((Message) message).getBody()));
            ClientDTO client = new ClientDTO(
                    clientJson.getString("email"),
                    clientJson.getString("login"),
                    clientJson.getBoolean("active"),
                    clientJson.getJSONObject("levelOfAccess").getDouble("amountOfMoney")
            );

            switch (key.substring(ConsumerConfiguration.ACCOUNT_BASE_ROUTING_KEY.length() - 1)) {

                case "create" -> createClientUseCase.createClient(client.getEmail(), client.getLogin(),
                        client.isActive(), client.getAmountOfMoney());
                case "update" -> updateClientUseCase.updateClient(ClientMapper.toClient(client));
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
