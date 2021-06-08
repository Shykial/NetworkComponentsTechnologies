package mappers;

import dto.VehicleDTO;
import entities.Vehicle;

public class VehicleMapper {

    private VehicleMapper() {
    }

    public static Vehicle toVehicle(VehicleDTO vehicleDTO) {
        return new Vehicle(vehicleDTO.getManufacturerName(), vehicleDTO.getModelName(), vehicleDTO.getBaseLoanPrice(), vehicleDTO.getVin(), vehicleDTO.getLicencePlate());
    }

    public static VehicleDTO toVehicleDTO(Vehicle vehicle) {
        return new VehicleDTO(vehicle.getManufacturerName(), vehicle.getModelName(), vehicle.getBaseLoanPrice(), vehicle.getVin(), vehicle.getLicencePlate());
    }
}
