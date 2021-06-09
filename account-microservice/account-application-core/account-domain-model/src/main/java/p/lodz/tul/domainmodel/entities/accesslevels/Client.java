package p.lodz.tul.domainmodel.entities.accesslevels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import p.lodz.tul.domainmodel.entities.Address;
import p.lodz.tul.domainmodel.entities.LevelOfAccess;

@ToString
@Getter
@AllArgsConstructor
public class Client extends LevelOfAccess {
    @Setter
    private String firstName;

    @Setter
    private String lastName;

    private final Address address;

    @Setter
    private double amountOfMoney;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Client client = (Client) o;

        return new EqualsBuilder().append(firstName, client.firstName).append(lastName, client.lastName).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(firstName).append(lastName).toHashCode();
    }
}
