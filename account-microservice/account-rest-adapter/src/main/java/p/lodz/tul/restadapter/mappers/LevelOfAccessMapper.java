package p.lodz.tul.restadapter.mappers;

import p.lodz.tul.domainmodel.entities.LevelOfAccess;
import p.lodz.tul.domainmodel.entities.accesslevels.Admin;
import p.lodz.tul.domainmodel.entities.accesslevels.Client;
import p.lodz.tul.restadapter.dto.LevelOfAccessDTO;
import p.lodz.tul.restadapter.dto.accessLevels.AdminDTO;
import p.lodz.tul.restadapter.dto.accessLevels.ClientDTO;
import p.lodz.tul.restadapter.mappers.accesslevels.AdminMapper;
import p.lodz.tul.restadapter.mappers.accesslevels.ClientMapper;

public class LevelOfAccessMapper {

    private LevelOfAccessMapper() {
    }

    public static LevelOfAccessDTO toLevelOfAccessDTO(LevelOfAccess levelOfAccess) {
        if (levelOfAccess instanceof Admin admin) {
            return AdminMapper.toAdminDTO(admin);
        }
        if (levelOfAccess instanceof Client client) {
            return ClientMapper.toClientDTO(client);
        }
        throw new IllegalArgumentException("Invalid access level provided");
    }

    public static LevelOfAccess toLevelOfAccess(LevelOfAccessDTO levelOfAccessDTO) {
        if (levelOfAccessDTO instanceof AdminDTO adminDTO) {
            return AdminMapper.toAdmin(adminDTO);
        }
        if (levelOfAccessDTO instanceof ClientDTO clientDTO) {
            return ClientMapper.toClient(clientDTO);
        }
        throw new IllegalArgumentException("Invalid access level entity provided");
    }
}
