package mappers;

import p.lodz.tul.entities.Vehicle;
import p.lodz.tul.entities.VehicleEnt;
import org.junit.jupiter.api.Test;
import p.lodz.tul.mappers.VehicleMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VehicleMapperTest {

    @Test
    void toVehicleTest() {
        VehicleEnt vehicleEnt = new VehicleEnt("Kia", "Ceed", 123321.12, "MDHFBUK13U0820078", "EL 1741HF");
        Vehicle vehicle = VehicleMapper.toVehicle(vehicleEnt);

        assertEquals(vehicleEnt.getBaseLoanPrice(), vehicle.getBaseLoanPrice());
        assertEquals(vehicleEnt.getLicencePlate(), vehicle.getLicencePlate());
        assertEquals(vehicleEnt.getManufacturerName(), vehicle.getManufacturerName());
        assertEquals(vehicleEnt.getModelName(), vehicle.getModelName());
        assertEquals(vehicleEnt.getVin(), vehicle.getVin());
    }

    @Test
    void toVehicleEntTest() {
        Vehicle vehicle = new Vehicle("Ford", "Fiesta", 1213.12, "GHSJGHS12FASDFGASDF", "WW 12729GH");
        VehicleEnt vehicleEnt = VehicleMapper.toVehicleEnt(vehicle);

        assertEquals(vehicle.getBaseLoanPrice(), vehicleEnt.getBaseLoanPrice());
        assertEquals(vehicle.getLicencePlate(), vehicleEnt.getLicencePlate());
        assertEquals(vehicle.getManufacturerName(), vehicleEnt.getManufacturerName());
        assertEquals(vehicle.getModelName(), vehicleEnt.getModelName());
        assertEquals(vehicle.getVin(), vehicleEnt.getVin());
    }

    @Test
    void twoWaysConversionTest() {
        Vehicle vehicle = new Vehicle("Ford", "Fiesta", 1213.12, "GHSJGHS12FASDFGASDF", "WW 12729GH");
        VehicleEnt vehicleEnt = new VehicleEnt("Kia", "Ceed", 123321.12, "MDHFBUK13U0820078", "EL 1741HF");

        Vehicle convertedVehicle = VehicleMapper.toVehicle(VehicleMapper.toVehicleEnt(vehicle));
        VehicleEnt convertedVehicleEnt = VehicleMapper.toVehicleEnt(VehicleMapper.toVehicle(vehicleEnt));

        assertEquals(vehicle, convertedVehicle);
        assertEquals(vehicleEnt, convertedVehicleEnt);
    }
}
