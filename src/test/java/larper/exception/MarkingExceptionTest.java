package larper.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MarkingExceptionTest {
    @Test
    public void getMessage_alreadyMarked_expectedMessage() {
        assertEquals(" This task is already marked. Lock in and pick one that is not done yet.",
                new MarkingException().getMessage());
    }
}
