package mappers;

import DomainModel.Entities.Address;
import dbEntities.AddressEnt;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddressMapperTest {

    @Test
    void toAddressTest() {
        AddressEnt addressEnt = new AddressEnt("Podłużna", "Warszawa", "577164174");
        Address address = AddressMapper.toAddress(addressEnt);

        assertEquals(addressEnt.getStreetName(), address.getStreetName());
        assertEquals(addressEnt.getPhoneNumber(), address.getPhoneNumber());
        assertEquals(addressEnt.getCityName(), address.getCityName());
    }

    @Test
    void toAddressEntTest() {
        Address address = new Address("Zielona", "Zgierz", "2123-123-312");
        AddressEnt addressEnt = AddressMapper.toAddressEnt(address);

        assertEquals(address.getCityName(), addressEnt.getCityName());
        assertEquals(address.getStreetName(), addressEnt.getStreetName());
        assertEquals(address.getPhoneNumber(), addressEnt.getPhoneNumber());
    }

    @Test
    void twoWaysConversionTest() {
        Address address = new Address("Zielona", "Zgierz", "2123-123-312");
        AddressEnt addressEnt = new AddressEnt("Podłużna", "Warszawa", "577164174");

        Address convertedAddress = AddressMapper.toAddress(AddressMapper.toAddressEnt(address));
        AddressEnt convertedAddressEnt = AddressMapper.toAddressEnt(AddressMapper.toAddress(addressEnt));
        assertEquals(address, convertedAddress);
        assertEquals(addressEnt, convertedAddressEnt);
    }
}