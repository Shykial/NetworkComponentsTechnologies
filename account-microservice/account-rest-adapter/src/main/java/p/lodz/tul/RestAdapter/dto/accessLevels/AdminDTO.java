package p.lodz.tul.RestAdapter.dto.accessLevels;

import p.lodz.tul.RestAdapter.dto.LevelOfAccessDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

public class AdminDTO extends LevelOfAccessDTO implements Serializable {

    @Setter
    private Long id;
    @Getter
    private final String adminCode;

    public AdminDTO(String adminCode) {
        this.adminCode = adminCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminDTO adminDTO = (AdminDTO) o;
        return adminCode.equals(adminDTO.adminCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adminCode);
    }
}
