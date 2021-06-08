package p.lodz.tul.RestAdapter.dto.accessLevels;

import p.lodz.tul.RestAdapter.dto.AddressDTO;
import p.lodz.tul.RestAdapter.dto.LevelOfAccessDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
public class ClientDTO extends LevelOfAccessDTO implements Serializable {

    @Setter
    private Long id;

    private String firstName;

    private String lastName;

    private AddressDTO addressDTO;

    private double amountOfMoney;

    public ClientDTO(String firstName,String lastName,  AddressDTO addressDTO, double amountOfMoney) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressDTO = addressDTO;
        this.amountOfMoney = amountOfMoney;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientDTO clientDTO = (ClientDTO) o;
        return firstName.equals(clientDTO.firstName) && lastName.equals(clientDTO.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
