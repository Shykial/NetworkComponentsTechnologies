package p.lodz.tul.repo;

import java.util.List;
import p.lodz.tul.entities.Vehicle;
import p.lodz.tul.exceptions.VehicleException;

public interface VehicleRepositoryPort {

    void addVehicle(Vehicle vehicle);

    void removeVehicle(Vehicle vehicle);

    void removeVehicle(String vin);

    void updateVehicle(String vin, Vehicle vehicle);

    Vehicle getVehicle(String vin) throws VehicleException;

    boolean vehicleExists(String vin);

    List<Vehicle> getAllVehicles();
}
