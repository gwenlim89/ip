public class NonNumberDeleteException extends LarperException {
    public NonNumberDeleteException() {
        super(" Deletion needs a number, not characters.");
    }
}
