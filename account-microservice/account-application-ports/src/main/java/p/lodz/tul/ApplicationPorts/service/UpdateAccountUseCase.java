package p.lodz.tul.ApplicationPorts.service;

import p.lodz.tul.DomainModel.Entities.Account;

public interface UpdateAccountUseCase {
    Account updateAccount(Account account);
}