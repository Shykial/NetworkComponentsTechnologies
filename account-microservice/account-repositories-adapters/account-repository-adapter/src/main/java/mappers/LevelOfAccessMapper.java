package mappers;

import DomainModel.Entities.LevelOfAccess;
import DomainModel.Entities.accessLevels.Admin;
import DomainModel.Entities.accessLevels.Client;
import dbEntities.LevelOfAccessEnt;
import dbEntities.accessLevels.AdminEnt;
import dbEntities.accessLevels.ClientEnt;
import mappers.accessLevels.AdminMapper;
import mappers.accessLevels.ClientMapper;

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
