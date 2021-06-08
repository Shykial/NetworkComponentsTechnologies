package p.lodz.tul.dbEntities;

import lombok.Getter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class AddressEnt {
    
    private Long id;
    @Getter
    private final String streetName;
    @Getter
    private final String cityName;
    @Getter
    private final String phoneNumber;

    public AddressEnt(String streetName, String cityName, String phoneNumber) {
        this.streetName = streetName;
        this.cityName = cityName;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        AddressEnt that = (AddressEnt) o;

        return new EqualsBuilder().append(streetName, that.streetName).append(cityName, that.cityName).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(streetName).append(cityName).toHashCode();
    }
}
