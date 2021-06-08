package DomainModel.Entities.accessLevels;

import DomainModel.Entities.Address;
import DomainModel.Entities.LevelOfAccess;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@ToString
public class Client extends LevelOfAccess {
    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;

    @Getter
    private final Address address;

    @Getter
    @Setter
    private double amountOfMoney;

    public Client(String firstName, String lastName, Address address, double amountOfMoney) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.amountOfMoney = amountOfMoney;
    }

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
