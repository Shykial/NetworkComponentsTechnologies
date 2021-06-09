package p.lodz.tul.restadapter.mappers.accesslevels;

import p.lodz.tul.domainmodel.entities.accesslevels.Admin;
import p.lodz.tul.restadapter.dto.accessLevels.AdminDTO;

public class AdminMapper {

    private AdminMapper() {
    }

    public static Admin toAdmin(AdminDTO adminDTO) {
        return new Admin(adminDTO.getAdminCode());
    }

    public static AdminDTO toAdminDTO(Admin admin) {
        return new AdminDTO(admin.getAdminCode());
    }
}
