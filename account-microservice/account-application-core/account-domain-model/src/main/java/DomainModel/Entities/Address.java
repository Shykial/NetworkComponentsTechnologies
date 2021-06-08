package DomainModel.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@AllArgsConstructor
public class Address {
    @Getter
    @Setter
    private String streetName;
    @Getter
    @Setter
    private String cityName;
    @Getter
    @Setter
    private String phoneNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Address address = (Address) o;

        return new EqualsBuilder().append(streetName, address.streetName).append(cityName, address.cityName).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(streetName).append(cityName).toHashCode();
    }
}
