package p.tul.applicationports.service;

import p.tul.domainmodel.entities.Rent;

import java.time.LocalDateTime;

public interface EndRentUseCase {
    Rent endRent(Rent rent, LocalDateTime endDate);

    Rent endRent(Rent rent);
}
