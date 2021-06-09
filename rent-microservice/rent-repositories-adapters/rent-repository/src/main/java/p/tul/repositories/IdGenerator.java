package p.tul.repositories;

public class IdGenerator {

    private long id;

    public Long nextId() {
        return ++id;
    }
}
