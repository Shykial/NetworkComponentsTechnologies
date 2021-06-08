package service;

import entities.Vehicle;

public interface CreateVehicleUseCase {
    Vehicle createVehicle(String manufacturerName, String modelName, double baseLoanPrice, String vin, String licencePlate);
}
