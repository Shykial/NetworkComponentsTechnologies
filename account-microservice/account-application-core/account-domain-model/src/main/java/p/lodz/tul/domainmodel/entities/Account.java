package p.lodz.tul.domainmodel.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@ToString
@Getter
@AllArgsConstructor
public class Account {
    @Setter
    private String email;

    private final String login;

    @Setter
    private String password;

    @Setter
    private boolean active;

    @Getter
    private final LevelOfAccess levelOfAccess;

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
