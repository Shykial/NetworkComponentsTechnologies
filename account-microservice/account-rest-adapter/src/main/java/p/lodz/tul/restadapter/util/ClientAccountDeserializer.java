package p.lodz.tul.restadapter.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import p.lodz.tul.restadapter.dto.AccountDTO;
import p.lodz.tul.restadapter.dto.AddressDTO;
import p.lodz.tul.restadapter.dto.accesslevels.ClientDTO;

import javax.annotation.PostConstruct;
import java.io.Serializable;

@Component
public class ClientAccountDeserializer implements Serializable {

    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        objectMapper = new ObjectMapper();
    }

    public AccountDTO fromJson(JSONObject jsonData) {
        JSONObject levelOfAccessObject = jsonData.getJSONObject("levelOfAccess");
        AddressDTO addressDTO = extractAddressFromJson(levelOfAccessObject);

        ClientDTO clientDTO = new ClientDTO(
                levelOfAccessObject.getString("firstName"),
                levelOfAccessObject.getString("lastName"),
                addressDTO,
                levelOfAccessObject.getDouble("amountOfMoney")
        );

        return new AccountDTO(
                jsonData.getString("email"),
                jsonData.getString("login"),
                jsonData.getString("password"),
                jsonData.getBoolean("active"),
                clientDTO
        );
    }

    @SneakyThrows
    private AddressDTO extractAddressFromJson(JSONObject jsonData) {
        String addressKey;
        if (jsonData.has("address")) addressKey = "address";
        else if (jsonData.has("addressDTO")) addressKey = "addressDTO";
        else throw new Exception("No address key in provided JSON");

        return objectMapper.readValue(
                jsonData.getJSONObject(addressKey).toString(),
                AddressDTO.class
        );
    }
}
