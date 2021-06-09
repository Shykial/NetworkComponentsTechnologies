package p.tul.repositoryadapter.adapters;

import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;
import p.tul.applicationports.repo.RentRepositoryPort;
import p.tul.domainmodel.entities.Rent;
import p.tul.domainmodel.exceptions.RentException;
import p.tul.repositories.RentRepository;
import p.tul.repositoryadapter.mappers.RentMapper;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class RentListRepositoryAdapter implements RentRepositoryPort {
    final RentRepository rentRepository;

    @Inject
    public RentListRepositoryAdapter(RentRepository rentRepository) {
        this.rentRepository = rentRepository;
    }

    public RentListRepositoryAdapter() {
        rentRepository = new RentRepository();
    }

    @SneakyThrows
    @Override
    public void addRent(Rent rent) {
        if (rent == null) {
            throw new RentException(RentException.NULL_VALUE);
        }

        try {
            rentRepository.addRent(RentMapper.toRentEnt(rent));
        } catch (Exception e) {
            throw new RentException(RentException.RENT_EXISTS);
        }
    }

    @SneakyThrows
    @Override
    public void removeRent(Rent rent) {
        if (rent == null) {
            throw new RentException(RentException.NULL_VALUE);
        }

        try {
            rentRepository.removeRent(RentMapper.toRentEnt(rent));
        } catch (Exception e) {
            throw new RentException(RentException.RENT_NOT_FOUND);
        }
    }

    @SneakyThrows
    @Override
    public void removeRent(UUID uuid) {
        if (uuid == null) {
            throw new RentException(RentException.NULL_VALUE);
        }

        try {
            rentRepository.removeRent(uuid);
        } catch (Exception e) {
            throw new RentException(RentException.RENT_NOT_FOUND);
        }
    }

    @SneakyThrows
    @Override
    public void updateRent(UUID uuid, Rent rent) {
        if (rent == null) {
            throw new RentException(RentException.NULL_VALUE);
        }

        try {
            rentRepository.updateRent(uuid, RentMapper.toRentEnt(rent));
        } catch (Exception e) {
            throw new RentException(RentException.RENT_NOT_FOUND);
        }
    }

    @SneakyThrows
    @Override
    public Rent getRent(UUID uuid) {
        if (uuid == null) {
            throw new RentException(RentException.NULL_VALUE);
        }

        if (rentRepository.getRent(uuid).isEmpty()) {
            throw new RentException(RentException.RENT_NOT_FOUND);
        }
        return RentMapper.toRent(rentRepository.getRent(uuid).get());
    }

    @SneakyThrows
    @Override
    public boolean rentExists(UUID uuid) {
        if (uuid == null) {
            throw new RentException(RentException.NULL_VALUE);
        }

        return rentRepository.getRent(uuid).isPresent();
    }

    @Override
    public List<Rent> getAllRents() {
        return rentRepository.getAllRents().stream()
                .map(RentMapper::toRent)
                .collect(Collectors.toList());
    }
}
