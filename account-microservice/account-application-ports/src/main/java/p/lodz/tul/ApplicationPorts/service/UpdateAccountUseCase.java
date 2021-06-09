package p.lodz.tul.ApplicationPorts.service;

import p.lodz.tul.domainmodel.entities.Account;

public interface UpdateAccountUseCase {
    Account updateAccount(Account account);
}