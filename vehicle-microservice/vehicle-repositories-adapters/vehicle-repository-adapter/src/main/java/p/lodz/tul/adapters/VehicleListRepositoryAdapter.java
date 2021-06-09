package p.lodz.tul.adapters;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import p.lodz.tul.entities.Vehicle;
import p.lodz.tul.entities.VehicleEnt;
import p.lodz.tul.exceptions.VehicleException;
import lombok.SneakyThrows;
import p.lodz.tul.mappers.VehicleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import p.lodz.tul.repo.VehicleRepositoryPort;
import p.lodz.tul.repository.VehicleRepository;

@Component
public class VehicleListRepositoryAdapter implements VehicleRepositoryPort {

    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleListRepositoryAdapter(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public VehicleListRepositoryAdapter() {
        vehicleRepository = new VehicleRepository();
    }

    @SneakyThrows
    @Override
    public void addVehicle(Vehicle vehicle) {
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
    public void removeVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new VehicleException(VehicleException.NULL_VALUE);
        }

        try {
            vehicleRepository.removeVehicle(VehicleMapper.toVehicleEnt(vehicle));
        } catch (Exception e) {
            throw new VehicleException(VehicleException.VEHICLE_NOT_FOUND);
        }
    }

    @SneakyThrows
    @Override
    public void removeVehicle(String vin) {
        try {
            vehicleRepository.removeVehicle(vin);
        } catch (Exception e) {
            throw new VehicleException(VehicleException.VEHICLE_NOT_FOUND);
        }
    }

    @SneakyThrows
    @Override
    public void updateVehicle(String vin, Vehicle vehicle) {
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
    public Vehicle getVehicle(String vin) throws VehicleException {
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
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.getAllVehicles().stream().map(VehicleMapper::toVehicle).collect(Collectors.toList());
    }
}
