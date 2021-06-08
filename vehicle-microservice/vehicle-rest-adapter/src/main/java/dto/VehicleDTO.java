package dto;

import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

public class VehicleDTO implements Serializable {

    @Getter
    @Setter
    private  String manufacturerName;

    @Getter
    @Setter
    private  String modelName;

    @Getter
    @Setter
    private double baseLoanPrice;

    @Getter
    private  String vin;

    @Getter
    @Setter
    private  String licencePlate;

    public VehicleDTO(String manufacturerName,
                      String modelName,
                      double baseLoanPrice,
                      String vin,
                      String licencePlate) {
        this.manufacturerName = manufacturerName;
        this.modelName = modelName;
        this.baseLoanPrice = baseLoanPrice;
        this.vin = vin;
        this.licencePlate = licencePlate;
    }

    public VehicleDTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleDTO that = (VehicleDTO) o;
        return vin.equals(that.vin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vin);
    }
}
