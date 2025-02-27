package TARS.task;

public class Deadline extends Task {
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    public String getBy() {
        return this.by;
    }
    public void setBy(String by) {
        this.by = by;
    }

    @Override
    public String getSymbol() {
        return "D";
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + this.by + ")";
    }
}
