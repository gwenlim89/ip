public class MarkingException extends LarperException {
    public MarkingException() {
        super(" This task is already marked. Lock in and pick one that is not done yet.");
    }
}
