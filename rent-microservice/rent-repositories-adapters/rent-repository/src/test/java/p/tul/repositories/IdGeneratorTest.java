package p.tul.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IdGeneratorTest {

    private IdGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new IdGenerator();
    }

    @Test
    void nextId() {
        Long initialValue = generator.nextId();
        assertEquals(initialValue + 1, generator.nextId());
    }
}