package p.lodz.tul.domainmodel.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Address {
    private String streetName;
    private String cityName;
    private String phoneNumber;
}
