package p.lodz.tul.restadapter.mappers;

import p.lodz.tul.domainmodel.entities.Client;
import p.lodz.tul.restadapter.dto.AccountDTO;

public class AccountMapper {

    public static Client toAccount(AccountDTO accountDTO) {
        return new Client(accountDTO.getEmail(), accountDTO.getLogin(), accountDTO.isActive(), accountDTO.getAmountOfMoney());
    }

    public static AccountDTO toAccountDTO(Client account) {
        return new AccountDTO(account.getEmail(), account.getLogin(), account.isActive(), account.getAmountOfMoney());

    }
}
