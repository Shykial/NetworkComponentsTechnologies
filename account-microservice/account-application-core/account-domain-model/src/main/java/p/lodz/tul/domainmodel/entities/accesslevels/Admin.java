package p.lodz.tul.domainmodel.entities.accesslevels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import p.lodz.tul.domainmodel.entities.LevelOfAccess;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class Admin extends LevelOfAccess {
    private String adminCode;
}