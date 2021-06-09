package p.lodz.tul.applicationports.service;


import p.lodz.tul.domainmodel.entities.Car;
import p.lodz.tul.domainmodel.entities.Client;
import p.lodz.tul.domainmodel.entities.Rent;

import java.time.LocalDateTime;
public interface CreateRentUseCase {
    Rent createRent(Client account, Car vehicle, LocalDateTime startDate);

    Rent createRent(Client account, Car vehicle, LocalDateTime startDate, LocalDateTime endDate);

    Rent createRent(Client account, Car vehicle);

}
