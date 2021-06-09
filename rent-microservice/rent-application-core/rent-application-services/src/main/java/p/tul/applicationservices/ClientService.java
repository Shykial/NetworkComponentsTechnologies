package p.tul.applicationservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import p.tul.applicationports.repo.ClientRepositoryPort;
import p.tul.applicationports.service.CreateAccountUseCase;
import p.tul.applicationports.service.GetAccountsUseCase;
import p.tul.applicationports.service.UpdateAccountUseCase;
import p.tul.domainmodel.entities.Client;
import p.tul.domainmodel.exceptions.AccountException;

import java.util.List;

@Service
public class ClientService implements CreateAccountUseCase, UpdateAccountUseCase, GetAccountsUseCase {
    private final ClientRepositoryPort clientRepositoryPort;

    @Autowired
    public ClientService(ClientRepositoryPort clientRepositoryPort) {
        this.clientRepositoryPort = clientRepositoryPort;
    }

    @Override
    public Client createAccount(String email, String login, boolean active, double amountOfMoney) throws AccountException {
        Client client = new Client(email, login, active, amountOfMoney);
        clientRepositoryPort.addClient(client);
        return client;
    }

    @Override
    public Client getClient(String login) throws AccountException {
        return clientRepositoryPort.getClient(login);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepositoryPort.getAllClients();
    }

    @Override
    public Client updateClient(Client client) {
        clientRepositoryPort.updateClient(client.getLogin(), client);
        return client;
    }
}
