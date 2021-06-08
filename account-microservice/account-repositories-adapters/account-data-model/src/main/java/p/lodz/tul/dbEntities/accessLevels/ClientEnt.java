package p.lodz.tul.dbEntities.accessLevels;

import p.lodz.tul.dbEntities.AddressEnt;
import p.lodz.tul.dbEntities.LevelOfAccessEnt;
import lombok.Getter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ClientEnt extends LevelOfAccessEnt {

    private Long id;
    @Getter
    private final String firstName;
    @Getter
    private final String lastName;
    @Getter
    private final AddressEnt addressEnt;
    @Getter
    private final double amountOfMoney;

    public ClientEnt(String firstName, String lastName, AddressEnt addressEnt, double amountOfMoney) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressEnt = addressEnt;
        this.amountOfMoney = amountOfMoney;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        ClientEnt clientEnt = (ClientEnt) o;

        return new EqualsBuilder().append(firstName, clientEnt.firstName).append(lastName, clientEnt.lastName).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(firstName).append(lastName).toHashCode();
    }
}
