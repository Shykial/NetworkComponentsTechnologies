package p.tul.repositories;

import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;
import p.tul.dbentities.RentEnt;

import java.util.*;

@Repository
public class RentRepository implements Iterable<RentEnt> {

    private final Map<UUID, RentEnt> rents;

    private final IdGenerator idGenerator;

    public RentRepository() {
        rents = new HashMap<>();
        idGenerator = new IdGenerator();
    }

    public RentRepository(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
        rents = new HashMap<>();
    }

    @SneakyThrows
    public void addRent(RentEnt rent) {
        if (rent == null) {
            throw new RepositoryException(RepositoryException.NULL_VALUE);
        }

        if (rents.containsKey(rent.getUuid())) {
            throw new RepositoryException(RepositoryException.ENTITY_EXISTS);

        }
        rent.setId(idGenerator.nextId());
        rents.put(rent.getUuid(), rent);
    }

    @SneakyThrows
    public void removeRent(RentEnt rent) {
        if (rent == null) {
            throw new RepositoryException(RepositoryException.NULL_VALUE);
        }

        if (!rents.containsValue(rent)) {
            throw new RepositoryException(RepositoryException.ENTITY_NOT_FOUND);
        }
        removeRent(rent.getUuid());
    }

    @SneakyThrows
    public void removeRent(UUID uuid) {
        if (uuid == null) {
            throw new RepositoryException(RepositoryException.NULL_VALUE);
        }

        if (!rents.containsKey(uuid)) {
            throw new RepositoryException(RepositoryException.ENTITY_NOT_FOUND);
        }
        rents.remove(uuid);
    }

    @SneakyThrows
    public void updateRent(UUID uuid, RentEnt rent) {
        if ((uuid == null) || (rent == null)) {
            throw new RepositoryException(RepositoryException.NULL_VALUE);
        }

        if (!rents.containsKey(uuid)) {
            throw new RepositoryException(RepositoryException.ENTITY_NOT_FOUND);
        }

        if (rent.getUuid() != uuid) {
            rent.setUuid(uuid);

        }

        rents.put(uuid, rent);
    }

    public Optional<RentEnt> getRent(UUID uuid) {
        return Optional.ofNullable(rents.get(uuid));
    }

    public List<RentEnt> getAllRents() {
        return new ArrayList<>(rents.values());
    }

    @Override
    public Iterator<RentEnt> iterator() {
        return rents.values().iterator();
    }
}
