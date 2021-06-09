package p.tul.applicationports.service;

import p.tul.domainmodel.entities.Client;
import p.tul.domainmodel.exceptions.AccountException;

public interface CreateAccountUseCase {
    Client createAccount(String email, String login, boolean active, double amountOfMoney) throws AccountException;
}
