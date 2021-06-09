package p.lodz.tul.applicationports.service;

import p.lodz.tul.domainmodel.entities.Car;

import java.util.List;

public interface GetVehiclesUseCase {
    Car getVehicle(String vin);
    List<Car> getAllVehicles();
}
