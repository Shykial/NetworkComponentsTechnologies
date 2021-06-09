package p.tul.applicationports.service;

import p.tul.domainmodel.entities.Rent;

import java.util.List;
import java.util.UUID;

public interface GetRentsUseCase {
    Rent getRent(UUID uuid);

    List<Rent> getAllRents();

    List<Rent> getRentsForAccount(String login);

    List<Rent> getRentsForVehicle(String vin);
}
