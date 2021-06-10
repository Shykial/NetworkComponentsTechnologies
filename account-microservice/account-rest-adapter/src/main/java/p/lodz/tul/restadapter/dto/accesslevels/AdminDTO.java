package p.lodz.tul.restadapter.dto.accesslevels;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import p.lodz.tul.restadapter.dto.LevelOfAccessDTO;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class AdminDTO extends LevelOfAccessDTO implements Serializable {

    private Long id;
    private String adminCode;

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
