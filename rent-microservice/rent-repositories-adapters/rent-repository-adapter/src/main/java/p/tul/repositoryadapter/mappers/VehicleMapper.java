package p.tul.repositoryadapter.mappers;

import p.tul.dbentities.VehicleEnt;
import p.tul.domainmodel.entities.Car;

public class VehicleMapper {

    public static Car toVehicle(VehicleEnt vehicleEnt) {
        return new Car(vehicleEnt.getBaseLoanPrice(), vehicleEnt.getVin());
    }

    public static VehicleEnt toVehicleEnt(Car vehicle) {
        return new VehicleEnt(vehicle.getBaseLoanPrice(), vehicle.getVin());
    }
}
