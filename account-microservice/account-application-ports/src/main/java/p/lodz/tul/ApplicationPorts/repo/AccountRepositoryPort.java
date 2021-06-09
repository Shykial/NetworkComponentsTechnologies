package p.lodz.tul.ApplicationPorts.repo;

import p.lodz.tul.domainmodel.entities.Account;
import p.lodz.tul.domainmodel.exceptions.AccountException;

import java.util.List;

public interface AccountRepositoryPort {

    void addAccount(Account account) throws AccountException;

    void removeAccount(Account account);

    void removeAccount(String login);

    void updateAccount(String login, Account account);

    Account getAccount(String login) throws AccountException;

    boolean loginExists(String login);

    List<Account> getAllAccounts();
}
