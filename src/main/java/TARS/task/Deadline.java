package TARS.task;

public class Deadline extends Task {
    protected String by;

    public Deadline(TaskType type, String description, boolean isDone, String by) {
        super(type, description, isDone);
        this.by = by;
    }

    public Deadline(TaskType type, String description, String by) {
        this(type, description, false, "");
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
