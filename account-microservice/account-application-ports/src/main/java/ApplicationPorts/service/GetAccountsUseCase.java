package ApplicationPorts.service;

import DomainModel.Entities.Account;

import java.util.List;

public interface GetAccountsUseCase {
    Account getAccount(String login);

    List<Account> getAllAccounts();
}
