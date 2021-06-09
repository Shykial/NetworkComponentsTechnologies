package p.lodz.tul.restadapter.mappers;

import p.lodz.tul.domainmodel.entities.Rent;
import p.lodz.tul.restadapter.dto.RentDTO;

public class RentMapper {

    public static Rent toRent(RentDTO rentDTO) {
        return new Rent(rentDTO.getUuid(), AccountMapper.toAccount(rentDTO.getAccountDTO()), CarMapper.toCar(rentDTO.getCarDTO()), rentDTO.getStartDate(), rentDTO.getEndDate());
    }

    public static RentDTO toRentDTO(Rent rent) {
        return new RentDTO(rent.getUuid(), AccountMapper.toAccountDTO(rent.getAccount()), CarMapper.toCarDTO(rent.getVehicle()), rent.getStartDate(), rent.getEndDate());
    }
}
