package p.lodz.tul.util;

public class IdGenerator {

    private long id;

    public Long nextId() {
        return ++id;
    }
}
