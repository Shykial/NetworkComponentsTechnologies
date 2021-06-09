package p.lodz.tul.applicationports.service;

import p.lodz.tul.domainmodel.entities.Client;
import p.lodz.tul.domainmodel.exceptions.AccountException;

public interface CreateAccountUseCase {
    Client createAccount(String email, String login, boolean active, double amountOfMoney) throws AccountException;
}
