package p.lodz.tul.applicationports.repo;

import p.lodz.tul.domainmodel.entities.Rent;

import java.util.List;
import java.util.UUID;

public interface RentRepositoryPort {

    void addRent(Rent rent);

    void removeRent(Rent rent);

    void removeRent(UUID uuid);

    void updateRent(UUID uuid, Rent rent);

    Rent getRent(UUID uuid);

    boolean rentExists(UUID uuid);

    List<Rent> getAllRents();
}
