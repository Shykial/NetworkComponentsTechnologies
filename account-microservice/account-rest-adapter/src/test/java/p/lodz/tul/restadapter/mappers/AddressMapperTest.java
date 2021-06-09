package p.lodz.tul.restadapter.mappers;

import p.lodz.tul.DomainModel.Entities.Address;
import p.lodz.tul.restadapter.dto.AddressDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddressMapperTest {

    @Test
    void toAddressTest() {
        AddressDTO addressDTO = new AddressDTO("Podłużna", "Warszawa", "577164174");
        Address address = AddressMapper.toAddress(addressDTO);

        assertEquals(addressDTO.getStreetName(), address.getStreetName());
        assertEquals(addressDTO.getPhoneNumber(), address.getPhoneNumber());
        assertEquals(addressDTO.getCityName(), address.getCityName());
    }

    @Test
    void toAddressDTOTest() {
        Address address = new Address("Zielona", "Zgierz", "2123-123-312");
        AddressDTO addressDTO = AddressMapper.toAddressDTO(address);

        assertEquals(address.getCityName(), addressDTO.getCityName());
        assertEquals(address.getStreetName(), addressDTO.getStreetName());
        assertEquals(address.getPhoneNumber(), addressDTO.getPhoneNumber());
    }

    @Test
    void twoWaysConversionTest() {
        Address address = new Address("Zielona", "Zgierz", "2123-123-312");
        AddressDTO addressDTO = new AddressDTO("Podłużna", "Warszawa", "577164174");

        Address convertedAddress = AddressMapper.toAddress(AddressMapper.toAddressDTO(address));
        AddressDTO convertedAddressDTO = AddressMapper.toAddressDTO(AddressMapper.toAddress(addressDTO));
        assertEquals(address, convertedAddress);
        assertEquals(addressDTO, convertedAddressDTO);
    }
}