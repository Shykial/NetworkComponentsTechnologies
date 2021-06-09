package p.lodz.tul.restadapter.mappers.accessLevels;

import p.lodz.tul.DomainModel.Entities.accessLevels.Admin;
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
