package larper.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EmptyDeletionExceptionTest {
    @Test
    public void getMessage_emptyDeletion_expectedMessage() {
        assertEquals(" Task list is empty nothing to delete here!!!", new EmptyDeletionException().getMessage());
    }
}
