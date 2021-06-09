package p.lodz.tul.repositoryadapter.mappers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import p.lodz.tul.dbentities.AccountEnt;
import p.lodz.tul.dbentities.RentEnt;
import p.lodz.tul.dbentities.VehicleEnt;
import p.lodz.tul.domainmodel.entities.Car;
import p.lodz.tul.domainmodel.entities.Client;
import p.lodz.tul.domainmodel.entities.Rent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RentMapperTest {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @Test
    void toRent() {

        AccountEnt accountEnt = new AccountEnt("janek123@gmail.com", "janek123", true, 176391.12);
        VehicleEnt vehicleEnt = new VehicleEnt(123321.12, "MDHFBUK13U0820078");

        RentEnt rentEnt = new RentEnt(UUID.randomUUID(), accountEnt, vehicleEnt, LocalDateTime.now(), null);
        Rent rent = RentMapper.toRent(rentEnt);

        assertEquals(rentEnt.getUuid(), rent.getUuid());
        assertEquals(rentEnt.getAccountEnt(), AccountMapper.toAccountEnt(rent.getAccount()));
        assertEquals(rentEnt.getVehicleEnt(), VehicleMapper.toVehicleEnt(rent.getVehicle()));
        assertEquals(rentEnt.getEndDate(), rent.getEndDate());
        assertEquals(rentEnt.getStartDate(), rent.getStartDate());
    }

    @SneakyThrows
    @Test
    void toRentEnt() {
        Client account = new Client("kowal1995@gmail.com", "kowal47329", false, 17319.12);
        Car vehicle = new Car(1213.12, "GHSJGHS12FASDFGASDF");

        Rent rent = new Rent(account, vehicle, LocalDate.parse("2019-12-22", dateTimeFormatter).atStartOfDay(), LocalDateTime.now());
        RentEnt rentEnt = RentMapper.toRentEnt(rent);

        assertEquals(rent.getUuid(), rentEnt.getUuid());
        assertEquals(rent.getAccount(), AccountMapper.toAccount(rentEnt.getAccountEnt()));
        assertEquals(rent.getVehicle(), VehicleMapper.toVehicle(rentEnt.getVehicleEnt()));
        assertEquals(rent.getEndDate(), rentEnt.getEndDate());
        assertEquals(rent.getStartDate(), rentEnt.getStartDate());
    }

    @SneakyThrows
    @Test
    void twoWaysConversionTest() {
        Client account = new Client("kowal1995@gmail.com", "kowal47329", false, 1639992.2);
        Car vehicle = new Car(1213.12, "GHSJGHS12FASDFGASDF");
        Rent rent = new Rent(account, vehicle, LocalDate.parse("2019-12-22", dateTimeFormatter).atStartOfDay(), LocalDateTime.now());

        AccountEnt accountEnt = new AccountEnt("janek123@gmail.com", "janek123", true, 719222.2);
        VehicleEnt vehicleEnt = new VehicleEnt(123321.12, "MDHFBUK13U0820078");
        RentEnt rentEnt = new RentEnt(UUID.randomUUID(), accountEnt, vehicleEnt, LocalDateTime.now(), null);

        Rent convertedRent = RentMapper.toRent(RentMapper.toRentEnt(rent));
        RentEnt convertedRentEnt = RentMapper.toRentEnt(RentMapper.toRent(rentEnt));

        assertEquals(rent, convertedRent);
        assertEquals(rentEnt, convertedRentEnt);
    }
}