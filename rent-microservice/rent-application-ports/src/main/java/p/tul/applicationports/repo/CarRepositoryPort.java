package p.tul.applicationports.repo;

import p.tul.domainmodel.entities.Car;
import p.tul.domainmodel.exceptions.VehicleException;

import java.util.List;

public interface CarRepositoryPort {

    void addVehicle(Car vehicle);

    void updateVehicle(String vin, Car vehicle);

    Car getVehicle(String vin) throws VehicleException;

    boolean vehicleExists(String vin);

    List<Car> getAllVehicles();
}
