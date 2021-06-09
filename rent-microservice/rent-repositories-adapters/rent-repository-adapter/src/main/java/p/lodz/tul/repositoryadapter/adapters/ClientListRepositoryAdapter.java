package p.lodz.tul.repositoryadapter.adapters;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import p.lodz.tul.applicationports.repo.ClientRepositoryPort;
import p.lodz.tul.dbentities.AccountEnt;
import p.lodz.tul.domainmodel.entities.Client;
import p.lodz.tul.domainmodel.exceptions.AccountException;
import p.lodz.tul.repositories.ClientRepository;
import p.lodz.tul.repositoryadapter.mappers.AccountMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ClientListRepositoryAdapter implements ClientRepositoryPort {

    private final ClientRepository accountRepository;

    @Autowired
    public ClientListRepositoryAdapter(ClientRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public ClientListRepositoryAdapter() {
        accountRepository = new ClientRepository();
    }

    @SneakyThrows
    @Override
    public void addClient(Client client) {
        if (client == null) {
            throw new AccountException(AccountException.NULL_VALUE);
        }

        try {
            accountRepository.addAccount(AccountMapper.toAccountEnt(client));
        } catch (Exception e) {
            throw new AccountException(AccountException.ACCOUNT_EXISTS);
        }
    }

    @SneakyThrows
    @Override
    public void updateClient(String login, Client account) {
        if (account == null) {
            throw new AccountException(AccountException.NULL_VALUE);
        }

        try {
            accountRepository.updateAccount(login, AccountMapper.toAccountEnt(account));
        } catch (Exception e) {
            throw new AccountException(AccountException.ACCOUNT_NOT_FOUND);
        }
    }

    @Override
    public Client getClient(String login) throws AccountException {
        Optional<AccountEnt> accountEntOptional = accountRepository.getAccount(login);
        if (accountEntOptional.isEmpty()) {
            throw new AccountException(AccountException.ACCOUNT_NOT_FOUND);
        }

        return AccountMapper.toAccount(accountEntOptional.get());
    }

    @Override
    public List<Client> getAllClients() {
        return accountRepository.getAllAccounts().stream().map(AccountMapper::toAccount).collect(Collectors.toList());
    }

    @Override
    public boolean loginExists(String login) {
        return accountRepository.getAccount(login).isPresent();
    }

    //    @PostConstruct
    public void addTestData() {
        List<Client> accounts = Arrays.asList(
                new Client("michal@gmail.com", "client1", true, 6391.12),
                new Client("jarek@interia.pl", "client2", true, 4686.4),
                new Client("miro@miroslaw.pl", "admin1", true, 16391.12),
                new Client("joanna@joanna.pl", "adminka2", true, 16388.2)
        );

        for (Client account : accounts) {
            addClient(account);
        }
    }
}
