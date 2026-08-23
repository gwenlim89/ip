public class NoDescriptionException extends LarperException {
    public NoDescriptionException() {
        super(" Larper needs a task description before charging into battle.\n"
                + " Please use one of these formats:\n"
                + " todo DESCRIPTION\n"
                + " deadline DESCRIPTION /by DATE TIME\n"
                + " event DESCRIPTION /from START_DATE START_TIME /to END_DATE END_TIME");
    }
}
