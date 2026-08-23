public class InvalidTimeException extends LarperException {
    public InvalidTimeException(String label) {
        super(" Larper found the " + label + " date, but no time was given.\n"
                + " Time is optional, so please confirm: add a time, or type no time.");
    }
}
