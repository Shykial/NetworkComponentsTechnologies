package p.lodz.tul.RestAdapter.mappers;

import p.lodz.tul.DomainModel.Entities.LevelOfAccess;
import p.lodz.tul.DomainModel.Entities.accessLevels.Admin;
import p.lodz.tul.DomainModel.Entities.accessLevels.Client;
import p.lodz.tul.RestAdapter.dto.LevelOfAccessDTO;
import p.lodz.tul.RestAdapter.dto.accessLevels.AdminDTO;
import p.lodz.tul.RestAdapter.dto.accessLevels.ClientDTO;
import p.lodz.tul.RestAdapter.mappers.accessLevels.AdminMapper;
import p.lodz.tul.RestAdapter.mappers.accessLevels.ClientMapper;

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
