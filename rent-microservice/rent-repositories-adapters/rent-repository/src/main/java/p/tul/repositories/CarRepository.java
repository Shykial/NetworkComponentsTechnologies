package p.tul.repositories;


import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;
import p.tul.dbentities.VehicleEnt;

import java.util.*;

@Repository
public class CarRepository implements Iterable<VehicleEnt> {

    private final Map<String, VehicleEnt> vehicles;
    private final IdGenerator idGenerator;

    public CarRepository() {
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
