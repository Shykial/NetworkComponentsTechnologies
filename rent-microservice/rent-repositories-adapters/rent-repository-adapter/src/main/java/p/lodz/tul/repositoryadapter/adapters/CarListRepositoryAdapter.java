package p.lodz.tul.repositoryadapter.adapters;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import p.lodz.tul.applicationports.repo.CarRepositoryPort;
import p.lodz.tul.dbentities.VehicleEnt;
import p.lodz.tul.domainmodel.entities.Car;
import p.lodz.tul.domainmodel.exceptions.VehicleException;
import p.lodz.tul.repositories.CarRepository;
import p.lodz.tul.repositoryadapter.mappers.VehicleMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CarListRepositoryAdapter implements CarRepositoryPort {

    private final CarRepository vehicleRepository;

    @Autowired
    public CarListRepositoryAdapter(CarRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public CarListRepositoryAdapter() {
        vehicleRepository = new CarRepository();
    }

    @SneakyThrows
    @Override
    public void addVehicle(Car vehicle) {
        if (vehicle == null) {
            throw new VehicleException(VehicleException.NULL_VALUE);
        }

        try {
            vehicleRepository.addVehicle(VehicleMapper.toVehicleEnt(vehicle));
        } catch (Exception e) {
            throw new VehicleException(VehicleException.VEHICLE_EXISTS);
        }
    }

    @SneakyThrows
    @Override
    public void updateVehicle(String vin, Car vehicle) {
        if (vehicle == null) {
            throw new VehicleException(VehicleException.NULL_VALUE);
        }

        try {
            vehicleRepository.updateVehicle(vin, VehicleMapper.toVehicleEnt(vehicle));
        } catch (Exception e) {
            throw new VehicleException(VehicleException.VEHICLE_NOT_FOUND);
        }
    }

    @Override
    public Car getVehicle(String vin) throws VehicleException {
        Optional<VehicleEnt> vehicleEntOptional = vehicleRepository.getVehicle(vin);

        if (vehicleEntOptional.isEmpty()) {
            throw new VehicleException(VehicleException.VEHICLE_NOT_FOUND);
        }

        return VehicleMapper.toVehicle(vehicleEntOptional.get());
    }

    @Override
    public boolean vehicleExists(String vin) {
        return vehicleRepository.getVehicle(vin).isPresent();
    }

    @Override
    public List<Car> getAllVehicles() {
        return vehicleRepository.getAllVehicles().stream().map(VehicleMapper::toVehicle).collect(Collectors.toList());
    }
}
