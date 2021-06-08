package services;

import java.util.List;
import entities.Vehicle;
import exceptions.ServiceException;
import exceptions.VehicleException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repo.VehicleRepositoryPort;
import service.CreateVehicleUseCase;
import service.GetVehiclesUseCase;
import service.RemoveVehicleUseCase;
import service.UpdateVehicleUseCase;

@Service
public class VehicleService implements CreateVehicleUseCase, RemoveVehicleUseCase, UpdateVehicleUseCase, GetVehiclesUseCase {

    private VehicleRepositoryPort vehicleRepository;

    @Autowired
    public VehicleService(VehicleRepositoryPort vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public VehicleService() {
    }

    @SneakyThrows
    @Override
    public Vehicle createVehicle(String manufacturerName, String modelName, double baseLoanPrice, String vin, String licencePlate) {
        if (vehicleRepository.vehicleExists(vin)) {
            throw new ServiceException("Vehicle with this vin already exists");
        }

        Vehicle vehicle = new Vehicle(manufacturerName, modelName, baseLoanPrice, vin, licencePlate);
        vehicleRepository.addVehicle(vehicle);
        return vehicle;
    }

    @SneakyThrows
    @Override
    public void removeVehicle(String vin) {
        if (!vehicleRepository.vehicleExists(vin)) {
            throw new ServiceException("Cannot delete vehicle as it was not found.");
        }

        vehicleRepository.removeVehicle(vin);
    }

    @Override
    public Vehicle updateVehicle(Vehicle vehicle) {
        vehicleRepository.updateVehicle(vehicle.getVin(), vehicle);
        return vehicle;
    }

    @SneakyThrows
    @Override
    public Vehicle getVehicle(String vin) {
        try {
            return vehicleRepository.getVehicle(vin);
        } catch (VehicleException e) {
            throw new ServiceException("Vehicle with provided vin was not found.");
        }
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.getAllVehicles();
    }
}
