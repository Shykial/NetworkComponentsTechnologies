package mappers;

import DomainModel.Entities.Account;
import dbEntities.AccountEnt;

public class AccountMapper {

    private AccountMapper() {
    }

    public static Account toAccount(AccountEnt accountEnt) {
        return new Account(accountEnt.getEmail(), accountEnt.getLogin(), accountEnt.getPassword(), accountEnt.isActive(), LevelOfAccessMapper.toLevelOfAccess(accountEnt.getLevelOfAccess()));
    }

    public static AccountEnt toAccountEnt(Account account) {
        return new AccountEnt(account.getEmail(), account.getLogin(), account.getPassword(), account.isActive(), LevelOfAccessMapper.toLevelOfAccessEnt(account.getLevelOfAccess()));

    }
}
