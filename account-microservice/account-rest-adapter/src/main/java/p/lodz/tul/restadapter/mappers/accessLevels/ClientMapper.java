package p.lodz.tul.restadapter.mappers.accessLevels;

import p.lodz.tul.DomainModel.Entities.accessLevels.Client;
import p.lodz.tul.restadapter.dto.accessLevels.ClientDTO;
import p.lodz.tul.restadapter.mappers.AddressMapper;

public class ClientMapper {

    private ClientMapper() {
    }

    public static Client toClient(ClientDTO clientDTO) {
        return new Client(clientDTO.getFirstName(), clientDTO.getLastName(), AddressMapper.toAddress(clientDTO.getAddressDTO()), clientDTO.getAmountOfMoney());

    }

    public static ClientDTO toClientDTO(Client client) {
        return new ClientDTO(client.getFirstName(), client.getLastName(), AddressMapper.toAddressDTO(client.getAddress()), client.getAmountOfMoney());
    }
}
