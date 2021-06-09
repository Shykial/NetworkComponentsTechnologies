package p.lodz.tul.restadapter.mappers.accesslevels;


import p.lodz.tul.domainmodel.entities.accesslevels.Admin;
import p.lodz.tul.restadapter.dto.accessLevels.AdminDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdminMapperTest {

    @Test
    void toAdminTest() {
        AdminDTO adminDTO = new AdminDTO("AGH!@5");
        Admin admin = AdminMapper.toAdmin(adminDTO);

        assertEquals(adminDTO.getAdminCode(), admin.getAdminCode());
    }

    @Test
    void toAdminDTOTest() {
        Admin admin = new Admin("AGJ!*$$#!12");
        AdminDTO adminDTO = AdminMapper.toAdminDTO(admin);

        assertEquals(admin.getAdminCode(), adminDTO.getAdminCode());
    }

    @Test
    void twoWaysConversionTest() {
        Admin admin = new Admin("AGJ!*$$#!12");
        AdminDTO adminDTO = new AdminDTO("AGH!@5");

        Admin convertedAdmin = AdminMapper.toAdmin(AdminMapper.toAdminDTO(admin));
        AdminDTO convertedAdminDTO = AdminMapper.toAdminDTO(AdminMapper.toAdmin(adminDTO));
    }
}