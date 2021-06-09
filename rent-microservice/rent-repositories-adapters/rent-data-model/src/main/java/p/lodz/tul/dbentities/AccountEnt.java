package p.lodz.tul.dbentities;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class AccountEnt {

    @Setter
    private Long id;
    @Getter
    private final String email;
    @Getter
    private final String login;
    @Getter
    private final boolean active;

    @Getter
    @Setter
    private double amountOfMoney;

    public AccountEnt(String email, String login, boolean active, double amountOfMoney) {
        this.email = email;
        this.login = login;
        this.active = active;
        this.amountOfMoney = amountOfMoney;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        AccountEnt that = (AccountEnt) o;

        return new EqualsBuilder().append(login, that.login).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(login).toHashCode();
    }
}
