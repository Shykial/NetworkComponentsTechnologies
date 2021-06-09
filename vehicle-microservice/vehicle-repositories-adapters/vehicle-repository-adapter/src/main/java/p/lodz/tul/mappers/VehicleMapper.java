package p.lodz.tul.mappers;

import org.springframework.stereotype.Indexed;
import p.lodz.tul.entities.Vehicle;
import p.lodz.tul.entities.VehicleEnt;

public class VehicleMapper {

    public static Vehicle toVehicle(VehicleEnt vehicleEnt) {
        return new Vehicle(vehicleEnt.getManufacturerName(), vehicleEnt.getModelName(), vehicleEnt.getBaseLoanPrice(), vehicleEnt.getVin(), vehicleEnt.getLicencePlate());
    }

    public static VehicleEnt toVehicleEnt(Vehicle vehicle) {
        return new VehicleEnt(vehicle.getManufacturerName(), vehicle.getModelName(), vehicle.getBaseLoanPrice(), vehicle.getVin(), vehicle.getLicencePlate());
    }
}
