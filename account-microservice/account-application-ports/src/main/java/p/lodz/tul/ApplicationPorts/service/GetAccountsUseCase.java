package p.lodz.tul.ApplicationPorts.service;

import p.lodz.tul.DomainModel.Entities.Account;

import java.util.List;

public interface GetAccountsUseCase {
    Account getAccount(String login);

    List<Account> getAllAccounts();
}
