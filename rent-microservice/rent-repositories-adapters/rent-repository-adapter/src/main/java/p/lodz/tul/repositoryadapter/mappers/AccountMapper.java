package p.lodz.tul.repositoryadapter.mappers;

import p.lodz.tul.dbentities.AccountEnt;
import p.lodz.tul.domainmodel.entities.Client;

public class AccountMapper {

    public static Client toAccount(AccountEnt accountEnt) {
        return new Client(accountEnt.getEmail(), accountEnt.getLogin(), accountEnt.isActive(), accountEnt.getAmountOfMoney());
    }

    public static AccountEnt toAccountEnt(Client account) {
        return new AccountEnt(account.getEmail(), account.getLogin(), account.isActive(), account.getAmountOfMoney());

    }
}
