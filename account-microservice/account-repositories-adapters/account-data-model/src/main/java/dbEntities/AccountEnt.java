package dbEntities;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class AccountEnt {

    @Setter
    private Long id;
    @Getter
    private String email;
    @Getter
    private String login;
    @Getter
    private String password;
    @Getter
    private boolean active;
    @Getter
    private LevelOfAccessEnt levelOfAccess;

    public AccountEnt() {
    }

    public AccountEnt(String email, String login, String password, boolean active, LevelOfAccessEnt levelOfAccess) {
        this.email = email;
        this.login = login;
        this.password = password;
        this.active = active;
        this.levelOfAccess = levelOfAccess;
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
