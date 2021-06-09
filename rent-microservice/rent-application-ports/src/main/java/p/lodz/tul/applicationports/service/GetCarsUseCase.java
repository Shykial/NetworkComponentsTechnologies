package p.lodz.tul.applicationports.service;

import p.lodz.tul.domainmodel.entities.Car;

import java.util.List;

public interface GetCarsUseCase {
    Car getCar(String vin);
    List<Car> getAllCars();
}
