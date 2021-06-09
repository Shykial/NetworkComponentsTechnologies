package repositoryAdapters;

import java.util.List;
import p.lodz.tul.adapters.VehicleListRepositoryAdapter;
import p.lodz.tul.entities.Vehicle;
import p.lodz.tul.exceptions.VehicleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import p.lodz.tul.mappers.VehicleMapper;
import p.lodz.tul.repository.VehicleRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VehicleListRepositoryAdapterIT {

    private VehicleListRepositoryAdapter adapter;

    private static Vehicle vehicle1() {
        return new Vehicle("Ferrari", "458", 250d, "ZFF67NFA0D0189338", "AA 4321");
    }

    private static Vehicle vehicle2() {
        return new Vehicle("Lamborghini", "Gallardo", 240d, "ZA9DU01B2XLA12304", "BB 1234");
    }

    @BeforeEach
    void setUp() {
        adapter = new VehicleListRepositoryAdapter(new VehicleRepository());
    }

    @Test
    void addVehicle_TEST_OK() {
        Vehicle vehicle = vehicle1();
        assertTrue(adapter.getAllVehicles().isEmpty());
        assertDoesNotThrow(() -> adapter.addVehicle(vehicle));
        assertTrue(adapter.getAllVehicles().contains(vehicle));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void addVehicle_NULL_VALUE() {
        Vehicle vehicle = null;
        assertTrue(adapter.getAllVehicles().isEmpty());
        assertThatThrownBy(
                () -> adapter.addVehicle(vehicle))
                .isInstanceOf(VehicleException.class)
                .hasMessage(VehicleException.NULL_VALUE);
        assertFalse(adapter.getAllVehicles().contains(vehicle));
    }

    @Test
    void addVehicle_DUPLICATED_VALUE() {
        Vehicle vehicle = vehicle1();

        assertDoesNotThrow(() -> adapter.addVehicle(vehicle));
        assertTrue(adapter.getAllVehicles().contains(vehicle));
        assertThatThrownBy(
                () -> adapter.addVehicle(vehicle))
                .isInstanceOf(VehicleException.class)
                .hasMessage(VehicleException.VEHICLE_EXISTS);
    }

    @Test
    void removeVehicleByVehicle_TEST_OK() {
        Vehicle vehicle1 = vehicle1();
        Vehicle vehicle2 = vehicle1();

        adapter.addVehicle(vehicle1);
        assertDoesNotThrow(() -> adapter.removeVehicle(vehicle2));
        assertFalse(adapter.getAllVehicles().contains(vehicle1));
    }

    @Test
    void removeVehicleByVin_TEST_OK() {
        Vehicle vehicle = vehicle1();

        adapter.addVehicle(vehicle);
        assertDoesNotThrow(() -> adapter.removeVehicle(vehicle.getVin()));
        assertFalse(adapter.getAllVehicles().contains(vehicle));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void removeVehicleByVehicle_NULL_VALUE() {
        Vehicle vehicle = vehicle1();
        Vehicle nullVehicle = null;
        adapter.addVehicle(vehicle);

        assertThatThrownBy(
                () -> adapter.removeVehicle(nullVehicle))
                .isInstanceOf(VehicleException.class)
                .hasMessage(VehicleException.NULL_VALUE);
        assertTrue(adapter.getAllVehicles().contains(vehicle));
    }

    @Test
    void removeVehicleByVehicle_NOT_FOUND() {
        Vehicle vehicle = vehicle1();
        assertFalse(adapter.getAllVehicles().contains(vehicle));

        assertThatThrownBy(
                () -> adapter.removeVehicle(vehicle))
                .isInstanceOf(VehicleException.class)
                .hasMessage(VehicleException.VEHICLE_NOT_FOUND);
    }

    @Test
    void removeVehicleByVin_NOT_FOUND() {
        Vehicle vehicle = vehicle1();
        assertFalse(adapter.getAllVehicles().contains(vehicle));

        assertThatThrownBy(
                () -> adapter.removeVehicle(vehicle.getVin()))
                .isInstanceOf(VehicleException.class)
                .hasMessage(VehicleException.VEHICLE_NOT_FOUND);
    }

    @Test
    void updateVehicle_TEST_OK() throws VehicleException {
        Vehicle vehicle1 = vehicle1();
        double newLoanPrice = 280d;
        assertNotEquals(newLoanPrice, vehicle1.getBaseLoanPrice());

        adapter.addVehicle(vehicle1);

        Vehicle vehicle2 = vehicle1();
        vehicle2.setBaseLoanPrice(newLoanPrice);
        adapter.updateVehicle(vehicle1.getVin(), vehicle2);

        assertEquals(adapter.getVehicle(vehicle1.getVin()).getBaseLoanPrice(), newLoanPrice);
    }

    @Test
    void updateVehicle_NULL_VALUE() {
        Vehicle vehicle1 = vehicle1();
        adapter.addVehicle(vehicle1);

        assertThatThrownBy(
                () -> adapter.updateVehicle(vehicle1.getVin(), null))
                .isInstanceOf(VehicleException.class)
                .hasMessage(VehicleException.NULL_VALUE);
    }

    @Test
    void updateVehicle_NOT_FOUND() {
        Vehicle vehicle1 = vehicle1();
        Vehicle vehicle2 = vehicle2();
        double newLoanPrice = 280d;
        vehicle2.setBaseLoanPrice(newLoanPrice);

        assertThatThrownBy(
                () -> adapter.updateVehicle(vehicle1.getVin(), vehicle2))
                .isInstanceOf(VehicleException.class)
                .hasMessage(VehicleException.VEHICLE_NOT_FOUND);
    }

    @Test
    void getVehicle_TEST_OK() {
        Vehicle vehicle1 = vehicle1();
        adapter.addVehicle(vehicle1);

        Vehicle vehicleFound = assertDoesNotThrow(() -> adapter.getVehicle(vehicle1.getVin()));

        assertEquals(vehicleFound, vehicle1);
    }

    @Test
    void getVehicle_NOT_FOUND() {
        Vehicle vehicle1 = vehicle1();

        assertThatThrownBy(
                () -> adapter.getVehicle(vehicle1.getVin()))
                .isInstanceOf(VehicleException.class)
                .hasMessage(VehicleException.VEHICLE_NOT_FOUND);
    }

    @Test
    void getAllVehicles_TEST_OK() {
        Vehicle vehicle1 = vehicle1();
        Vehicle vehicle2 = vehicle2();

        adapter.addVehicle(vehicle1);
        adapter.addVehicle(vehicle2);

        List<Vehicle> vehiclesFound = adapter.getAllVehicles();

        org.hamcrest.MatcherAssert.assertThat(vehiclesFound, hasSize(2));
        org.hamcrest.MatcherAssert.assertThat(vehiclesFound, hasItem(vehicle1));
        org.hamcrest.MatcherAssert.assertThat(vehiclesFound, hasItem(vehicle1));
    }

    @Test
    void vehicleExists_TEST_OK() {
        Vehicle vehicle = vehicle1();
        adapter.addVehicle(vehicle);
        assertTrue(adapter.vehicleExists(vehicle.getVin()));
    }

    @Test
    void vehicleExists_TEST_NOT_OK() {
        Vehicle vehicle = vehicle1();
        assertFalse(adapter.vehicleExists(vehicle.getVin()));
    }
}
