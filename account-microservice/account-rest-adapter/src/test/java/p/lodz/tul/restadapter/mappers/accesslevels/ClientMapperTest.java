package p.lodz.tul.restadapter.mappers.accesslevels;

import p.lodz.tul.domainmodel.entities.Address;
import p.lodz.tul.domainmodel.entities.accesslevels.Client;
import p.lodz.tul.restadapter.dto.AddressDTO;
import p.lodz.tul.restadapter.dto.accesslevels.ClientDTO;
import p.lodz.tul.restadapter.mappers.AddressMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientMapperTest {

    @Test
    void toClientTest() {
        AddressDTO addressDTO = new AddressDTO("Podłużna", "Warszawa", "577164174");
        ClientDTO clientDTO = new ClientDTO("Michał", "Poduszka", addressDTO, 47104.12);
        Client client = ClientMapper.toClient(clientDTO);

        assertEquals(clientDTO.getAddressDTO(), AddressMapper.toAddressDTO(client.getAddress()));
        assertEquals(clientDTO.getFirstName(), client.getFirstName());
        assertEquals(clientDTO.getLastName(), client.getLastName());
        assertEquals(clientDTO.getAmountOfMoney(), client.getAmountOfMoney());
    }

    @Test
    void toClientDTOTest() {
        Address address = new Address("Zielona", "Zgierz", "2123-123-312");
        Client client = new Client("Jan", "Kowalski", address, 8483883.12);
        ClientDTO clientDTO = ClientMapper.toClientDTO(client);

        assertEquals(client.getAddress(), AddressMapper.toAddress(clientDTO.getAddressDTO()));
        assertEquals(client.getFirstName(), clientDTO.getFirstName());
        assertEquals(client.getLastName(), clientDTO.getLastName());
        assertEquals(client.getAmountOfMoney(), clientDTO.getAmountOfMoney());
    }

    @Test
    void twoWaysConversionTest() {
        Address address = new Address("Zielona", "Zgierz", "2123-123-312");
        Client client = new Client("Jan", "Kowalski", address, 8483883.12);

        AddressDTO addressDTO = new AddressDTO("Podłużna", "Warszawa", "577164174");
        ClientDTO clientDTO = new ClientDTO("Michał", "Poduszka", addressDTO, 47104.12);

        Client convertedClient = ClientMapper.toClient(ClientMapper.toClientDTO(client));
        ClientDTO convertedClientDTO = ClientMapper.toClientDTO(ClientMapper.toClient(clientDTO));

        assertEquals(client, convertedClient);
        assertEquals(clientDTO, convertedClientDTO);
    }
}