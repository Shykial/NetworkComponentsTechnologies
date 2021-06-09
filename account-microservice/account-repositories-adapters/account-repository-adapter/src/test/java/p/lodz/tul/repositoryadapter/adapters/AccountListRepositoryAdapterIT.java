package p.lodz.tul.repositoryadapter.adapters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import p.lodz.tul.DomainModel.Entities.Account;
import p.lodz.tul.DomainModel.Entities.accessLevels.Admin;
import p.lodz.tul.DomainModel.Exceptions.AccountException;
import p.lodz.tul.repositories.AccountRepository;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

class AccountListRepositoryAdapterIT {

    private AccountListRepositoryAdapter adapter;

    private static Account account1() {
        return new Account("account1@mail.com", "account1",
                "haslo", true, new Admin("1234"));
    }

    private static Account account2() {
        return new Account("account2@mail.com", "account2",
                "haslo", true, new Admin("1111"));
    }

    @BeforeEach
    void setUp() {
        adapter = new AccountListRepositoryAdapter(new AccountRepository());
    }

    @Test
    void addAccount_TEST_OK() {
        Account account = account1();
        Assertions.assertTrue(adapter.getAllAccounts().isEmpty());
        assertDoesNotThrow(() -> adapter.addAccount(account));
        Assertions.assertTrue(adapter.getAllAccounts().contains(account));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void addAccount_NULL_VALUE() {
        Account account = null;
        Assertions.assertTrue(adapter.getAllAccounts().isEmpty());
        assertThatThrownBy(
                () -> adapter.addAccount(account))
                .isInstanceOf(AccountException.class)
                .hasMessage(AccountException.NULL_VALUE);
        Assertions.assertFalse(adapter.getAllAccounts().contains(account));
    }

    @Test
    void addAccount_DUPLICATED_VALUE() {
        Account account = account1();

        assertDoesNotThrow(() -> adapter.addAccount(account));
        Assertions.assertTrue(adapter.getAllAccounts().contains(account));
        assertThatThrownBy(
                () -> adapter.addAccount(account))
                .isInstanceOf(AccountException.class)
                .hasMessage(AccountException.ACCOUNT_EXISTS);
    }

    @Test
    void removeAccountByAccount_TEST_OK() {
        Account account1 = account1();
        Account account = account1();

        adapter.addAccount(account1);
        assertDoesNotThrow(() -> adapter.removeAccount(account));
        assertFalse(adapter.getAllAccounts().contains(account));
    }

    @Test
    void removeAccountByLogin_TEST_OK() {
        Account account = account1();

        adapter.addAccount(account);
        assertDoesNotThrow(() -> adapter.removeAccount(account.getLogin()));
        assertFalse(adapter.getAllAccounts().contains(account));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void removeAccountByAccount_NULL_VALUE() {
        Account account = account1();
        Account nullAccount = null;
        adapter.addAccount(account);

        assertThatThrownBy(
                () -> adapter.removeAccount(nullAccount))
                .isInstanceOf(AccountException.class)
                .hasMessage(AccountException.NULL_VALUE);
        assertTrue(adapter.getAllAccounts().contains(account));
    }

    @Test
    void removeAccountByAccount_NOT_FOUND() {
        Account account = account1();
        assertFalse(adapter.getAllAccounts().contains(account));

        assertThatThrownBy(
                () -> adapter.removeAccount(account))
                .isInstanceOf(AccountException.class)
                .hasMessage(AccountException.ACCOUNT_NOT_FOUND);
    }

    @Test
    void removeAccountByLogin_NOT_FOUND() {
        Account account = account1();
        assertFalse(adapter.getAllAccounts().contains(account));

        assertThatThrownBy(
                () -> adapter.removeAccount(account.getLogin()))
                .isInstanceOf(AccountException.class)
                .hasMessage(AccountException.ACCOUNT_NOT_FOUND);
    }

    @Test
    void updateAccount_TEST_OK() throws AccountException {
        Account account1 = account1();
        String newPassword = "newPassword123";
        assertNotEquals(newPassword, account1.getPassword());

        adapter.addAccount(account1);

        Account account2 = account1();
        account2.setPassword(newPassword);
        adapter.updateAccount(account1.getLogin(), account2);

        assertEquals(adapter.getAccount(account1.getLogin()).getPassword(), newPassword);
    }

    @Test
    void updateAccount_NULL_VALUE() {
        Account account1 = account1();
        adapter.addAccount(account1);

        assertThatThrownBy(
                () -> adapter.updateAccount(account1.getLogin(), null))
                .isInstanceOf(AccountException.class)
                .hasMessage(AccountException.NULL_VALUE);
    }

    @Test
    void updateAccount_NOT_FOUND() {
        Account account1 = account1();
        Account account2 = account1();
        String newPassword = "newPassword123";
        account2.setPassword(newPassword);

        assertThatThrownBy(
                () -> adapter.updateAccount(account1.getLogin(), account2))
                .isInstanceOf(AccountException.class)
                .hasMessage(AccountException.ACCOUNT_NOT_FOUND);
    }

    @Test
    void getAccount_TEST_OK() {
        Account account1 = account1();
        adapter.addAccount(account1);

        Account accountFound = assertDoesNotThrow(() -> adapter.getAccount(account1.getLogin()));

        assertEquals(accountFound, account1);
    }

    @Test
    void getAccount_NOT_FOUND() {
        Account account1 = account1();

        assertThatThrownBy(
                () -> adapter.getAccount(account1.getLogin()))
                .isInstanceOf(AccountException.class)
                .hasMessage(AccountException.ACCOUNT_NOT_FOUND);
    }

    @Test
    void getAllAccounts_TEST_OK() {
        Account account1 = account1();
        Account account2 = account2();

        adapter.addAccount(account1);
        adapter.addAccount(account2);

        List<Account> accountsFound = adapter.getAllAccounts();

        org.hamcrest.MatcherAssert.assertThat(accountsFound, hasSize(2));
        org.hamcrest.MatcherAssert.assertThat(accountsFound, hasItem(account1));
        org.hamcrest.MatcherAssert.assertThat(accountsFound, hasItem(account1));
    }

    @Test
    void loginExists_TEST_OK() {
        Account account = account1();
        adapter.addAccount(account);
        assertTrue(adapter.loginExists(account.getLogin()));
    }

    @Test
    void loginExists_TEST_NOT_OK() {
        Account account = account1();
        assertFalse(adapter.loginExists(account.getLogin()));
    }
}