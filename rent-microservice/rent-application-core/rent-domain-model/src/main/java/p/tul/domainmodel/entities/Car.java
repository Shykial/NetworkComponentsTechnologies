package p.tul.domainmodel.entities;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Getter
public class Car {

    @Setter
    private double baseLoanPrice;

    private final String vin;

    public Car(double baseLoanPrice, String vin) {
        this.baseLoanPrice = baseLoanPrice;
        this.vin = vin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Car vehicle = (Car) o;

        return new EqualsBuilder().append(vin, vehicle.vin).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(vin).toHashCode();
    }
}
