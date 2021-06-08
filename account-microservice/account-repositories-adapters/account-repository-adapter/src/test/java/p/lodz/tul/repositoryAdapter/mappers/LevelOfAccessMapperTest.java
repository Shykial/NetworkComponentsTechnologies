package p.lodz.tul.repositoryAdapter.mappers;

import p.lodz.tul.DomainModel.Entities.Address;
import p.lodz.tul.DomainModel.Entities.LevelOfAccess;
import p.lodz.tul.DomainModel.Entities.accessLevels.Client;
import p.lodz.tul.dbEntities.LevelOfAccessEnt;
import p.lodz.tul.dbEntities.accessLevels.AdminEnt;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LevelOfAccessMapperTest {

    @Test
    void twoWaysConversionTest() {
        Address address = new Address("Zielona", "Zgierz", "2123-123-312");
        LevelOfAccess levelOfAccess = new Client("Michał", "Gruszka", address, 5012.12);

        LevelOfAccessEnt levelOfAccessEnt = new AdminEnt("1347812&&");

        LevelOfAccess convertedLevelOfAccess = LevelOfAccessMapper.toLevelOfAccess(LevelOfAccessMapper.toLevelOfAccessEnt(levelOfAccess));
        LevelOfAccessEnt convertedLevelOfAccessEnt = LevelOfAccessMapper.toLevelOfAccessEnt(LevelOfAccessMapper.toLevelOfAccess(levelOfAccessEnt));

        assertEquals(levelOfAccess, convertedLevelOfAccess);
        assertEquals(levelOfAccessEnt, convertedLevelOfAccessEnt);
    }
}