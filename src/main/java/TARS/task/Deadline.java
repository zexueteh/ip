package TARS.task;

public class Deadline extends Task {
    protected String by;

    public Deadline(TaskType type, String description, String by) {
        super(type, description);
        this.by = by;
    }

    public Deadline(TaskType type, String description) {
        this(type, description, "");
    }

    public String getBy() {
        return this.by;
    }
    public void setBy(String by) {
        this.by = by;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + this.by + ")";
    }
}
