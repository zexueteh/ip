package TARS.task;

/**
 * Represents a generic Task with a description and done status.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;
    protected TaskType type;

    /**
     * Constructs a {@code Task} with specified type, description and status.
     *
     * @param type The type of the task.
     * @param description The description of the task.
     * @param isDone Whether the task is completed.
     */
    public Task(TaskType type, String description, boolean isDone) {
        this.type = type;
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Constructs a new task with the specified type and description.
     * The task is initially not completed.
     *
     * @param type The type of the task.
     * @param description The description of the task.
     */
    public Task(TaskType type, String description) {
        this(type, description, false);
    }

    public String getDescription() {
        return description;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public boolean contains(String term) {
        return description.contains(term);
    }

    @Override
    public String toString() {
        return "[" + type + "][" + (this.isDone ? "X" : " ") + "] " + getDescription();
    }

}
