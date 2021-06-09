package p.lodz.tul.repositories;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.NullSource;
import p.lodz.tul.dbentities.AccountEnt;
import p.lodz.tul.dbentities.RentEnt;
import p.lodz.tul.dbentities.VehicleEnt;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class RentRepositoryIT {

    private RentRepository repository;
    private final RentRepository persistentRepo;

    private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;


    public RentRepositoryIT() {
        persistentRepo = new RentRepository();
    }

    @BeforeEach
    void setUp() {
        repository = new RentRepository();
    }

    @ParameterizedTest(name = "{index} {0} is being added.")
    @CsvFileSource(resources = "/rents.csv", numLinesToSkip = 1)
    void addRent_TEST_OK(@AggregateWith(RentAggregator.class) RentEnt rent) {
        assertDoesNotThrow(() -> repository.addRent(rent));
        assertThat(repository).contains(rent);

        persistentRepo.addRent(rent);
        assertThat(repository).doesNotHaveDuplicates();
    }

    @ParameterizedTest(name = "{index} adding rent with null argument value.")
    @NullSource
    void addRent_NULL_VALUE(RentEnt rent) {
        RepositoryException exception = assertThrows(RepositoryException.class, () -> repository.addRent(rent));
        assertThat(exception).hasMessage(RepositoryException.NULL_VALUE);
    }

    @ParameterizedTest(name = "{index} {0} is being added.")
    @CsvFileSource(resources = "/rents.csv", numLinesToSkip = 1)
    void addRent_DUPLICATED_VALUE(@AggregateWith(RentAggregator.class) RentEnt rent) {
        assertDoesNotThrow(() -> repository.addRent(rent));


        RentEnt duplicatedRent = new RentEnt(rent.getUuid(), rent.getAccountEnt(), rent.getVehicleEnt(), rent.getStartDate(), rent.getEndDate());

        assertThatThrownBy(
                () -> repository.addRent(duplicatedRent))
                .isInstanceOf(RepositoryException.class)
                .hasMessage(RepositoryException.ENTITY_EXISTS);
    }

    @Test
    void removeRentByRent_TEST_OK() {
        initTestSet(repository);
        int initialRepoSize = repository.getAllRents().size();

        AccountEnt account2 = new AccountEnt("account2@mail.com", "account2", false, 9991);
        VehicleEnt vehicle2 = new VehicleEnt(51.2d, "FIA987987987AIF");
        RentEnt rent = new RentEnt("6ea9d5f6-58bd-4b0d-9dd2-34d00db4d661", account2, vehicle2, LocalDateTime.now(), LocalDateTime.now());

        assertDoesNotThrow(() -> repository.removeRent(rent));
        assertThat(repository).hasSize(initialRepoSize - 1);
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void removeRent_NULL_VALUE() {
        initTestSet(repository);
        int initialRepoSize = repository.getAllRents().size();

        RentEnt nullRent = null;
        assertThatExceptionOfType(
                RepositoryException.class)
                .isThrownBy(() -> repository.removeRent(nullRent))
                .withMessage(RepositoryException.NULL_VALUE);
        assertThat(repository).hasSize(initialRepoSize);
    }

    @Test
    void removeRentByUuid_NOT_FOUND() {
        int initialRepoSize = repository.getAllRents().size();

        assertThatExceptionOfType(RepositoryException.class)
                .isThrownBy(() -> repository.removeRent(UUID.fromString("6ea9d5f6-58bd-4b0d-9dd2-34d00db4d661")))
                .withMessage(RepositoryException.ENTITY_NOT_FOUND);
        assertThat(repository).hasSize(initialRepoSize);
    }

    @Test
    void updateRent_TEST_OK() {
        initTestSet(repository);

        RentEnt rent = repository.getRent(UUID.fromString("6ea9d5f6-58bd-4b0d-9dd2-34d00db4d661")).get();
        LocalDateTime newBeginDate = LocalDate.parse("2021-02-01", dateTimeFormatter).atStartOfDay();
        RentEnt newRent = new RentEnt(rent.getUuid(), rent.getAccountEnt(), rent.getVehicleEnt(), newBeginDate, rent.getEndDate());

        assertDoesNotThrow(() -> repository.updateRent(rent.getUuid(), newRent));
        assertThat(repository.getRent(rent.getUuid()).get().getStartDate()).isEqualTo(newBeginDate);
        assertThat(repository).contains(newRent);
    }

    @Test
    void updateRent_NULL_VALUE() {
        initTestSet(repository);

        RentEnt rent = repository.getRent(UUID.fromString("6ea9d5f6-58bd-4b0d-9dd2-34d00db4d661")).get();

        assertThatExceptionOfType(RepositoryException.class)
                .isThrownBy(() -> repository.updateRent(rent.getUuid(), null))
                .withMessage(RepositoryException.NULL_VALUE);
    }

    @Test
    void updateRent_NOT_FOUND() {
        initTestSet(repository);

        RentEnt rent = repository.getRent(UUID.fromString("6ea9d5f6-58bd-4b0d-9dd2-34d00db4d661")).get();
        UUID nonExistingUuid = UUID.fromString("819ef7bd-285a-47a1-b940-a6949bab70b9");

        assertThat(repository).extracting(RentEnt::getUuid).doesNotContain(nonExistingUuid);

        assertThatExceptionOfType(RepositoryException.class)
                .isThrownBy(() -> repository.updateRent(nonExistingUuid, rent))
                .withMessage(RepositoryException.ENTITY_NOT_FOUND);
    }

    @Test
    void getRent_TEST_OK() {
        initTestSet(repository);

        UUID existingRent = UUID.fromString("6ea9d5f6-58bd-4b0d-9dd2-34d00db4d661");

        assertThat(repository).extracting(RentEnt::getUuid).contains(existingRent);
        Optional<RentEnt> rentFound = repository.getRent(existingRent);
        assertTrue(rentFound.isPresent());
    }

    @Test
    void getAllAccounts_TEST_OK() {
        initTestSet(repository);

        UUID existingUuid = UUID.fromString("6ea9d5f6-58bd-4b0d-9dd2-34d00db4d661");

        assertThat(repository).hasSize(3).contains(repository.getRent(existingUuid).get());

        List<RentEnt> rents = repository.getAllRents();
        assertThat(rents).hasSize(3).extracting(RentEnt::getUuid).contains(existingUuid);
    }

    private void initTestSet(RentRepository testRepository) {
        AccountEnt account1 = new AccountEnt("account1@mail.com", "account1", true, 17483);
        AccountEnt account2 = new AccountEnt("account2@mail.com", "haslo", false, 17301.2);
        AccountEnt account3 = new AccountEnt("account3@mail.com", "account3", true, 180122.1);

        VehicleEnt vehicle1 = new VehicleEnt(76.5d, "IOP987987987POI");
        VehicleEnt vehicle2 = new VehicleEnt(51.2d, "FIA987987987AIF");
        VehicleEnt vehicle3 = new VehicleEnt(63.3d, "DAC098098DAC");

        RentEnt rent1 = new RentEnt("6f2d3124-be04-466d-8a83-2b5b61279eda", account1, vehicle1, LocalDateTime.now(), LocalDateTime.now());
        RentEnt rent2 = new RentEnt("6ea9d5f6-58bd-4b0d-9dd2-34d00db4d661", account2, vehicle2, LocalDateTime.now(), LocalDateTime.now());
        RentEnt rent3 = new RentEnt("968dbd1e-2c29-411f-8e55-25fcd259fcf2", account3, vehicle3, LocalDateTime.now(), LocalDateTime.now());

        testRepository.addRent(rent1);
        testRepository.addRent(rent2);
        testRepository.addRent(rent3);
    }

    public static class RentAggregator implements ArgumentsAggregator {

        @SneakyThrows
        @Override
        public RentEnt aggregateArguments(ArgumentsAccessor aa, ParameterContext pc)
                throws ArgumentsAggregationException {
            String[] parts = (String[]) aa.toArray();

            String uuid = parts[0];
            AccountEnt account = aggregateAccount(Arrays.copyOfRange(parts, 1, 12));
            VehicleEnt vehicle = aggregateVehicle(Arrays.copyOfRange(parts, 12, 17));
            LocalDateTime startDate = LocalDate.parse(parts[17], dateTimeFormatter).atStartOfDay();
            LocalDateTime endDate = LocalDate.parse(parts[18], dateTimeFormatter).atStartOfDay();

            return new RentEnt(uuid, account, vehicle, startDate, endDate);
        }

        private AccountEnt aggregateAccount(String[] accountParts) {
            String email = accountParts[0];
            String login = accountParts[1];
            double amountOfMoney = Double.parseDouble(accountParts[10]);

            boolean active = Boolean.parseBoolean(accountParts[3]);
            return new AccountEnt(email, login, active, amountOfMoney);
        }

        private VehicleEnt aggregateVehicle(String[] vehicleParts) {
            double basePrice = Double.parseDouble(vehicleParts[2]);
            String vin = vehicleParts[3];

            return new VehicleEnt(basePrice, vin);
        }
    }
}