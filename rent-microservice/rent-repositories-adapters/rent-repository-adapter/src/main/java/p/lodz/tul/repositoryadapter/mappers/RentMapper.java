package p.lodz.tul.repositoryadapter.mappers;

import p.lodz.tul.dbentities.RentEnt;
import p.lodz.tul.domainmodel.entities.Rent;

public class RentMapper {

    public static Rent toRent(RentEnt rentEnt) {
        return new Rent(rentEnt.getUuid(), AccountMapper.toAccount(rentEnt.getAccountEnt()), VehicleMapper.toVehicle(rentEnt.getVehicleEnt()), rentEnt.getStartDate(), rentEnt.getEndDate());
    }

    public static RentEnt toRentEnt(Rent rent) {
        return new RentEnt(rent.getUuid(), AccountMapper.toAccountEnt(rent.getAccount()), VehicleMapper.toVehicleEnt(rent.getVehicle()), rent.getStartDate(), rent.getEndDate());


    }
}
