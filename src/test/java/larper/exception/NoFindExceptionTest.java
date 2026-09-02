package larper.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NoFindExceptionTest {
    @Test
    public void getMessage_noFind_expectedMessage() {
        NoFindException exception = new NoFindException();

        assertEquals(" No match found. Larper checked the whole quest log, try another phrase.",
                exception.getMessage());
    }
}
