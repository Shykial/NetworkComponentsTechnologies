package p.tul.dbentities;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

public class RentEnt {
    @Setter
    private Long id;
    @Getter
    @Setter
    private UUID uuid;
    @Getter
    private final AccountEnt accountEnt;
    @Getter
    private final VehicleEnt vehicleEnt;
    @Getter
    private final LocalDateTime startDate;
    @Getter
    private LocalDateTime endDate;

    public RentEnt(String uuid, AccountEnt accountEnt, VehicleEnt vehicleEnt, LocalDateTime startDate, LocalDateTime endDate) {
        this.uuid = UUID.fromString(uuid);
        this.accountEnt = accountEnt;
        this.vehicleEnt = vehicleEnt;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public RentEnt(UUID uuid, AccountEnt accountEnt, VehicleEnt vehicleEnt, LocalDateTime startDate, LocalDateTime endDate) {
        this.uuid = uuid;
        this.accountEnt = accountEnt;
        this.vehicleEnt = vehicleEnt;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        RentEnt rentEnt = (RentEnt) o;

        return new EqualsBuilder().append(uuid, rentEnt.uuid).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(uuid).toHashCode();
    }
}
