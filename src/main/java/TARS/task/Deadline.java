package TARS.task;

/**
 * Represents a task with a deadline.
 */
public class Deadline extends Task {
    protected String by;

    /**
     * Constructs a {@code Deadline} task with a specified type, description, status, and deadline.
     *
     * @param type The type of the task.
     * @param description The description of the deadline task.
     * @param isDone Whether the task is completed.
     * @param by The deadline of the task.
     */
    public Deadline(TaskType type, String description, boolean isDone, String by) {
        super(type, description, isDone);
        this.by = by;
    }


    public String getBy() {
        return this.by;
    }
    public void setBy(String by) {
        this.by = by;
    }

    @Override
    public boolean contains(String term) {
        return super.contains(term) || getBy().contains(term);
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + this.by + ")";
    }
}
