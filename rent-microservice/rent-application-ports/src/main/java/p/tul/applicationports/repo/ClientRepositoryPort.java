package p.tul.applicationports.repo;

import p.tul.domainmodel.entities.Client;
import p.tul.domainmodel.exceptions.AccountException;

import java.util.List;

public interface ClientRepositoryPort {

    void addClient(Client account) throws AccountException;

    void updateClient(String login, Client account);

    Client getClient(String login) throws AccountException;

    boolean loginExists(String login);

    List<Client> getAllClients();
}
