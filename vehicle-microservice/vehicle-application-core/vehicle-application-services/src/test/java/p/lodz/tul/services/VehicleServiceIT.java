package p.lodz.tul.services;

import java.util.Arrays;
import java.util.List;

import p.lodz.tul.adapters.VehicleListRepositoryAdapter;
import p.lodz.tul.entities.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class VehicleServiceIT {
    private VehicleService vehicleService;

    @BeforeEach
    void setUp() {
        vehicleService = new VehicleService(new VehicleListRepositoryAdapter());
    }

    private static Vehicle vehicle1() {
        return new Vehicle("Kia", "Ceed", 1283.12, "HFHAG82HFDFKASHF", "EL 483GH");
    }


    @Test
    void createVehicle_TEST_OK() {
        int numberOfVehicles = vehicleService.getAllVehicles().size();
        Vehicle v1 = vehicle1();

        assertThatThrownBy(() -> vehicleService.getVehicle(v1.getVin())).hasMessage("Vehicle with provided vin was not found.");
        vehicleService.createVehicle(v1.getManufacturerName(), v1.getModelName(), v1.getBaseLoanPrice(), v1.getVin(), v1.getLicencePlate());

        assertEquals(numberOfVehicles + 1, vehicleService.getAllVehicles().size());
        assertDoesNotThrow(() -> vehicleService.getVehicle(v1.getVin()));
        assertEquals(v1, vehicleService.getVehicle(v1.getVin()));
    }


    @Test
    void removeVehicle_TEST_OK() {
        Vehicle v1 = vehicle1();
        vehicleService.createVehicle(v1.getManufacturerName(), v1.getModelName(), v1.getBaseLoanPrice(), v1.getVin(), v1.getLicencePlate());
        int numberOfVehicles = vehicleService.getAllVehicles().size();

        assertDoesNotThrow(() -> vehicleService.getVehicle(v1.getVin()));
        vehicleService.removeVehicle(v1.getVin());

        assertThatThrownBy(() -> vehicleService.getVehicle(v1.getVin())).hasMessage("Vehicle with provided vin was not found.");
        assertEquals(numberOfVehicles - 1, vehicleService.getAllVehicles().size());
    }

    @Test
    void removeVehicle_TEST_NO_SUCH_VEHICLE() {
        Vehicle v1 = vehicle1();

        assertThatThrownBy(() -> vehicleService.getVehicle(v1.getVin())).hasMessage("Vehicle with provided vin was not found.");
        assertThatThrownBy(() -> vehicleService.removeVehicle(v1.getVin())).hasMessage("Cannot delete vehicle as it was not found.");
    }

    @Test
    void updateVehicle_TEST_OK() {
        Vehicle v1 = vehicle1();
        vehicleService.createVehicle(v1.getManufacturerName(), v1.getModelName(), v1.getBaseLoanPrice(), v1.getVin(), v1.getLicencePlate());
        Vehicle oldVehicle = vehicleService.getVehicle(v1.getVin());

        v1.setBaseLoanPrice(123123123.12);
        vehicleService.updateVehicle(v1);

        assertEquals(oldVehicle.getVin(), v1.getVin());
        assertNotEquals(oldVehicle.getBaseLoanPrice(), v1.getBaseLoanPrice());
    }

    @Test
    void getVehicle_TEST_OK() {
        Vehicle v1 = vehicle1();
        vehicleService.createVehicle(v1.getManufacturerName(), v1.getModelName(), v1.getBaseLoanPrice(), v1.getVin(), v1.getLicencePlate());

        assertDoesNotThrow(() -> vehicleService.getVehicle(v1.getVin()));

        assertEquals(v1, vehicleService.getVehicle(v1.getVin()));
    }

    @Test
    void getVehicle_TEST_NOT_FOUND() {
        assertThatThrownBy(() -> vehicleService.getVehicle("I don't exist")).hasMessage("Vehicle with provided vin was not found.");
    }

    @Test
    void getAllVehicles_TEST_OK() {
        List<Vehicle> vehicleList = Arrays.asList(
                vehicle1(),
                new Vehicle("Opel", "Astra", 1231235.12, "GHRIOIGBASKGBQ1K", "PZ 204GH"),
                new Vehicle("Ferrari", "458 Italia", 1.99, "BKAKGNQNIGN33BU62", "US 193C")
        );

        vehicleList.forEach(vehicle -> vehicleService.createVehicle(
                vehicle.getManufacturerName(), vehicle.getModelName(), vehicle.getBaseLoanPrice(), vehicle.getVin(), vehicle.getLicencePlate())
        );


        assertThat(vehicleService.getAllVehicles()).containsAll(vehicleList);
        assertEquals(vehicleList.size(), vehicleService.getAllVehicles().size());
    }
}
