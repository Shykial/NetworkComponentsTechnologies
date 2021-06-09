package p.lodz.tul.restadapter.mappers;

import p.lodz.tul.domainmodel.entities.Car;
import p.lodz.tul.restadapter.dto.VehicleDTO;

public class VehicleMapper {

    public static Car toVehicle(VehicleDTO vehicleDTO) {
        return new Car(vehicleDTO.getBaseLoanPrice(), vehicleDTO.getVin());
    }

    public static VehicleDTO toVehicleDTO(Car vehicle) {
        return new VehicleDTO(vehicle.getBaseLoanPrice(), vehicle.getVin());
    }
}
