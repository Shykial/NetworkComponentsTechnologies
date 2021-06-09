package p.lodz.tul.domainmodel.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Rent {

    @Setter
    private UUID uuid;


    @Setter
    private Client account;

    @Setter
    private Car vehicle;

    private LocalDateTime startDate;

    @Setter
    private LocalDateTime endDate;

    public Rent(Client account, Car vehicle, LocalDateTime startDate) {
        this.account = account;
        this.vehicle = vehicle;
        this.startDate = startDate;
    }

    public Rent(Client account, Car vehicle, LocalDateTime startDate, LocalDateTime endDate) {
        this.account = account;
        this.vehicle = vehicle;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Rent(Client account, Car vehicle) {
        this.account = account;
        this.vehicle = vehicle;
        this.startDate = LocalDateTime.now();
    }

    public Rent(UUID uuid, Client account, Car vehicle, LocalDateTime startDate) {
        this.uuid = uuid;
        this.account = account;
        this.vehicle = vehicle;
        this.startDate = startDate;
    }

    public Rent(UUID uuid, Client account, Car vehicle) {
        this.uuid = uuid;
        this.account = account;
        this.vehicle = vehicle;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Rent rent = (Rent) o;

        return new EqualsBuilder().append(uuid, rent.uuid).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(uuid).toHashCode();
    }
}
