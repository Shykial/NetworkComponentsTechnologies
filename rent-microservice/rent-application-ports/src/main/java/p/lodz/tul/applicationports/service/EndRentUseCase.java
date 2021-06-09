package p.lodz.tul.applicationports.service;

import p.lodz.tul.domainmodel.entities.Rent;

import java.time.LocalDateTime;

public interface EndRentUseCase {
    Rent endRent(Rent rent, LocalDateTime endDate);

    Rent endRent(Rent rent);
}
