package p.lodz.tul.ApplicationPorts.service;

import p.lodz.tul.domainmodel.entities.Account;

import java.util.List;

public interface GetAccountsUseCase {
    Account getAccount(String login);

    List<Account> getAllAccounts();
}
