package ApplicationPorts.service;

import DomainModel.Entities.Account;
import DomainModel.Entities.LevelOfAccess;

public interface CreateAccountUseCase {
    Account createAccount(String email, String login, String password, LevelOfAccess accessLevel);

    Account createAccount(String email, String login, String password, boolean active, LevelOfAccess accessLevel);
}
