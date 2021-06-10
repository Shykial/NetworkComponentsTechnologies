package p.lodz.tul.restadapter.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.NoArgsConstructor;
import p.lodz.tul.restadapter.dto.accesslevels.AdminDTO;
import p.lodz.tul.restadapter.dto.accesslevels.ClientDTO;

import java.io.Serializable;

@NoArgsConstructor
//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({
        @Type(value = AdminDTO.class),
        @Type(value = ClientDTO.class)
})
public abstract class LevelOfAccessDTO implements Serializable {
}
