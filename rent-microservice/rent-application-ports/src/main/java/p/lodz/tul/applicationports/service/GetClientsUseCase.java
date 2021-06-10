package p.lodz.tul.applicationports.service;

import p.lodz.tul.domainmodel.entities.Client;
import p.lodz.tul.domainmodel.exceptions.AccountException;

import java.util.List;

public interface GetClientsUseCase {
    Client getClient(String login) throws AccountException;

    List<Client> getAllClients();
}
