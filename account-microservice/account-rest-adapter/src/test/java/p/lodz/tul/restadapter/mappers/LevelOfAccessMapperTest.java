package p.lodz.tul.restadapter.mappers;


import p.lodz.tul.domainmodel.entities.Address;
import p.lodz.tul.domainmodel.entities.LevelOfAccess;
import p.lodz.tul.domainmodel.entities.accesslevels.Client;
import p.lodz.tul.restadapter.dto.LevelOfAccessDTO;
import p.lodz.tul.restadapter.dto.accesslevels.AdminDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LevelOfAccessMapperTest {

    @Test
    void twoWaysConversionTest() {
        Address address = new Address("Zielona", "Zgierz", "2123-123-312");
        LevelOfAccess levelOfAccess = new Client("Michał", "Gruszka", address, 5012.12);

        LevelOfAccessDTO levelOfAccessDTO = new AdminDTO("1347812&&");

        LevelOfAccess convertedLevelOfAccess = LevelOfAccessMapper.toLevelOfAccess(LevelOfAccessMapper.toLevelOfAccessDTO(levelOfAccess));
        LevelOfAccessDTO convertedLevelOfAccessDTO = LevelOfAccessMapper.toLevelOfAccessDTO(LevelOfAccessMapper.toLevelOfAccess(levelOfAccessDTO));

        assertEquals(levelOfAccess, convertedLevelOfAccess);
        assertEquals(levelOfAccessDTO, convertedLevelOfAccessDTO);
    }
}