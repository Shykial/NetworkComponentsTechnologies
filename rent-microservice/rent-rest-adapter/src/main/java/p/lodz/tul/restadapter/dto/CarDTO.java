package p.lodz.tul.restadapter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarDTO implements Serializable {

    private double baseLoanPrice;
    private String vin;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarDTO that = (CarDTO) o;
        return vin.equals(that.vin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vin);
    }
}
