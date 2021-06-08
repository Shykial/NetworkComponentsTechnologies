package entities;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class VehicleEnt {

    @Setter
    private Long id;
    @Getter
    private final String manufacturerName;
    @Getter
    private final String modelName;
    @Getter
    private final double baseLoanPrice;
    @Getter
    private final String vin;
    @Getter
    private final String licencePlate;

    public VehicleEnt(String manufacturerName, String modelName, double baseLoanPrice, String vin, String licencePlate) {
        this.manufacturerName = manufacturerName;
        this.modelName = modelName;
        this.baseLoanPrice = baseLoanPrice;
        this.vin = vin;
        this.licencePlate = licencePlate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        VehicleEnt that = (VehicleEnt) o;

        return new EqualsBuilder().append(vin, that.vin).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(vin).toHashCode();
    }
}
