package mappers;

import entities.Vehicle;
import entities.VehicleEnt;

public class VehicleMapper {

    private VehicleMapper() {
    }

    public static Vehicle toVehicle(VehicleEnt vehicleEnt) {
        return new Vehicle(vehicleEnt.getManufacturerName(), vehicleEnt.getModelName(), vehicleEnt.getBaseLoanPrice(), vehicleEnt.getVin(), vehicleEnt.getLicencePlate());
    }

    public static VehicleEnt toVehicleEnt(Vehicle vehicle) {
        return new VehicleEnt(vehicle.getManufacturerName(), vehicle.getModelName(), vehicle.getBaseLoanPrice(), vehicle.getVin(), vehicle.getLicencePlate());
    }
}
