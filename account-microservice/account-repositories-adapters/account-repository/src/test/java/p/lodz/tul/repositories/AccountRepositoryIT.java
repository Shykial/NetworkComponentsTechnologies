package p.lodz.tul.repositories;

import p.lodz.tul.dbEntities.AccountEnt;
import p.lodz.tul.dbEntities.AddressEnt;
import p.lodz.tul.dbEntities.LevelOfAccessEnt;
import p.lodz.tul.dbEntities.accessLevels.AdminEnt;
import p.lodz.tul.dbEntities.accessLevels.ClientEnt;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class AccountRepositoryIT {

    private final AccountRepository persistentRepo;
    private AccountRepository repository;

    public AccountRepositoryIT() {
        this.persistentRepo = new AccountRepository();
    }

    @BeforeEach
    void setUp() {
        repository = new AccountRepository();
    }

    @ParameterizedTest(name = "{index} {0} is being added.")
    @CsvFileSource(resources = "/accounts.csv", numLinesToSkip = 1)
    void addAccount_TEST_OK(@AggregateWith(AccountAggregator.class) AccountEnt account) {
        assertDoesNotThrow(() -> repository.addAccount(account));
        assertThat(repository).contains(account);

        persistentRepo.addAccount(account);
        assertThat(repository).doesNotHaveDuplicates();
    }

    @ParameterizedTest(name = "{index} adding account with null argument value.")
    @NullSource
    void addAccount_NULL_VALUE(AccountEnt account) {
        RepositoryException exception = assertThrows(RepositoryException.class, () -> repository.addAccount(account));
        assertThat(exception).hasMessage(RepositoryException.NULL_VALUE);
    }

    @ParameterizedTest(name = "{index} {0} is being added.")
    @CsvFileSource(resources = "/accounts.csv", numLinesToSkip = 1)
    void addAccount_DUPLICATED_VALUE(@AggregateWith(AccountAggregator.class) AccountEnt account) {
        assertDoesNotThrow(() -> repository.addAccount(account));
        AccountEnt duplicatedAccount = new AccountEnt("new-mail@mail.com", account.getLogin(), "haslo", true, account.getLevelOfAccess());
        
        assertThatThrownBy(
                () -> repository.addAccount(duplicatedAccount))
                .isInstanceOf(RepositoryException.class)
                .hasMessage(RepositoryException.ENTITY_EXISTS);
    }

    @Test
    void removeAccountByAccount_TEST_OK() {
        initTestSet(repository);
        int initialRepoSize = repository.getAllAccounts().size();
        
        AccountEnt account = new AccountEnt("jkowalski@mail.com", "account2", "admin1", false, new AdminEnt("12345"));
        
        assertDoesNotThrow(() -> repository.removeAccount(account));
        assertThat(repository).hasSize(initialRepoSize - 1);
    }

    @Test
    void removeAccountByLogin_TEST_OK() {
        initTestSet(repository);
        int initialRepoSize = repository.getAllAccounts().size();

        assertDoesNotThrow(() -> repository.removeAccount("account2"));
        assertThat(repository).hasSize(initialRepoSize - 1);
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void removeAccount_NULL_VALUE() {
        initTestSet(repository);
        int initialRepoSize = repository.getAllAccounts().size();

        AccountEnt nullAccount = null;
        assertThatExceptionOfType(
                RepositoryException.class)
                .isThrownBy(() -> repository.removeAccount(nullAccount))
                .withMessage(RepositoryException.NULL_VALUE);
        assertThat(repository).hasSize(initialRepoSize);
    }
    
    @Test
    void removeAccountByLogin_NOT_FOUND() {
        int initialRepoSize = repository.getAllAccounts().size();

        assertThatExceptionOfType(RepositoryException.class)
                .isThrownBy(() -> repository.removeAccount("nonExistingAccountLogin"))
                .withMessage(RepositoryException.ENTITY_NOT_FOUND);
        assertThat(repository).hasSize(initialRepoSize);
    }

    @Test
    void updateAccount_TEST_OK() {
        initTestSet(repository);
        
        AccountEnt account = repository.getAccount("account1").get();
        String newPassword = "newPassword";
        AccountEnt newAccount = new AccountEnt(account.getEmail(), account.getLogin(), newPassword, account.isActive(), account.getLevelOfAccess());
        
        assertDoesNotThrow(() -> repository.updateAccount(newAccount.getLogin(), newAccount));
        assertThat(repository.getAccount("account1").get().getPassword()).isEqualTo(newPassword);
        assertThat(repository).contains(newAccount);
    }

    @Test
    void updateAccount_NULL_VALUE() {
        initTestSet(repository);

        AccountEnt account = repository.getAccount("account1").get();

        assertThatExceptionOfType(RepositoryException.class)
                .isThrownBy(() -> repository.updateAccount(account.getLogin(), null))
                .withMessage(RepositoryException.NULL_VALUE);
    }

    @Test
    void updateAccount_NOT_FOUND() {
        initTestSet(repository);

        AccountEnt account = repository.getAccount("account1").get();
        String nonExistingLogin = "nonExistingLogin";
        
        assertThat(repository).extracting(AccountEnt::getLogin).doesNotContain(nonExistingLogin);

        assertThatExceptionOfType(RepositoryException.class)
                .isThrownBy(() -> repository.updateAccount(nonExistingLogin, account))
                .withMessage(RepositoryException.ENTITY_NOT_FOUND);
    }

    @Test
    void getAccount_TEST_OK() {
        initTestSet(repository);

        String existingLogin = "account1";
        
        assertThat(repository).extracting(AccountEnt::getLogin).contains(existingLogin);
        Optional<AccountEnt> accountFound = repository.getAccount(existingLogin);
        assertTrue(accountFound.isPresent());
    }

    @Test
    void getAllAccounts_TEST_OK() {
        initTestSet(repository);

        String existingLogin = "account1";
        
        assertThat(repository).hasSize(3).contains(repository.getAccount(existingLogin).get());
        
        List<AccountEnt> accounts = repository.getAllAccounts();
        assertThat(accounts).hasSize(3).extracting(AccountEnt::getLogin).contains(existingLogin);
    }
    
    private void initTestSet(AccountRepository testRepository) {
        AccountEnt account1 = new AccountEnt("account1@mail.com", "account1", "haslo", true, new AdminEnt("1234"));
        AccountEnt account2 = new AccountEnt("account2@mail.com", "account2", "haslo", false, new AdminEnt("1234"));
        AccountEnt account3 = new AccountEnt(
                "account3@mail.com", "account3", "haslo", true,
                new ClientEnt("Jan", "Kowalski",
                        new AddressEnt("High Street 1", "London", "123456789"), 10000));

        testRepository.addAccount(account1);
        testRepository.addAccount(account2);
        testRepository.addAccount(account3);
    }

    public static class AccountAggregator implements ArgumentsAggregator {

        @Override
        public AccountEnt aggregateArguments(ArgumentsAccessor aa, ParameterContext pc)
                throws ArgumentsAggregationException {
            String[] parts = (String[]) aa.toArray();
            String accessLevel = parts[4];
            return accessLevel.equalsIgnoreCase("ADMIN") ? aggregateAdmin(parts) : aggregateClient(parts);
        }

        public AccountEnt aggregateAdmin(String[] parts)
                throws ArgumentsAggregationException {
            String email = parts[0];
            String login = parts[1];
            String password = parts[2];
            boolean active = Boolean.parseBoolean(parts[3]);
            
            String adminCode = parts[5];
            LevelOfAccessEnt levelOfAccess = new AdminEnt(adminCode);

            return new AccountEnt(email, login, password, active, levelOfAccess);
        }

        public AccountEnt aggregateClient(String[] parts)
                throws ArgumentsAggregationException {
            String email = parts[0];
            String login = parts[1];
            String password = parts[2];
            boolean active = Boolean.parseBoolean(parts[3]);
            
            String firstName = parts[5];
            String lastName = parts[6];
            
            String streetName = parts[7];
            String cityName = parts[8];
            String phoneNumber = parts[9];
            AddressEnt address = new AddressEnt(streetName, cityName, phoneNumber);
            
            double amountOfMoney = Double.parseDouble(parts[10]);
            LevelOfAccessEnt levelOfAccess = new ClientEnt(firstName, lastName, address, amountOfMoney);
            
            return new AccountEnt(email, login, password, active, levelOfAccess);
        }
    }


}