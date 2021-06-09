package p.lodz.tul.applicationports.service;

import p.lodz.tul.domainmodel.entities.Car;

public interface CreateCarUseCase {
    Car createCar(double baseLoanPrice, String vin);
}
