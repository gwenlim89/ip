package larper.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class InvalidNumberDeleteExceptionTest {
    @Test
    public void getMessage_threeTasks_includesTaskRange() {
        assertEquals(" That delete number is out of bounds. Try again.\n"
                + " You have 3 task(s), so pick from 1 to 3.",
                new InvalidNumberDeleteException(3).getMessage());
    }
}
