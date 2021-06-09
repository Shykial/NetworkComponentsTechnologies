package p.lodz.tul.mappers.accessLevels;

import p.lodz.tul.DomainModel.Entities.Address;
import p.lodz.tul.DomainModel.Entities.accessLevels.Client;
import p.lodz.tul.dbEntities.AddressEnt;
import p.lodz.tul.dbEntities.accessLevels.ClientEnt;
import p.lodz.tul.mappers.AddressMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientMapperTest {

    @Test
    void toClientTest() {
        AddressEnt addressEnt = new AddressEnt("Podłużna", "Warszawa", "577164174");
        ClientEnt clientEnt = new ClientEnt("Michał", "Poduszka", addressEnt, 47104.12);
        Client client = ClientMapper.toClient(clientEnt);

        assertEquals(clientEnt.getAddressEnt(), AddressMapper.toAddressEnt(client.getAddress()));
        assertEquals(clientEnt.getFirstName(), client.getFirstName());
        assertEquals(clientEnt.getLastName(), client.getLastName());
        assertEquals(clientEnt.getAmountOfMoney(), client.getAmountOfMoney());
    }

    @Test
    void toClientEntTest() {
        Address address = new Address("Zielona", "Zgierz", "2123-123-312");
        Client client = new Client("Jan", "Kowalski", address, 8483883.12);
        ClientEnt clientEnt = ClientMapper.toClientEnt(client);

        assertEquals(client.getAddress(), AddressMapper.toAddress(clientEnt.getAddressEnt()));
        assertEquals(client.getFirstName(), clientEnt.getFirstName());
        assertEquals(client.getLastName(), clientEnt.getLastName());
        assertEquals(client.getAmountOfMoney(), clientEnt.getAmountOfMoney());
    }

    @Test
    void twoWaysConversionTest() {
        Address address = new Address("Zielona", "Zgierz", "2123-123-312");
        Client client = new Client("Jan", "Kowalski", address, 8483883.12);

        AddressEnt addressEnt = new AddressEnt("Podłużna", "Warszawa", "577164174");
        ClientEnt clientEnt = new ClientEnt("Michał", "Poduszka", addressEnt, 47104.12);

        Client convertedClient = ClientMapper.toClient(ClientMapper.toClientEnt(client));
        ClientEnt convertedClientEnt = ClientMapper.toClientEnt(ClientMapper.toClient(clientEnt));

        assertEquals(client, convertedClient);
        assertEquals(clientEnt, convertedClientEnt);
    }
}