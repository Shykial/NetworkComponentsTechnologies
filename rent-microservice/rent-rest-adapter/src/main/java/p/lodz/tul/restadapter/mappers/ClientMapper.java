package p.lodz.tul.restadapter.mappers;

import p.lodz.tul.domainmodel.entities.Client;
import p.lodz.tul.restadapter.dto.ClientDTO;

public class ClientMapper {

    private ClientMapper() {
    }
    public static Client toClient(ClientDTO accountDTO) {
        return new Client(accountDTO.getEmail(), accountDTO.getLogin(), accountDTO.isActive(), accountDTO.getAmountOfMoney());
    }

    public static ClientDTO toClientDTO(Client account) {
        return new ClientDTO(account.getEmail(), account.getLogin(), account.isActive(), account.getAmountOfMoney());

    }
}
