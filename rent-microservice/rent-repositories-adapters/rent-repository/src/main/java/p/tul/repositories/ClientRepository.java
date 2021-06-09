package p.tul.repositories;

import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;
import p.tul.dbentities.AccountEnt;

import java.util.*;

@Repository
public class ClientRepository implements Iterable<AccountEnt> {

    private final Map<String, AccountEnt> accounts;
    private final IdGenerator idGenerator;

    public ClientRepository() {
        accounts = new HashMap<>();
        idGenerator = new IdGenerator();
    }

    @SneakyThrows
    public void addAccount(AccountEnt account) {
        if (account == null) {
            throw new RepositoryException(RepositoryException.NULL_VALUE);
        }

        if (accounts.containsKey(account.getLogin())) {
            throw new RepositoryException(RepositoryException.ENTITY_EXISTS);
        }
        account.setId(idGenerator.nextId());
        accounts.put(account.getLogin(), account);
    }

    @SneakyThrows
    public void updateAccount(String login, AccountEnt account) {
        if (account == null) {
            throw new RepositoryException(RepositoryException.NULL_VALUE);
        }

        if (!accounts.containsKey(login)) {
            throw new RepositoryException(RepositoryException.ENTITY_NOT_FOUND);
        }
        accounts.put(login, account);
    }

    public Optional<AccountEnt> getAccount(String login) {
        return Optional.ofNullable(accounts.get(login));
    }

    public List<AccountEnt> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }

    @Override
    public Iterator<AccountEnt> iterator() {
        return accounts.values().iterator();
    }
}
