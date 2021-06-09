package p.lodz.tul.restadapter.mappers;

import p.lodz.tul.DomainModel.Entities.Address;
import p.lodz.tul.restadapter.dto.AddressDTO;

public class AddressMapper {

    private AddressMapper() {
    }

    public static Address toAddress(AddressDTO addressDTO) {
        return new Address(addressDTO.getStreetName(), addressDTO.getCityName(), addressDTO.getPhoneNumber());
    }

    public static AddressDTO toAddressDTO(Address address) {
        return new AddressDTO(address.getStreetName(), address.getCityName(), address.getPhoneNumber());
    }
}
