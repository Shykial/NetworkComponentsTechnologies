package p.lodz.tul.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import p.lodz.tul.entities.VehicleEnt;
import p.lodz.tul.exceptions.RepositoryException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;
import p.lodz.tul.util.IdGenerator;

@Repository
public class VehicleRepository implements Iterable<VehicleEnt> {

    private final Map<String, VehicleEnt> vehicles;
    private final IdGenerator idGenerator;

    public VehicleRepository() {
        vehicles = new HashMap<>();
        idGenerator = new IdGenerator();
    }

    @SneakyThrows
    public void addVehicle(VehicleEnt vehicle) {
        if (vehicle == null) {
            throw new RepositoryException(RepositoryException.NULL_VALUE);
        }

        if (vehicles.containsKey(vehicle.getVin())) {
            throw new RepositoryException(RepositoryException.ENTITY_EXISTS);
        }

        vehicle.setId(idGenerator.nextId());
        vehicles.put(vehicle.getVin(), vehicle);
    }

    @SneakyThrows
    public void removeVehicle(VehicleEnt vehicle) {
        if (vehicle == null) {
            throw new RepositoryException(RepositoryException.NULL_VALUE);
        }

        if (!vehicles.containsValue(vehicle)) {
            throw new RepositoryException(RepositoryException.ENTITY_NOT_FOUND);
        }
        removeVehicle(vehicle.getVin());
    }

    @SneakyThrows
    public void removeVehicle(String vin) {
        if (!vehicles.containsKey(vin)) {
            throw new RepositoryException(RepositoryException.ENTITY_NOT_FOUND);
        }
        vehicles.remove(vin);
    }

    @SneakyThrows
    public void updateVehicle(String vin, VehicleEnt vehicle) {
        if (vehicle == null) {
            throw new RepositoryException(RepositoryException.NULL_VALUE);
        }

        if (!vehicles.containsKey(vin)) {
            throw new RepositoryException(RepositoryException.ENTITY_NOT_FOUND);
        }

        vehicles.put(vin, vehicle);
    }

    public Optional<VehicleEnt> getVehicle(String vin) {
        return Optional.ofNullable(vehicles.get(vin));
    }

    public List<VehicleEnt> getAllVehicles() {
        return new ArrayList<>(vehicles.values());
    }

    @Override
    public Iterator<VehicleEnt> iterator() {
        return vehicles.values().iterator();
    }
}
