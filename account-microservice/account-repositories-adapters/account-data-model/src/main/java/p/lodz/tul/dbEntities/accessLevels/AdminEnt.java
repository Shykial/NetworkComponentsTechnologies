package p.lodz.tul.dbEntities.accessLevels;

import p.lodz.tul.dbEntities.LevelOfAccessEnt;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class AdminEnt extends LevelOfAccessEnt {

    @Setter
    private Long id;
    @Getter
    private final String adminCode;

    public AdminEnt(String adminCode) {
        this.adminCode = adminCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        AdminEnt adminEnt = (AdminEnt) o;

        return new EqualsBuilder().append(adminCode, adminEnt.adminCode).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(adminCode).toHashCode();
    }
}
