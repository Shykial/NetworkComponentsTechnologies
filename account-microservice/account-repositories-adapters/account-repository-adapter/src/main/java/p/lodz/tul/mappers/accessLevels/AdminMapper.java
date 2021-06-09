package p.lodz.tul.mappers.accessLevels;

import p.lodz.tul.DomainModel.Entities.accessLevels.Admin;
import p.lodz.tul.dbEntities.accessLevels.AdminEnt;

public class AdminMapper {

    private AdminMapper() {
    }

    public static Admin toAdmin(AdminEnt adminEnt) {
        return new Admin(adminEnt.getAdminCode());
    }

    public static AdminEnt toAdminEnt(Admin admin) {
        return new AdminEnt(admin.getAdminCode());
    }
}
