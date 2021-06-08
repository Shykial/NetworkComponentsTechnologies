package p.lodz.tul.RestAdapter.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO implements Serializable {
    @Setter
    private String email;

    @Setter
    private String login;

    @Setter
    @JsonIgnore
    private String password;

    @Setter
    private boolean active;

    @Setter
    private LevelOfAccessDTO levelOfAccess;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDTO that = (AccountDTO) o;
        return login.equals(that.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }

}
