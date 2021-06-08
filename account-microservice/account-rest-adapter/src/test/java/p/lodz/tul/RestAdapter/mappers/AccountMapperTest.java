package p.lodz.tul.RestAdapter.mappers;

import p.lodz.tul.DomainModel.Entities.Account;
import p.lodz.tul.DomainModel.Entities.Address;
import p.lodz.tul.DomainModel.Entities.accessLevels.Client;
import p.lodz.tul.RestAdapter.dto.AccountDTO;
import p.lodz.tul.RestAdapter.dto.AddressDTO;
import p.lodz.tul.RestAdapter.dto.accessLevels.ClientDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountMapperTest {

    @Test
    void toAccountTest() {
        AddressDTO addressDTO = new AddressDTO("Podłużna", "Warszawa", "577164174");
        ClientDTO clientDTO = new ClientDTO("Jan", "Poduszka", addressDTO, 47104.12);

        AccountDTO accountDTO = new AccountDTO("janek123@gmail.com", "janek123", "ahg^9g9132:FD", true, clientDTO);
        Account account = AccountMapper.toAccount(accountDTO);

        assertEquals(accountDTO.getEmail(), account.getEmail());
        assertEquals(accountDTO.getLevelOfAccess(), LevelOfAccessMapper.toLevelOfAccessDTO(account.getLevelOfAccess()));
        assertEquals(accountDTO.getLogin(), account.getLogin());
        assertEquals(accountDTO.getPassword(), account.getPassword());
    }

    @Test
    void toAccountDTOTest() {
        Address address = new Address("Zielona", "Zgierz", "2123-123-312");
        Client client = new Client("Jan", "Kowalski", address, 8483883.12);

        Account account = new Account("kowal1995@gmail.com", "kowal47329", "minikowadlo123", false, client);
        AccountDTO accountDTO = AccountMapper.toAccountDTO(account);

        assertEquals(account.getEmail(), accountDTO.getEmail());
        assertEquals(account.getLevelOfAccess(), LevelOfAccessMapper.toLevelOfAccess(accountDTO.getLevelOfAccess()));
        assertEquals(account.getLogin(), accountDTO.getLogin());
        assertEquals(account.getPassword(), accountDTO.getPassword());
    }

    @Test
    void twoWaysConversionTest() {
        Address address = new Address("Zielona", "Zgierz", "2123-123-312");
        Client client = new Client("Jan", "Kowalski", address, 8483883.12);
        Account account = new Account("kowal1995@gmail.com", "kowal47329", "minikowadlo123", false, client);

        AddressDTO addressDTO = new AddressDTO("Podłużna", "Warszawa", "577164174");
        ClientDTO clientDTO = new ClientDTO("Jan", "Poduszka", addressDTO, 47104.12);
        AccountDTO accountDTO = new AccountDTO("janek123@gmail.com", "janek123", "ahg^9g9132:FD", true, clientDTO);

        Account convertedAccount = AccountMapper.toAccount(AccountMapper.toAccountDTO(account));
        AccountDTO convertedAccountDTO = AccountMapper.toAccountDTO(AccountMapper.toAccount(accountDTO));

        assertEquals(account, convertedAccount);
        assertEquals(accountDTO, convertedAccountDTO);
    }
}