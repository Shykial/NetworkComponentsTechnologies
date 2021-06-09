package p.lodz.tul.applicationservices;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import p.lodz.tul.applicationports.repo.RentRepositoryPort;
import p.lodz.tul.applicationports.service.CreateRentUseCase;
import p.lodz.tul.applicationports.service.EndRentUseCase;
import p.lodz.tul.applicationports.service.GetRentsUseCase;
import p.lodz.tul.applicationports.service.UpdateRentUseCase;
import p.lodz.tul.domainmodel.entities.Car;
import p.lodz.tul.domainmodel.entities.Client;
import p.lodz.tul.domainmodel.entities.Rent;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RentService implements CreateRentUseCase, EndRentUseCase, UpdateRentUseCase, GetRentsUseCase {

    private final RentRepositoryPort rentRepository;

    @Autowired
    public RentService(RentRepositoryPort rentRepository) {
        this.rentRepository = rentRepository;
    }

    @SneakyThrows
    @Override
    public Rent createRent(Client account, Car vehicle) {
        if (account == null || vehicle == null) {
            throw new ServiceException(ServiceException.NULL_ARGUMENT);
        }

        Rent rent = new Rent(UUID.randomUUID(), account, vehicle);
        double chargeForRental = vehicle.getBaseLoanPrice();
//        validateClient(account, chargeForRental);

        return addToRepoAndReturn(rent);
    }

    @SneakyThrows
    @Override
    public Rent createRent(Client account, Car vehicle, LocalDateTime startDate) {
        if (account == null || vehicle == null || startDate == null) {
            throw new ServiceException(ServiceException.NULL_ARGUMENT);
        }

        Rent rent = new Rent(UUID.randomUUID(), account, vehicle, startDate);
        double chargeForRental = vehicle.getBaseLoanPrice();
//        validateClient(account, chargeForRental);

        return addToRepoAndReturn(rent);
    }

    @SneakyThrows
    @Override
    public Rent createRent(Client account, Car vehicle, LocalDateTime startDate, LocalDateTime endDate) {
        if (account == null || vehicle == null || startDate == null || endDate == null) {
            throw new ServiceException(ServiceException.NULL_ARGUMENT);
        }

        Rent rent = new Rent(UUID.randomUUID(), account, vehicle, startDate, endDate);
        double chargeForRental = calculateChargeForRental(rent);
//        validateClient(account, chargeForRental);

        return addToRepoAndReturn(rent);
    }

    private double calculateChargeForRental(Rent rent) {
        long daysOfRental = calculateDaysOfRental(rent.getStartDate(), rent.getEndDate());
        return daysOfRental * rent.getVehicle().getBaseLoanPrice();
    }

    private long calculateDaysOfRental(LocalDateTime startDate, LocalDateTime endDate) {
        Duration duration = Duration.between(startDate, endDate);
        return duration.toDays();
    }
//
//    private void validateClient(Account account, double chargeForRental) throws ServiceException {
//        if (!accountService.authenticate(account)) {
//            throw new ServiceException(ServiceException.NOT_AUTHENTICATED);
//        }
//
//        if (!accountService.authorizeAsClient(account)) {
//            throw new ServiceException(ServiceException.NOT_AUTHORIZED);
//        }
//
//        if (((Client) account.getLevelOfAccess()).getAmountOfMoney() < chargeForRental) {
//            throw new ServiceException(ServiceException.NOT_ENOUGH_FUNDS);
//        }
//    }

    private Rent addToRepoAndReturn(Rent rent) {
        rentRepository.addRent(rent);
        return rent;
    }

    @SneakyThrows
    @Override
    public Rent endRent(Rent rent) {
        if (rent == null) {
            throw new ServiceException(ServiceException.NULL_ARGUMENT);
        }

        if (!rentRepository.rentExists(rent.getUuid())) {
            throw new ServiceException(ServiceException.RENT_NOT_FOUND);
        }

        rent.setEndDate(LocalDateTime.now());
        chargeClientForRent(rent);
        updateRent(rent);
//        accountService.updateAccount(rent.getAccount());

        return rent;
    }

    @SneakyThrows
    @Override
    public Rent endRent(Rent rent, LocalDateTime endDate) {
        if (rent == null || endDate == null) {
            throw new ServiceException(ServiceException.NULL_ARGUMENT);
        }

        if (rent.getEndDate() != null) {
            throw new ServiceException(ServiceException.RENT_ALREADY_ENDED);
        }

        if (!rentRepository.rentExists(rent.getUuid())) {
            throw new ServiceException(ServiceException.RENT_NOT_FOUND);
        }

        rent.setEndDate(endDate);
        chargeClientForRent(rent);
        updateRent(rent);
//        accountService.updateAccount(rent.getAccount());

        return rent;
    }

    @SneakyThrows
    private void chargeClientForRent(Rent rent) {
        if (rent.getEndDate() == null) {
            throw new ServiceException(ServiceException.RENT_NOT_ENDED);
        }
        Client client = rent.getAccount();
        double charge = calculateChargeForRental(rent);

//        client.setAmountOfMoney(client.getAmountOfMoney() - charge);
    }

    @SneakyThrows
    @Override
    public Rent updateRent(Rent rent) {
        if (rent == null) {
            throw new ServiceException(ServiceException.NULL_ARGUMENT);
        }

        UUID rentId = rent.getUuid();

        try {
            rentRepository.updateRent(rentId, rent);
        } catch (Exception e) {
            throw new ServiceException(ServiceException.RENT_NOT_FOUND);
        }

        return rentRepository.getRent(rentId);
    }

    @SneakyThrows
    @Override
    public Rent getRent(UUID uuid) {
        if (uuid == null) {
            throw new ServiceException(ServiceException.NULL_ARGUMENT);
        }

        try {
            return rentRepository.getRent(uuid);
        } catch (Exception e) {
            throw new ServiceException(ServiceException.RENT_NOT_FOUND);
        }
    }

    @Override
    public List<Rent> getAllRents() {
        return rentRepository.getAllRents();
    }

    @Override
    public List<Rent> getRentsForAccount(String login) {
        return rentRepository.getAllRents().stream()
                .filter(rent -> rent.getAccount().getLogin().equals(login))
                .collect(Collectors.toList());
    }

    @Override
    public List<Rent> getRentsForVehicle(String vin) {
        return rentRepository.getAllRents().stream()
                .filter(rent -> rent.getVehicle().getVin().equals(vin))
                .collect(Collectors.toList());
    }
}
