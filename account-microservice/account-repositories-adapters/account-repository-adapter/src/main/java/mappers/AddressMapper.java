package mappers;

import DomainModel.Entities.Address;
import dbEntities.AddressEnt;

public class AddressMapper {

    private AddressMapper() {
    }

    public static Address toAddress(AddressEnt addressEnt) {
        return new Address(addressEnt.getStreetName(), addressEnt.getCityName(), addressEnt.getPhoneNumber());
    }

    public static AddressEnt toAddressEnt(Address address) {
        return new AddressEnt(address.getStreetName(), address.getCityName(), address.getPhoneNumber());
    }
}
