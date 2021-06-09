package p.lodz.tul.mappers.accessLevels;

import p.lodz.tul.DomainModel.Entities.accessLevels.Client;
import p.lodz.tul.dbEntities.accessLevels.ClientEnt;
import p.lodz.tul.mappers.AddressMapper;

public class ClientMapper {

    private ClientMapper() {
    }

    public static Client toClient(ClientEnt clientEnt) {
        return new Client(clientEnt.getFirstName(), clientEnt.getLastName(), AddressMapper.toAddress(clientEnt.getAddressEnt()), clientEnt.getAmountOfMoney());

    }

    public static ClientEnt toClientEnt(Client client) {
        return new ClientEnt(client.getFirstName(), client.getLastName(), AddressMapper.toAddressEnt(client.getAddress()), client.getAmountOfMoney());
    }
}
