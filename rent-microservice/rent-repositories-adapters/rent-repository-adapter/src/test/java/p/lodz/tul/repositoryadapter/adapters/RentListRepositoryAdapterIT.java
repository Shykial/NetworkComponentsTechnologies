package p.lodz.tul.repositoryadapter.adapters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import p.lodz.tul.domainmodel.entities.Car;
import p.lodz.tul.domainmodel.entities.Client;
import p.lodz.tul.domainmodel.entities.Rent;
import p.lodz.tul.domainmodel.exceptions.RentException;
import p.lodz.tul.repositories.RentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

class RentListRepositoryAdapterIT {

    private RentListRepositoryAdapter adapter;

    private static Client account1() {
        return new Client("account1@mail.com", "account1",
                true, 17301.12);
    }

    private static Client account2() {
        return new Client("account2@mail.com", "account2",
                true, 173911.77);
    }

    private static Car vehicle1() {
        return new Car(250d, "ZFF67NFA0D0189338");
    }

    private static Car vehicle2() {
        return new Car(240d, "ZA9DU01B2XLA12304");
    }

    private static Rent rent1() {
        return new Rent(UUID.fromString("4f1f6e03-ee95-4d1e-a513-07197389767c"), account1(), vehicle1(), LocalDateTime.now());
    }

    private static Rent rent2() {
        return new Rent(UUID.fromString("3002dfce-3a10-4811-8265-74f49332696e"), account2(), vehicle2(), LocalDateTime.now());
    }

    @BeforeEach
    void setUp() {
        adapter = new RentListRepositoryAdapter(new RentRepository());
    }

    @Test
    void addRent_TEST_OK() {
        Rent rent = rent1();
        assertTrue(adapter.getAllRents().isEmpty());
        assertDoesNotThrow(() -> adapter.addRent(rent));
        assertTrue(adapter.getAllRents().contains(rent));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void addRent_NULL_VALUE() {
        Rent rent = null;
        assertTrue(adapter.getAllRents().isEmpty());
        assertThatThrownBy(
                () -> adapter.addRent(rent))
                .isInstanceOf(RentException.class)
                .hasMessage(RentException.NULL_VALUE);
        assertFalse(adapter.getAllRents().contains(rent));
    }

    @Test
    void addRent_DUPLICATED_VALUE() {
        Rent rent = rent1();

        assertDoesNotThrow(() -> adapter.addRent(rent));
        assertTrue(adapter.getAllRents().contains(rent));
        assertThatThrownBy(
                () -> adapter.addRent(rent))
                .isInstanceOf(RentException.class)
                .hasMessage(RentException.RENT_EXISTS);
    }

    @Test
    void removeRentByRent_TEST_OK() {
        Rent rent1 = rent1();
        Rent rent2 = rent1();

        adapter.addRent(rent1);
        assertDoesNotThrow(() -> adapter.removeRent(rent2));
        assertFalse(adapter.getAllRents().contains(rent1));
    }

    @Test
    void removeRentByUuid_TEST_OK() {
        Rent rent = rent1();

        adapter.addRent(rent);
        assertDoesNotThrow(() -> adapter.removeRent(rent.getUuid()));
        assertFalse(adapter.getAllRents().contains(rent));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void removeRentByRent_NULL_VALUE() {
        Rent rent = rent1();
        Rent nullRent = null;
        adapter.addRent(rent);

        assertThatThrownBy(
                () -> adapter.removeRent(nullRent))
                .isInstanceOf(RentException.class)
                .hasMessage(RentException.NULL_VALUE);
        assertTrue(adapter.getAllRents().contains(rent));
    }

    @Test
    void removeRentByRent_NOT_FOUND() {
        Rent rent = rent1();
        assertFalse(adapter.getAllRents().contains(rent));

        assertThatThrownBy(
                () -> adapter.removeRent(rent))
                .isInstanceOf(RentException.class)
                .hasMessage(RentException.RENT_NOT_FOUND);
    }

    @Test
    void removeRentByUuid_NOT_FOUND() {
        Rent rent = rent1();
        assertFalse(adapter.getAllRents().contains(rent));

        assertThatThrownBy(
                () -> adapter.removeRent(rent.getUuid()))
                .isInstanceOf(RentException.class)
                .hasMessage(RentException.RENT_NOT_FOUND);
    }

    @Test
    void updateRent_TEST_OK() {
        Rent rent1 = rent1();
        LocalDateTime endDate = LocalDateTime.now();
        assertNotEquals(endDate, rent1.getEndDate());

        adapter.addRent(rent1);

        Rent rent2 = rent1();
        rent2.setEndDate(endDate);
        adapter.updateRent(rent1.getUuid(), rent2);

        assertEquals(adapter.getRent(rent1.getUuid()).getEndDate(), endDate);
    }

    @Test
    void updateRent_NULL_VALUE() {
        Rent rent1 = rent1();
        adapter.addRent(rent1);

        assertThatThrownBy(
                () -> adapter.updateRent(rent1.getUuid(), null))
                .isInstanceOf(RentException.class)
                .hasMessage(RentException.NULL_VALUE);
    }

    @Test
    void updateRent_NOT_FOUND() {
        Rent rent1 = rent1();
        Rent rent2 = rent2();
        LocalDateTime endDate = LocalDateTime.now();
        rent2.setEndDate(endDate);

        assertThatThrownBy(
                () -> adapter.updateRent(rent1.getUuid(), rent2))
                .isInstanceOf(RentException.class)
                .hasMessage(RentException.RENT_NOT_FOUND);
    }

    @Test
    void getRent_TEST_OK() {
        Rent rent1 = rent1();
        adapter.addRent(rent1);

        Rent rentFound = assertDoesNotThrow(() -> adapter.getRent(rent1.getUuid()));

        assertEquals(rentFound, rent1);
    }

    @Test
    void getRent_NOT_FOUND() {
        Rent rent1 = rent1();

        assertThatThrownBy(
                () -> adapter.getRent(rent1.getUuid()))
                .isInstanceOf(RentException.class)
                .hasMessage(RentException.RENT_NOT_FOUND);
    }

    @Test
    void getAllRents_TEST_OK() {
        Rent rent1 = rent1();
        Rent rent2 = rent2();

        adapter.addRent(rent1);
        adapter.addRent(rent2);

        List<Rent> rentFound = adapter.getAllRents();

        org.hamcrest.MatcherAssert.assertThat(rentFound, hasSize(2));
        org.hamcrest.MatcherAssert.assertThat(rentFound, hasItem(rent1));
        org.hamcrest.MatcherAssert.assertThat(rentFound, hasItem(rent1));
    }

    @Test
    void rentExists_TEST_OK() {
        Rent rent = rent1();
        adapter.addRent(rent);
        assertTrue(adapter.rentExists(rent.getUuid()));
    }

    @Test
    void rentExists_TEST_NOT_OK() {
        Rent rent = rent1();
        assertFalse(adapter.rentExists(rent.getUuid()));
    }
}