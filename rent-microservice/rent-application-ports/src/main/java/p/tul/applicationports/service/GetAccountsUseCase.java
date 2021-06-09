package p.tul.applicationports.service;

import p.tul.domainmodel.entities.Client;
import p.tul.domainmodel.exceptions.AccountException;

import java.util.List;

public interface GetAccountsUseCase {
    Client getClient(String login) throws AccountException;

    List<Client> getAllClients();
}
