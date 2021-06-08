package repositories;

public class IdGenerator {

    private long id;

    public Long nextId() {
        return ++id;
    }
}
