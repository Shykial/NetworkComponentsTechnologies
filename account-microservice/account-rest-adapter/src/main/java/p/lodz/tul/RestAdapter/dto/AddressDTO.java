package p.lodz.tul.RestAdapter.dto;

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
