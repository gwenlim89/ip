package larper.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UnmarkingExceptionTest {
    @Test
    public void getMessage_alreadyUnmarked_expectedMessage() {
        assertEquals(" This task is already unmarked. Quit messing around and pick a done task.",
                new UnmarkingException().getMessage());
    }
}
