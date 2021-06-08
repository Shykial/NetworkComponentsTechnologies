package p.lodz.tul.RestAdapter.mappers;

import p.lodz.tul.DomainModel.Entities.Account;
import p.lodz.tul.RestAdapter.dto.AccountDTO;

public class AccountMapper {

    private AccountMapper() {
    }

    public static Account toAccount(AccountDTO accountDTO) {
        return new Account(accountDTO.getEmail(), accountDTO.getLogin(), accountDTO.getPassword(), accountDTO.isActive(), LevelOfAccessMapper.toLevelOfAccess(accountDTO.getLevelOfAccess()));
    }

    public static AccountDTO toAccountDTO(Account account) {
        return new AccountDTO(account.getEmail(), account.getLogin(), account.getPassword(), account.isActive(), LevelOfAccessMapper.toLevelOfAccessDTO(account.getLevelOfAccess()));

    }
}
