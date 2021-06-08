package repo;

import java.util.List;
import entities.Vehicle;
import exceptions.VehicleException;

public interface VehicleRepositoryPort {

    void addVehicle(Vehicle vehicle);

    void removeVehicle(Vehicle vehicle);

    void removeVehicle(String vin);

    void updateVehicle(String vin, Vehicle vehicle);

    Vehicle getVehicle(String vin) throws VehicleException;

    boolean vehicleExists(String vin);

    List<Vehicle> getAllVehicles();
}
