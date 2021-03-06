package p.lodz.tul.ApplicationServices;

import p.lodz.tul.ApplicationPorts.repo.AccountRepositoryPort;
import p.lodz.tul.ApplicationPorts.service.CreateAccountUseCase;
import p.lodz.tul.ApplicationPorts.service.GetAccountsUseCase;
import p.lodz.tul.ApplicationPorts.service.RemoveAccountUseCase;
import p.lodz.tul.ApplicationPorts.service.UpdateAccountUseCase;
import p.lodz.tul.domainmodel.entities.Account;
import p.lodz.tul.domainmodel.entities.LevelOfAccess;
import p.lodz.tul.domainmodel.entities.accesslevels.Admin;
import p.lodz.tul.domainmodel.entities.accesslevels.Client;
import p.lodz.tul.domainmodel.exceptions.AccountException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService implements CreateAccountUseCase, UpdateAccountUseCase, RemoveAccountUseCase, GetAccountsUseCase {

    private AccountRepositoryPort accountRepository;

    @Autowired
    public AccountService(AccountRepositoryPort accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountService() {
    }

    @SneakyThrows
    @Override
    public Account createAccount(String email, String login, String password, LevelOfAccess accessLevel) {
        if (accountRepository.loginExists(login)) {
            throw new ServiceException("Account with this login already exists");
        }

        Account account = new Account(email, login, password, accessLevel);
        accountRepository.addAccount(account);
        return account;
    }

    @SneakyThrows
    @Override
    public Account createAccount(String email, String login, String password, boolean active, LevelOfAccess accessLevel) {
        if (accountRepository.loginExists(login)) {
            throw new ServiceException("Account with this login already exists");
        }

        Account account = new Account(email, login, password, active, accessLevel);
        accountRepository.addAccount(account);
        return account;
    }

    @SneakyThrows
    @Override
    public void removeAccount(String login) {
        if (!accountRepository.loginExists(login)) {
            throw new ServiceException("Cannot delete account as it was not found.");
        }

        accountRepository.removeAccount(login);
    }

    @SneakyThrows
    @Override
    public Account updateAccount(Account account) {
        String login = account.getLogin();
        accountRepository.updateAccount(login, account);
        return accountRepository.getAccount(login);
    }

    @SneakyThrows
    @Override
    public Account getAccount(String login) {
        try {
            return accountRepository.getAccount(login);
        } catch (AccountException e) {
            throw new ServiceException("Account with provided login was not found");
        }
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.getAllAccounts();
    }

    @SneakyThrows
    public boolean authenticate(Account account) {
        if (account == null) {
            throw new ServiceException(ServiceException.NULL_ARGUMENT);
        }
        return account.isActive() && accountRepository.loginExists(account.getLogin());
    }

    @SneakyThrows
    public boolean authorizeAsClient(Account account) {
        if (account == null) {
            throw new ServiceException(ServiceException.NULL_ARGUMENT);
        }

        return account.getLevelOfAccess() instanceof Client;
    }

    @SneakyThrows
    public boolean authorizeAsAdmin(Account account) {
        if (account == null) {
            throw new ServiceException(ServiceException.NULL_ARGUMENT);
        }

        return account.getLevelOfAccess() instanceof Admin;
    }
}
