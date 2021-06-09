package p.tul.domainmodel.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Client {

    private final String email;

    private final String login;
    private final boolean active;
    private double amountOfMoney;
}
