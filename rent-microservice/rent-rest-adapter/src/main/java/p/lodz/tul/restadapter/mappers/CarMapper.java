package p.lodz.tul.restadapter.mappers;

import p.lodz.tul.domainmodel.entities.Car;
import p.lodz.tul.restadapter.dto.CarDTO;

public class CarMapper {

    public static Car toCar(CarDTO carDTO) {
        return new Car(carDTO.getBaseLoanPrice(), carDTO.getVin());
    }

    public static CarDTO toCarDTO(Car vehicle) {
        return new CarDTO(vehicle.getBaseLoanPrice(), vehicle.getVin());
    }
}
