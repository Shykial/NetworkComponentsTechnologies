package p.lodz.tul.DomainModel.Entities.accessLevels;

import p.lodz.tul.DomainModel.Entities.LevelOfAccess;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@ToString
public class Admin extends LevelOfAccess {

    @Getter
    @Setter
    private String adminCode;

    public Admin(String adminCode) {
        this.adminCode = adminCode;
    }

    public Admin() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Admin admin = (Admin) o;

        return new EqualsBuilder().append(adminCode, admin.adminCode).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(adminCode).toHashCode();
    }
}
