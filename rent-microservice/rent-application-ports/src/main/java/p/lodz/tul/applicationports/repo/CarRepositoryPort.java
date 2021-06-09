package p.lodz.tul.applicationports.repo;

import p.lodz.tul.domainmodel.entities.Car;
import p.lodz.tul.domainmodel.exceptions.VehicleException;

import java.util.List;

public interface CarRepositoryPort {

    void addVehicle(Car vehicle);

    void updateVehicle(String vin, Car vehicle);

    Car getVehicle(String vin) throws VehicleException;

    boolean vehicleExists(String vin);

    List<Car> getAllVehicles();
}
