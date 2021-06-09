package p.lodz.tul.mappers.accessLevels;

import p.lodz.tul.domainmodel.entities.accesslevels.Client;
import p.lodz.tul.dbentities.accesslevels.ClientEnt;
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
