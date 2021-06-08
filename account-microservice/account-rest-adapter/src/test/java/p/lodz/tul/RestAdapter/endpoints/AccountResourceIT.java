package p.lodz.tul.RestAdapter.endpoints;

import p.lodz.tul.RestAdapter.dto.AccountDTO;
import p.lodz.tul.RestAdapter.dto.AddressDTO;
import p.lodz.tul.RestAdapter.dto.accessLevels.ClientDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountResourceIT {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void registerClientTest() {
        AccountDTO account = getRandomLoginAccount();
        String accountJSONString = new JSONObject(account).toString();

        Response response = given().get("http://localhost:8080/car-rent/api/accounts/" + account.getLogin());

        assertEquals(404, response.getStatusCode()); //account was yet not registered

        response = given().contentType(JSON).body(accountJSONString)
                .post("http://localhost:8080/car-rent/api/accounts/account");

        assertEquals(201, response.getStatusCode()); //asserting account creation was successful

        response = given().get("http://localhost:8080/car-rent/api/accounts/" + account.getLogin());

        assertEquals(200, response.getStatusCode());
    }

    @Test
    void accountByLoginTest() throws JsonProcessingException {
        AccountDTO account = getRandomLoginAccount();
        String accountJSONString = objectMapper.writeValueAsString(account).replace("addressDTO", "address");

        Response response = given().get("http://localhost:8080/car-rent/api/accounts/" + account.getLogin());
        assertEquals(404, response.getStatusCode()); //account was yet not registered

        registerClient(account);

        JSONObject expectedAccount = new JSONObject(accountJSONString);
        expectedAccount.remove("password");
        //password will not be in the response

        response = given().get("http://localhost:8080/car-rent/api/accounts/" + account.getLogin());

        assertTrue(expectedAccount.similar(new JSONObject(response.getBody().asString())));
    }

    @Test
    void removeAccountTest() {
        AccountDTO account = getRandomLoginAccount();
        registerClient(account);

        Response response = given().get("http://localhost:8080/car-rent/api/accounts/" + account.getLogin());
        assertEquals(200, response.getStatusCode());

        given().delete("http://localhost:8080/car-rent/api/accounts/" + account.getLogin());

        response = given().get("http://localhost:8080/car-rent/api/accounts/" + account.getLogin());
        assertEquals(404, response.getStatusCode());
    }

    @Test
    void updateAccountTest() {
        AccountDTO account = getRandomLoginAccount();
        registerClient(account);

        Response response = given().get("http://localhost:8080/car-rent/api/accounts/" + account.getLogin());

        assertEquals(account.getEmail(), response.getBody().jsonPath().getString("email"));

        String newEmail = "new_email@gmail.com";
        account.setEmail(newEmail);
        JSONObject accountJSON = new JSONObject(account);

        response = given().contentType(JSON).body(accountJSON.toString())
                .put("http://localhost:8080/car-rent/api/accounts/account");

        assertEquals(200, response.getStatusCode());

        response = given().get("http://localhost:8080/car-rent/api/accounts/" + account.getLogin());
        assertEquals(newEmail, response.getBody().jsonPath().getString("email"));
    }

    private AccountDTO getRandomLoginAccount() {
        return new AccountDTO("michal@gmail.com", randomAlphanumeric(8), "michu_pass123", true,
                new ClientDTO("Michał", "Jajeczny",
                        new AddressDTO("Polna", "Łódź", "274-174-178"), 1741.12));
    }

    @SneakyThrows
    private void registerClient(AccountDTO account) {
        RequestSpecification request = given();
        request.contentType(JSON);
        request.body(objectMapper.writeValueAsString(account));
        request.post("http://localhost:8080/car-rent/api/accounts/account");
    }
}