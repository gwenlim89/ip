package larper.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class InvalidNumberDeleteExceptionTest {
    @Test
    public void getMessage_threeTasks_includesTaskRange() {
        assertEquals(" The number provided is invalid. Try again.\n"
                + " You have 3 task(s) in the list, so the number must be from 1 to 3.",
                new InvalidNumberDeleteException(3).getMessage());
    }
}
