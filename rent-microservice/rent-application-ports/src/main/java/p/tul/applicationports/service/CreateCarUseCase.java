package p.tul.applicationports.service;

import p.tul.domainmodel.entities.Car;

public interface CreateCarUseCase {
    Car createCar(double baseLoanPrice, String vin);
}
