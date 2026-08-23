public class Deadline extends Task {
    private String byDate;
    private String byTime;

    public Deadline(String description, String byDate, String byTime) {
        super(description);
        this.byDate = byDate;
        this.byTime = byTime;
    }

    @Override
    public String getTypeIcon() {
        return "D";
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + byDate + " " + byTime + ")";
    }
}
