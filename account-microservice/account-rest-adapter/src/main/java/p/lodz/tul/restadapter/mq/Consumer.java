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
import p.lodz.tul.ApplicationPorts.service.GetAccountsUseCase;
import p.lodz.tul.ApplicationPorts.service.UpdateAccountUseCase;
import p.lodz.tul.domainmodel.entities.Account;
import p.lodz.tul.domainmodel.entities.accesslevels.Client;

@Component
@RequiredArgsConstructor
@Log
public class Consumer {
    private final UpdateAccountUseCase updateAccountUseCase;
    private final GetAccountsUseCase getAccountsUseCase;

    @RabbitListener(queues = ConsumerConfiguration.RENT_QUEUE_NAME)
    public boolean receiveForVehicle(Object message, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String key) {
        log.info("received message");
        try {
            JSONObject clientJson = new JSONObject(new String(((Message) message).getBody()));
            Account account = getAccountsUseCase.getAccount(clientJson.getString("login"));

            if ("updateClient".equals(key.substring(ConsumerConfiguration.RENT_BASE_ROUTING_KEY.length() - 1))) {
                Client client = (Client) account.getLevelOfAccess();
                client.setAmountOfMoney(clientJson.getDouble("amountOfMoney"));

                updateAccountUseCase.updateAccount(account);
            } else {
                return false;
            }
            return true;
        } catch (Throwable throwable) {
            return false;
        }
    }
}
