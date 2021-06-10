package p.lodz.tul.restadapter.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import p.lodz.tul.restadapter.dto.ClientDTO;
import p.lodz.tul.restadapter.dto.CarDTO;
import p.lodz.tul.restadapter.dto.RentDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.junit.jupiter.api.Assertions.*;

class RentResourceIT {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void addRentTest() {
        RentDTO rent = getRandomUUIDRent();
        JSONObject jsonObject = new JSONObject(Map.of(
                "account", rent.getClientDTO().getLogin(),
                "vehicle", rent.getCarDTO().getVin(),
                "startDate", rent.getStartDate(),
                "endDate", rent.getEndDate()
        ));

        registerClient(rent.getClientDTO());
        registerVehicle(rent.getCarDTO());

        Response response = given().get("http://localhost:8080/car-rent/api/rents/" + rent.getUuid().toString());
        assertEquals(404, response.getStatusCode());

        response = given().contentType(ContentType.JSON).body(jsonObject.toString()).post("http://localhost:8080/car-rent/api/rents/rent");

        assertEquals(201, response.getStatusCode());
    }

    @Test
    void rentByUUIDTest() {
        RentDTO rent = getRandomUUIDRent();
        JSONObject jsonObject = getJSONFromRent(rent);
        jsonObject.remove("uuid"); //removing uuid as new one will be created anyways

        Response response = given().get("http://localhost:8080/car-rent/api/rents/" + rent.getUuid().toString());
        assertEquals(404, response.getStatusCode());

        String rentUUID = registerRent(rent);

        response = given().get("http://localhost:8080/car-rent/api/rents/" + rentUUID);
        assertEquals(200, response.getStatusCode());
        JSONObject outcomeJSONobject = new JSONObject(response.getBody().asString());
        outcomeJSONobject.remove("uuid");
        assertTrue(jsonObject.similar(outcomeJSONobject));
    }

    @Test
    void updateRentTest() {
        RentDTO rent = getRandomUUIDRent();
        String rentUUID = registerRent(rent);
        rent = new RentDTO(UUID.fromString(rentUUID), rent.getClientDTO(), rent.getCarDTO(), rent.getStartDate(), rent.getEndDate());

        Response response = given().get("http://localhost:8080/car-rent/api/rents/" + rentUUID);
        assertEquals(rent.getEndDate(), LocalDateTime.parse(response.getBody().jsonPath().getString("endDate")));

        LocalDateTime newEndDate = LocalDateTime.now();
        rent.setEndDate(newEndDate);

        response = given().contentType(ContentType.JSON).body(getJSONFromRent(rent).toString())
                .put("http://localhost:8080/car-rent/api/rents/rent");

        assertEquals(200, response.getStatusCode());

        response = given().get("http://localhost:8080/car-rent/api/rents/" + rentUUID);
        assertTrue(getJSONFromRent(rent).similar(new JSONObject(response.getBody().asString())));
        assertEquals(newEndDate, LocalDateTime.parse(response.getBody().jsonPath().getString("endDate")));
    }

    @Test
    void endRentTest() {
        RentDTO rent = getRandomUUIDRent();
        rent.setEndDate(null);
        String rentUUID = registerRent(rent);
        rent = new RentDTO(UUID.fromString(rentUUID), rent.getClientDTO(), rent.getCarDTO(), rent.getStartDate(), rent.getEndDate());
        JSONObject rentJSONObject = getJSONFromRent(rent);
        rentJSONObject.remove("endDate"); //removing endDate as it will differ from outcome

        Response response = given().get("http://localhost:8080/car-rent/api/rents/" + rentUUID);
        assertNull(response.jsonPath().getString("endDate"));

        response = given().put("http://localhost:8080/car-rent/api/rents/endRent/" + rentUUID);
        assertEquals(200, response.getStatusCode());

        response = given().get("http://localhost:8080/car-rent/api/rents/" + rentUUID);
        assertNotNull(response.jsonPath().getString("endDate"));


        JSONObject outcomeJSONObject = new JSONObject(response.getBody().asString());
        outcomeJSONObject.remove("endDate");

        assertTrue(rentJSONObject.similar(outcomeJSONObject));
    }

    private RentDTO getRandomUUIDRent() {
        return RentDTO.builder().clientDTO(getRandomLoginAccount())
                .carDTO(getRandomVinVehicle())
                .startDate(LocalDate.parse("2020-12-12", dateTimeFormatter).atStartOfDay())
                .endDate(LocalDateTime.now()).build();
    }

    private ClientDTO getRandomLoginAccount() {
        return new ClientDTO("michal@gmail.com", randomAlphanumeric(8), true, 9999999.12);
    }

    private CarDTO getRandomVinVehicle() {
        return new CarDTO(999.12, randomAlphanumeric(12));
    }

    private String registerRent(RentDTO rent) {
        JSONObject jsonObject = getJSONFromRent(rent);

        registerClient(rent.getClientDTO());
        registerVehicle(rent.getCarDTO());

        Response response = given().contentType(ContentType.JSON).body(jsonObject.toString())
                .post("http://localhost:8080/car-rent/api/rents/rent");
        return response.asString();
    }

    @SneakyThrows
    private void registerClient(ClientDTO account) {
        RequestSpecification request = given();
        request.contentType(ContentType.JSON);
        request.body(objectMapper.writeValueAsString(account));
        request.post("http://localhost:8080/car-rent/api/accounts/account");
    }

    @SneakyThrows
    private void registerVehicle(CarDTO vehicle) {
        given().contentType(ContentType.JSON).body(objectMapper.writeValueAsString(vehicle))
                .post("http://localhost:8080/car-rent/api/vehicles/vehicle");
    }

    private JSONObject getJSONFromRent(RentDTO rent) {
        //wrapping values with JSONObject.wrap to avoid NPE and properly save null values
        return new JSONObject(Map.of(
                "uuid", JSONObject.wrap(rent.getUuid()),
                "account", JSONObject.wrap(rent.getClientDTO().getLogin()),
                "vehicle", JSONObject.wrap(rent.getCarDTO().getVin()),
                "startDate", JSONObject.wrap(rent.getStartDate()),
                "endDate", JSONObject.wrap(rent.getEndDate())
        ));
    }
}