package p.lodz.tul.mappers.accessLevels;

import p.lodz.tul.domainmodel.entities.accesslevels.Admin;
import p.lodz.tul.dbentities.accesslevels.AdminEnt;

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
