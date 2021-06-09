package p.lodz.tul.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import p.lodz.tul.dto.VehicleDTO;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VehicleResourceIT {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void addVehicleTest() throws JsonProcessingException {
        VehicleDTO vehicle = getRandomVinVehicle();
        Response response = given().get("http://localhost:8080/car-rent/api/vehicles/" + vehicle.getVin());
        assertEquals(404, response.getStatusCode());

        response = given().contentType(ContentType.JSON).body(objectMapper.writeValueAsString(vehicle))
                .post("http://localhost:8080/car-rent/api/vehicles/vehicle");

        assertEquals(201, response.getStatusCode());

        response = given().get("http://localhost:8080/car-rent/api/vehicles/" + vehicle.getVin());
        assertEquals(200, response.getStatusCode());
    }

    @Test
    void vehicleByVinTest() throws JSONException {
        VehicleDTO vehicle = getRandomVinVehicle();

        Response response = given().get("http://localhost:8080/car-rent/api/vehicles/" + vehicle.getVin());
        assertEquals(404, response.getStatusCode());

        addVehicle(vehicle);

        response = given().get("http://localhost:8080/car-rent/api/vehicles/" + vehicle.getVin());
        assertEquals(200, response.getStatusCode());

        JSONObject expectedVehicle = new JSONObject(String.valueOf(vehicle));
        JSONObject responseJSON = new JSONObject(response.getBody().asString());
        responseJSON.put("baseLoanPrice", responseJSON.getDouble("baseLoanPrice"));

//        assertTrue(expectedVehicle.similar(responseJSON));
    }

    @Test
    void removeVehicleTest() {
        VehicleDTO vehicle = getRandomVinVehicle();
        addVehicle(vehicle);

        Response response = given().get("http://localhost:8080/car-rent/api/vehicles/" + vehicle.getVin());
        assertEquals(200, response.getStatusCode());

        response = given().delete("http://localhost:8080/car-rent/api/vehicles/" + vehicle.getVin());
        assertEquals(200, response.getStatusCode());

        response = given().get("http://localhost:8080/car-rent/api/vehicles/" + vehicle.getVin());
        assertEquals(404, response.getStatusCode());
    }

    @Test
    void updateVehicleTest() throws JsonProcessingException {
        VehicleDTO vehicle = getRandomVinVehicle();
        addVehicle(vehicle);

        Response response = given().get("http://localhost:8080/car-rent/api/vehicles/" + vehicle.getVin());
        assertEquals(vehicle.getModelName(), response.getBody().jsonPath().getString("modelName"));

        String newModelName = "Panamera";
        vehicle.setModelName(newModelName);

        response = given().contentType(ContentType.JSON).body(objectMapper.writeValueAsString(vehicle))
                .put("http://localhost:8080/car-rent/api/vehicles/vehicle");

        assertEquals(200, response.getStatusCode());

        response = given().get("http://localhost:8080/car-rent/api/vehicles/" + vehicle.getVin());
        assertEquals(newModelName, response.getBody().jsonPath().getString("modelName"));
    }

    @SneakyThrows
    private void addVehicle(VehicleDTO vehicle) {
        given().contentType(ContentType.JSON).body(objectMapper.writeValueAsString(vehicle))
                .post("http://localhost:8080/car-rent/api/vehicles/vehicle");
    }

    private VehicleDTO getRandomVinVehicle() {
        return new VehicleDTO("Porsche", "Carrera GT", 999999.12, randomAlphanumeric(12), randomAlphanumeric(8));
    }
}
