package ApplicationPorts.service;

import DomainModel.Entities.Account;

public interface UpdateAccountUseCase {
    Account updateAccount(Account account);
}