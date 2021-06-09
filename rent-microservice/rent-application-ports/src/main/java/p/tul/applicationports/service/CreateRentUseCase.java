package p.tul.applicationports.service;


import p.tul.domainmodel.entities.Car;
import p.tul.domainmodel.entities.Client;
import p.tul.domainmodel.entities.Rent;

import java.time.LocalDateTime;
public interface CreateRentUseCase {
    Rent createRent(Client account, Car vehicle, LocalDateTime startDate);

    Rent createRent(Client account, Car vehicle, LocalDateTime startDate, LocalDateTime endDate);

    Rent createRent(Client account, Car vehicle);

}
