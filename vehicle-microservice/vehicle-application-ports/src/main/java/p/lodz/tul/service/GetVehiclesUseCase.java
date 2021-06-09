package p.lodz.tul.service;

import java.util.List;
import p.lodz.tul.entities.Vehicle;

public interface GetVehiclesUseCase {
    Vehicle getVehicle(String vin);

    List<Vehicle> getAllVehicles();
}
