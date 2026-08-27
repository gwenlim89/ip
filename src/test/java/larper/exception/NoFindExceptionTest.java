package larper.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NoFindExceptionTest {
    @Test
    public void getMessage_noFind_expectedMessage() {
        NoFindException exception = new NoFindException();

        assertEquals(" oh no book found! Please retry again!!", exception.getMessage());
    }
}
