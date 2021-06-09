package p.lodz.tul.restadapter.mappers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import p.lodz.tul.domainmodel.entities.Car;
import p.lodz.tul.domainmodel.entities.Client;
import p.lodz.tul.domainmodel.entities.Rent;
import p.lodz.tul.restadapter.dto.AccountDTO;
import p.lodz.tul.restadapter.dto.CarDTO;
import p.lodz.tul.restadapter.dto.RentDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RentMapperTest {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Test
    void toRent() {
        AccountDTO accountDTO = new AccountDTO("janek123@gmail.com", "janek123", true, 6288811.12);
        CarDTO carDTO = new CarDTO(123321.12, "MDHFBUK13U0820078");

        RentDTO rentDTO = new RentDTO(UUID.randomUUID(), accountDTO, carDTO, LocalDateTime.now(), null);
        Rent rent = RentMapper.toRent(rentDTO);

        assertEquals(rentDTO.getAccountDTO(), AccountMapper.toAccountDTO(rent.getAccount()));
        assertEquals(rentDTO.getCarDTO(), CarMapper.toCarDTO(rent.getVehicle()));
        assertEquals(rentDTO.getEndDate(), rent.getEndDate());
        assertEquals(rentDTO.getStartDate(), rent.getStartDate());
    }

    @SneakyThrows
    @Test
    void toRentDTO() {
        Client account = new Client("kowal1995@gmail.com", "kowal47329", false, 1604.12);
        Car vehicle = new Car(1213.12, "GHSJGHS12FASDFGASDF");


        Rent rent = new Rent(account, vehicle, LocalDate.parse("2019-12-22", dateTimeFormatter).atStartOfDay(), LocalDateTime.now());
        RentDTO rentDTO = RentMapper.toRentDTO(rent);

        assertEquals(rent.getUuid(), rentDTO.getUuid());
        assertEquals(rent.getAccount(), AccountMapper.toAccount(rentDTO.getAccountDTO()));
        assertEquals(rent.getVehicle(), CarMapper.toCar(rentDTO.getCarDTO()));
        assertEquals(rent.getEndDate(), rentDTO.getEndDate());
        assertEquals(rent.getStartDate(), rentDTO.getStartDate());
    }

    @SneakyThrows
    @Test
    void twoWaysConversionTest() {
        Client account = new Client("kowal1995@gmail.com", "kowal47329", false, 163832.41);
        Car vehicle = new Car(1213.12, "GHSJGHS12FASDFGASDF");
        Rent rent = new Rent(account, vehicle, LocalDate.parse("2019-12-22", dateTimeFormatter).atStartOfDay(), LocalDateTime.now());

        AccountDTO accountDTO = new AccountDTO("janek123@gmail.com", "janek123", true, 787366762.12);
        CarDTO carDTO = new CarDTO(123321.12, "MDHFBUK13U0820078");
        RentDTO rentDTO = new RentDTO(UUID.randomUUID(), accountDTO, carDTO, LocalDateTime.now(), null);

        Rent convertedRent = RentMapper.toRent(RentMapper.toRentDTO(rent));
        RentDTO convertedRentDTO = RentMapper.toRentDTO(RentMapper.toRent(rentDTO));

        assertEquals(rent, convertedRent);
        assertEquals(rentDTO, convertedRentDTO);
    }
}