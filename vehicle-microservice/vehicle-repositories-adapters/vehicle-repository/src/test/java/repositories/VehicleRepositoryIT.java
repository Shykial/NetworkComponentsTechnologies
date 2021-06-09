package repositories;

import java.util.List;
import java.util.Optional;
import p.lodz.tul.entities.VehicleEnt;
import p.lodz.tul.exceptions.RepositoryException;
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
import p.lodz.tul.repository.VehicleRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VehicleRepositoryIT {

    private final VehicleRepository persistentRepo;
    private VehicleRepository repository;

    public VehicleRepositoryIT() {
        persistentRepo = new VehicleRepository();
    }

    @BeforeEach
    void setUp() {
        repository = new VehicleRepository();
    }

    @ParameterizedTest(name = "{index} {0} is being added.")
    @CsvFileSource(resources = "/vehicles.csv", numLinesToSkip = 1)
    void addVehicle_TEST_OK(@AggregateWith(VehicleAggregator.class) VehicleEnt vehicle) {
        assertDoesNotThrow(() -> repository.addVehicle(vehicle));
        assertThat(repository).contains(vehicle);

        persistentRepo.addVehicle(vehicle);
        assertThat(repository).doesNotHaveDuplicates();
    }

    @ParameterizedTest(name = "{index} adding vehicle with null argument value.")
    @NullSource
    void addVehicle_NULL_VALUE(VehicleEnt vehicle) {
        RepositoryException exception = assertThrows(RepositoryException.class, () -> repository.addVehicle(vehicle));
        assertThat(exception).hasMessage(RepositoryException.NULL_VALUE);
    }

    @ParameterizedTest(name = "{index} {0} is being added.")
    @CsvFileSource(resources = "/vehicles.csv", numLinesToSkip = 1)
    void addVehicle_DUPLICATED_VALUE(@AggregateWith(VehicleAggregator.class) VehicleEnt vehicle) {
        assertDoesNotThrow(() -> repository.addVehicle(vehicle));
        VehicleEnt duplicatedVehicle = new VehicleEnt("BMW", "X", 32500d, vehicle.getVin(), "EL0333");

        assertThatThrownBy(
                () -> repository.addVehicle(duplicatedVehicle))
                .isInstanceOf(RepositoryException.class)
                .hasMessage(RepositoryException.ENTITY_EXISTS);
    }

    @Test
    void removeVehicleByVehicle_TEST_OK() {
        initTestSet(repository);
        int initialRepoSize = repository.getAllVehicles().size();

        VehicleEnt vehicle = new VehicleEnt("Fiat", "Doblo", 51.2d, "FIA987987987AIF", "EL56789");

        assertDoesNotThrow(() -> repository.removeVehicle(vehicle));
        assertThat(repository).hasSize(initialRepoSize - 1);
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void removeVehicle_NULL_VALUE() {
        initTestSet(repository);
        int initialRepoSize = repository.getAllVehicles().size();

        VehicleEnt nullVehicle = null;
        assertThatExceptionOfType(
                RepositoryException.class)
                .isThrownBy(() -> repository.removeVehicle(nullVehicle))
                .withMessage(RepositoryException.NULL_VALUE);
        assertThat(repository).hasSize(initialRepoSize);
    }

    @Test
    void removeVehicleByLogin_NOT_FOUND() {
        int initialRepoSize = repository.getAllVehicles().size();

        assertThatExceptionOfType(RepositoryException.class)
                .isThrownBy(() -> repository.removeVehicle("nonExistingVehicleVin"))
                .withMessage(RepositoryException.ENTITY_NOT_FOUND);
        assertThat(repository).hasSize(initialRepoSize);
    }

    @Test
    void updateVehicle_TEST_OK() {
        initTestSet(repository);

        VehicleEnt vehicle = repository.getVehicle("FIA987987987AIF").get();
        double newLoanPrice = 99.9d;
        VehicleEnt newVehicle = new VehicleEnt(
                vehicle.getManufacturerName(), vehicle.getModelName(),
                newLoanPrice, vehicle.getVin(), vehicle.getLicencePlate());

        assertDoesNotThrow(() -> repository.updateVehicle(newVehicle.getVin(), newVehicle));
        assertThat(repository.getVehicle(vehicle.getVin()).get().getBaseLoanPrice()).isEqualTo(newLoanPrice);
        assertThat(repository).contains(newVehicle);
    }

    @Test
    void updateVehicle_NULL_VALUE() {
        initTestSet(repository);

        VehicleEnt vehicle = repository.getVehicle("FIA987987987AIF").get();

        assertThatExceptionOfType(RepositoryException.class)
                .isThrownBy(() -> repository.updateVehicle(vehicle.getVin(), null))
                .withMessage(RepositoryException.NULL_VALUE);
    }

    @Test
    void updateVehicle_NOT_FOUND() {
        initTestSet(repository);

        VehicleEnt vehicle = repository.getVehicle("FIA987987987AIF").get();
        String nonExistingVin = "nonExistingVin";

        assertThat(repository).extracting(VehicleEnt::getVin).doesNotContain(nonExistingVin);

        assertThatExceptionOfType(RepositoryException.class)
                .isThrownBy(() -> repository.updateVehicle(nonExistingVin, vehicle))
                .withMessage(RepositoryException.ENTITY_NOT_FOUND);
    }

    @Test
    void getVehicle_TEST_OK() {
        initTestSet(repository);

        String existingVin = "FIA987987987AIF";

        assertThat(repository).extracting(VehicleEnt::getVin).contains(existingVin);
        Optional<VehicleEnt> vehicleFound = repository.getVehicle(existingVin);
        assertTrue(vehicleFound.isPresent());
    }

    @Test
    void getAllAccounts_TEST_OK() {
        initTestSet(repository);

        String existingVin = "FIA987987987AIF";

        assertThat(repository).hasSize(3).contains(repository.getVehicle(existingVin).get());

        List<VehicleEnt> vehicles = repository.getAllVehicles();
        assertThat(vehicles).hasSize(3).extracting(VehicleEnt::getVin).contains(existingVin);
    }

    private void initTestSet(VehicleRepository testRepository) {
        VehicleEnt vehicle1 = new VehicleEnt("Audi", "S4", 76.5d, "IOP987987987POI", "EL98765");
        VehicleEnt vehicle2 = new VehicleEnt("Fiat", "Doblo", 51.2d, "FIA987987987AIF", "EL56789");
        VehicleEnt vehicle3 = new VehicleEnt("Dacia", "Duster", 63.3d, "DAC098098DAC", "ELWER12");

        testRepository.addVehicle(vehicle1);
        testRepository.addVehicle(vehicle2);
        testRepository.addVehicle(vehicle3);
    }

    static class VehicleAggregator implements ArgumentsAggregator {

        @Override
        public VehicleEnt aggregateArguments(ArgumentsAccessor aa, ParameterContext pc)
                throws ArgumentsAggregationException {
            String[] parts = (String[]) aa.toArray();

            String manufacturer = parts[0];
            String model = parts[1];
            double basePrice = Double.parseDouble(parts[2]);
            String vin = parts[3];
            String licensePlate = parts[4];

            return new VehicleEnt(manufacturer, model, basePrice, vin, licensePlate);
        }
    }
}
