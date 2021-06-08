package util;

public class IdGenerator {

    private Long id;

    public IdGenerator() {
        id = 0L;
    }

    public Long nextId() {
        return ++id;
    }
}
