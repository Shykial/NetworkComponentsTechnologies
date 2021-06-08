package service;

import java.util.List;
import entities.Vehicle;

public interface GetVehiclesUseCase {
    Vehicle getVehicle(String vin);

    List<Vehicle> getAllVehicles();
}
