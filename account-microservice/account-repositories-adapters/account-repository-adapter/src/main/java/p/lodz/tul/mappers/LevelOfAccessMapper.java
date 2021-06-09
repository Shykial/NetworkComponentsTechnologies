package p.lodz.tul.mappers;

import p.lodz.tul.domainmodel.entities.LevelOfAccess;
import p.lodz.tul.domainmodel.entities.accesslevels.Admin;
import p.lodz.tul.domainmodel.entities.accesslevels.Client;
import p.lodz.tul.dbentities.LevelOfAccessEnt;
import p.lodz.tul.dbentities.accesslevels.AdminEnt;
import p.lodz.tul.dbentities.accesslevels.ClientEnt;
import p.lodz.tul.mappers.accessLevels.AdminMapper;
import p.lodz.tul.mappers.accessLevels.ClientMapper;

public class LevelOfAccessMapper {

    private LevelOfAccessMapper() {
    }

    public static LevelOfAccessEnt toLevelOfAccessEnt(LevelOfAccess levelOfAccess) {
        if (levelOfAccess instanceof Admin admin) {
            return AdminMapper.toAdminEnt(admin);
        }
        if (levelOfAccess instanceof Client client) {
            return ClientMapper.toClientEnt(client);
        }
        throw new IllegalArgumentException("Invalid access level provided");
    }

    public static LevelOfAccess toLevelOfAccess(LevelOfAccessEnt levelOfAccessEnt) {
        if (levelOfAccessEnt instanceof AdminEnt adminEnt) {
            return AdminMapper.toAdmin(adminEnt);
        }
        if (levelOfAccessEnt instanceof ClientEnt clientEnt) {
            return ClientMapper.toClient(clientEnt);
        }
        throw new IllegalArgumentException("Invalid access level entity provided");
    }

}
