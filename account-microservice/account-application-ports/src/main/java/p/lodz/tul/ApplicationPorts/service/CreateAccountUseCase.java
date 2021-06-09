package p.lodz.tul.ApplicationPorts.service;

import p.lodz.tul.domainmodel.entities.Account;
import p.lodz.tul.domainmodel.entities.LevelOfAccess;

public interface CreateAccountUseCase {
    Account createAccount(String email, String login, String password, LevelOfAccess accessLevel);

    Account createAccount(String email, String login, String password, boolean active, LevelOfAccess accessLevel);
}
