package p.lodz.tul.repositoryAdapter.adapters;

import p.lodz.tul.ApplicationPorts.repo.AccountRepositoryPort;
import p.lodz.tul.DomainModel.Entities.Account;
import p.lodz.tul.DomainModel.Entities.Address;
import p.lodz.tul.DomainModel.Entities.accessLevels.Admin;
import p.lodz.tul.DomainModel.Entities.accessLevels.Client;
import p.lodz.tul.DomainModel.Exceptions.AccountException;
import p.lodz.tul.dbEntities.AccountEnt;
import lombok.SneakyThrows;
import p.lodz.tul.repositoryAdapter.mappers.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import p.lodz.tul.repositories.AccountRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class AccountListRepositoryAdapter implements AccountRepositoryPort {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountListRepositoryAdapter(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountListRepositoryAdapter() {
        accountRepository = new AccountRepository();
    }

    @SneakyThrows
    @Override
    public void addAccount(Account account) {
        if (account == null) {
            throw new AccountException(AccountException.NULL_VALUE);
        }

        try {
            accountRepository.addAccount(AccountMapper.toAccountEnt(account));
        } catch (Exception e) {
            throw new AccountException(AccountException.ACCOUNT_EXISTS);
        }
    }

    @SneakyThrows
    @Override
    public void removeAccount(Account account) {
        if (account == null) {
            throw new AccountException(AccountException.NULL_VALUE);
        }

        try {
            accountRepository.removeAccount(AccountMapper.toAccountEnt(account));
        } catch (Exception e) {
            throw new AccountException(AccountException.ACCOUNT_NOT_FOUND);
        }
    }

    @SneakyThrows
    @Override
    public void removeAccount(String login) {
        try {
            accountRepository.removeAccount(login);
        } catch (Exception e) {
            throw new AccountException(AccountException.ACCOUNT_NOT_FOUND);
        }
    }

    @SneakyThrows
    @Override
    public void updateAccount(String login, Account account) {
        if (account == null) {
            throw new AccountException(AccountException.NULL_VALUE);
        }

        try {
            accountRepository.updateAccount(login, AccountMapper.toAccountEnt(account));
        } catch (Exception e) {
            throw new AccountException(AccountException.ACCOUNT_NOT_FOUND);
        }
    }

    @Override
    public Account getAccount(String login) throws AccountException {
        Optional<AccountEnt> accountEntOptional = accountRepository.getAccount(login);
        if (accountEntOptional.isEmpty()) {
            throw new AccountException(AccountException.ACCOUNT_NOT_FOUND);
        }

        return AccountMapper.toAccount(accountEntOptional.get());
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.getAllAccounts().stream().map(AccountMapper::toAccount).collect(Collectors.toList());
    }

    @Override
    public boolean loginExists(String login) {
        return accountRepository.getAccount(login).isPresent();
    }

    //    @PostConstruct
    public void addTestData() {
        List<Account> accounts = Arrays.asList(
                new Account("michal@gmail.com", "client1", "michu_pass123",
                        new Client("Michał", "Jajeczny",
                                new Address("Polna", "Łódź", "274-174-178"), 1741.12)),
                new Account("jarek@interia.pl", "client2", "jarekpasswd",
                        new Client("Jarosław", "Kopytko",
                                new Address("Szklana", "Warszawa", "4791-174-183"), 17481.12)),
                new Account("miro@miroslaw.pl", "admin1", "admin1", new Admin("GAHLGKA301KHG")),
                new Account("joanna@joanna.pl", "adminka2", "admin2", new Admin("GHAKG018GJKL"))
        );

        for (Account account : accounts) {
            addAccount(account);
        }
    }
}
