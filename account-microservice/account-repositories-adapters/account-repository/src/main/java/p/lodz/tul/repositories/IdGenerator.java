package p.lodz.tul.repositories;

public class IdGenerator {

    private long id;

    public Long nextId() {
        return ++id;
    }
}
