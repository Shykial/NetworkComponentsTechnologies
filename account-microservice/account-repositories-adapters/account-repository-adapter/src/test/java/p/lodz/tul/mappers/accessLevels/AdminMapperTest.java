package p.lodz.tul.mappers.accessLevels;

import p.lodz.tul.DomainModel.Entities.accessLevels.Admin;
import p.lodz.tul.dbEntities.accessLevels.AdminEnt;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdminMapperTest {

    @Test
    void toAdminTest() {
        AdminEnt adminEnt = new AdminEnt("AGH!@5");
        Admin admin = AdminMapper.toAdmin(adminEnt);

        assertEquals(adminEnt.getAdminCode(), admin.getAdminCode());
    }

    @Test
    void toAdminEntTest() {
        Admin admin = new Admin("AGJ!*$$#!12");
        AdminEnt adminEnt = AdminMapper.toAdminEnt(admin);

        assertEquals(admin.getAdminCode(), adminEnt.getAdminCode());
    }

    @Test
    void twoWaysConversionTest() {
        Admin admin = new Admin("AGJ!*$$#!12");
        AdminEnt adminEnt = new AdminEnt("AGH!@5");

        Admin convertedAdmin = AdminMapper.toAdmin(AdminMapper.toAdminEnt(admin));
        AdminEnt convertedAdminEnt = AdminMapper.toAdminEnt(AdminMapper.toAdmin(adminEnt));
    }
}