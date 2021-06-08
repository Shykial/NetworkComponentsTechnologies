package p.lodz.tul.DomainModel.Entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@ToString
public class Account {

    @Getter
    @Setter
    private String email;

    @Getter
    private String login;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private boolean active;

    @Getter
    private LevelOfAccess levelOfAccess;

    public Account() {
    }

    public Account(String email, String login, String password, boolean active, LevelOfAccess levelOfAccess) {
        this.email = email;
        this.login = login;
        this.password = password;
        this.active = active;
        this.levelOfAccess = levelOfAccess;
    }

    public Account(String email, String login, String password, LevelOfAccess levelOfAccess) {
        this.email = email;
        this.login = login;
        this.password = password;
        this.active = true;
        this.levelOfAccess = levelOfAccess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Account account = (Account) o;

        return new EqualsBuilder().append(login, account.login).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(login).toHashCode();
    }
}
