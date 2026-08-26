public class InvalidDateException extends LarperException {
    public InvalidDateException(String label) {
        super(" Where the date is? Larper needs the " + label + " date.\n"
                + " Try: deadline DESCRIPTION /by yyyy-MM-dd TIME\n"
                + " Or: event DESCRIPTION /from START_DATE START_TIME /to END_DATE END_TIME");
    }
}
