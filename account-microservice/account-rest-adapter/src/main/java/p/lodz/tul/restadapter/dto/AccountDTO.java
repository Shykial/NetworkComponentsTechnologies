package p.lodz.tul.restadapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO implements Serializable {
    private String email;

    private String login;

    @JsonProperty(access = WRITE_ONLY)
    private String password;

    private boolean active;

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
