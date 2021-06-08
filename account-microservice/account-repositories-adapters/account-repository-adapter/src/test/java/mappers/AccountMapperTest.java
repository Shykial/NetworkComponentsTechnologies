package mappers;

import DomainModel.Entities.Account;
import DomainModel.Entities.Address;
import DomainModel.Entities.accessLevels.Client;
import dbEntities.AccountEnt;
import dbEntities.AddressEnt;
import dbEntities.accessLevels.ClientEnt;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountMapperTest {

    @Test
    void toAccountTest() {
        AddressEnt addressEnt = new AddressEnt("Podłużna", "Warszawa", "577164174");
        ClientEnt clientEnt = new ClientEnt("Jan", "Poduszka", addressEnt, 47104.12);

        AccountEnt accountEnt = new AccountEnt("janek123@gmail.com", "janek123", "ahg^9g9132:FD", true, clientEnt);
        Account account = AccountMapper.toAccount(accountEnt);

        assertEquals(accountEnt.getEmail(), account.getEmail());
        assertEquals(accountEnt.getLevelOfAccess(), LevelOfAccessMapper.toLevelOfAccessEnt(account.getLevelOfAccess()));
        assertEquals(accountEnt.getLogin(), account.getLogin());
        assertEquals(accountEnt.getPassword(), account.getPassword());
    }

    @Test
    void toAccountEntTest() {
        Address address = new Address("Zielona", "Zgierz", "2123-123-312");
        Client client = new Client("Jan", "Kowalski", address, 8483883.12);

        Account account = new Account("kowal1995@gmail.com", "kowal47329", "minikowadlo123", false, client);
        AccountEnt accountEnt = AccountMapper.toAccountEnt(account);

        assertEquals(account.getEmail(), accountEnt.getEmail());
        assertEquals(account.getLevelOfAccess(), LevelOfAccessMapper.toLevelOfAccess(accountEnt.getLevelOfAccess()));
        assertEquals(account.getLogin(), accountEnt.getLogin());
        assertEquals(account.getPassword(), accountEnt.getPassword());
    }

    @Test
    void twoWaysConversionTest() {
        Address address = new Address("Zielona", "Zgierz", "2123-123-312");
        Client client = new Client("Jan", "Kowalski", address, 8483883.12);
        Account account = new Account("kowal1995@gmail.com", "kowal47329", "minikowadlo123", false, client);

        AddressEnt addressEnt = new AddressEnt("Podłużna", "Warszawa", "577164174");
        ClientEnt clientEnt = new ClientEnt("Jan", "Poduszka", addressEnt, 47104.12);
        AccountEnt accountEnt = new AccountEnt("janek123@gmail.com", "janek123", "ahg^9g9132:FD", true, clientEnt);

        Account convertedAccount = AccountMapper.toAccount(AccountMapper.toAccountEnt(account));
        AccountEnt convertedAccountEnt = AccountMapper.toAccountEnt(AccountMapper.toAccount(accountEnt));

        assertEquals(account, convertedAccount);
        assertEquals(accountEnt, convertedAccountEnt);
    }
}