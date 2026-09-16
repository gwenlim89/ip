package larper.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UnmarkingExceptionTest {
    @Test
    public void getMessage_alreadyUnmarked_expectedMessage() {
        assertEquals(" This task is already unmarked. Productivity allegations were never confirmed.",
                new UnmarkingException().getMessage());
    }
}
