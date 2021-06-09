package p.lodz.tul.restadapter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class RentDTO implements Serializable {

    //    @Setter
    private final UUID uuid;

    private final AccountDTO accountDTO;

    private final CarDTO carDTO;

    @Setter
    private LocalDateTime startDate;

    @Setter
    private LocalDateTime endDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentDTO rentDTO = (RentDTO) o;
        return uuid.equals(rentDTO.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
