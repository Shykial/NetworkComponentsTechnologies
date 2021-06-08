package p.lodz.tul.RestAdapter.mappers.accessLevels;

import p.lodz.tul.DomainModel.Entities.accessLevels.Admin;
import p.lodz.tul.RestAdapter.dto.accessLevels.AdminDTO;

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
