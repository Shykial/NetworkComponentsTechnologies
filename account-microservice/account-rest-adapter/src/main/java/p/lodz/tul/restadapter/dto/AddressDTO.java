package p.lodz.tul.restadapter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressDTO implements Serializable {

    private String streetName;

    private String cityName;

    private String phoneNumber;
}
