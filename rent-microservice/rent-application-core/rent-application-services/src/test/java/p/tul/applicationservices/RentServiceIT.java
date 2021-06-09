package p.tul.applicationservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import p.tul.domainmodel.entities.Car;
import p.tul.domainmodel.entities.Client;
import p.tul.domainmodel.entities.Rent;
import p.tul.repositoryadapter.adapters.RentListRepositoryAdapter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class RentServiceIT {

    private RentService rentService;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static Client admin() {
        return new Client("admin@mail.com", "admin", true, 16391.2);
    }

    private static Client client1() {
        return new Client("client1@mail.com", "client1", true, 173911.2);
    }

    private static Client client2() {
        return new Client("client2@mail.com", "client2", true, 5000);
    }

    private static Car vehicle1() {
        return new Car(250d, "ZFF67NFA0D0189338");
    }

    private static Car vehicle2() {
        return new Car(240d, "ZA9DU01B2XLA12304");
    }

    private static Rent rent1() {
        return new Rent(UUID.fromString("4f1f6e03-ee95-4d1e-a513-07197389767c"), client1(), vehicle1());
    }

    private static Rent rent2() {
        return new Rent(UUID.fromString("3002dfce-3a10-4811-8265-74f49332696e"), client2(), vehicle2(), LocalDateTime.now());
    }

    private LocalDateTime parseDate(String date) {
        return LocalDate.parse(date, dateTimeFormatter).atStartOfDay();
    }

    @BeforeEach
    void setUp() {
        rentService = new RentService(new RentListRepositoryAdapter());
    }

    @Test
    void createRent_TEST_OK() {
        assertThat(rentService.getRentsForAccount(client1().getLogin()), is(empty()));
        assertDoesNotThrow(() -> rentService.createRent(client1(), vehicle1()));
        assertThat(rentService.getRentsForAccount(client1().getLogin()), hasSize(1));
    }

    @Test
    void createRentWithStartDate_TEST_OK() {
        assertThat(rentService.getRentsForAccount(client1().getLogin()), is(empty()));
        assertDoesNotThrow(() -> rentService.createRent(client1(), vehicle1(), parseDate("22/02/2021")));
        assertThat(rentService.getRentsForAccount(client1().getLogin()), hasSize(1));
    }

    @Test
    void createRentWithStartEndDate_TEST_OK() {
        assertThat(rentService.getRentsForAccount(client1().getLogin()), is(empty()));
        assertDoesNotThrow(() -> rentService.createRent(client1(), vehicle1(), parseDate("22/02/2021"), parseDate("25/02/2021")));
        assertThat(rentService.getRentsForAccount(client1().getLogin()), hasSize(1));
    }

    @Test
    void endRent_TEST_OK() {
        rentService.createRent(client1(), vehicle1(), parseDate("29/03/2021"));

        Rent rent = rentService.getRentsForAccount(client1().getLogin()).get(0);
        assertThat(rentService.getRent(rent.getUuid()).getEndDate(), is(nullValue()));

        assertDoesNotThrow(() -> rentService.endRent(rent));

        assertThat(rentService.getRent(rent.getUuid()).getEndDate(), is(notNullValue()));
    }

    @Test
    void endRentWithEndDate_TEST_OK() {
        rentService.createRent(client1(), vehicle1(), parseDate("29/03/2021"));

        Rent rent = rentService.getRentsForAccount(client1().getLogin()).get(0);
        assertThat(rentService.getRent(rent.getUuid()).getEndDate(), is(nullValue()));

        assertDoesNotThrow(() -> rentService.endRent(rent, parseDate("31/03/2021")));

        assertThat(rentService.getRent(rent.getUuid()).getEndDate(), is(notNullValue()));
    }

    @Test
    void updateRent_TEST_OK() {
        rentService.createRent(client1(), vehicle1(), parseDate("29/03/2021"), parseDate("31/03/2021"));

        Rent rent = rentService.getRentsForAccount(client1().getLogin()).get(0);
        rent.setVehicle(vehicle2());

        assertDoesNotThrow(() -> rentService.updateRent(rent));
        assertThat(rentService.getRent(rent.getUuid()).getVehicle(), is(equalTo(vehicle2())));
    }

    @Test
    void getRent_TEST_OK() {
        rentService.createRent(client1(), vehicle1());
        rentService.createRent(client2(), vehicle2());

        Rent rent = rentService.getRentsForAccount(client1().getLogin()).get(0);

        assertThat(rentService.getRent(rent.getUuid()), is(equalTo(rent)));
    }

    @Test
    void getAllRents_TEST_OK() {
        rentService.createRent(client1(), vehicle1());
        rentService.createRent(client2(), vehicle2());

        assertThat(rentService.getAllRents(), hasSize(2));
    }

    @Test
    void getRentsForAccount_TEST_OK() {
        rentService.createRent(client1(), vehicle1());
        rentService.createRent(client1(), vehicle2());

        assertThat(rentService.getRentsForAccount(client1().getLogin()), hasSize(2));
    }

    @Test
    void getRentsForVehicle_TEST_OK() {
        rentService.createRent(client1(), vehicle1());
        rentService.createRent(client2(), vehicle1());

        assertThat(rentService.getRentsForVehicle(vehicle1().getVin()), hasSize(2));
    }
}