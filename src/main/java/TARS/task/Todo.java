package TARS.task;

/**
 * Represents a simple to-do task.
 */
public class Todo extends Task {

    /**
     * Constructs a {@code Todo} task with a specified type, description, and status.
     *
     * @param type The type of the task.
     * @param description The description of the to-do task.
     * @param isDone Whether the task is completed.
     */
    public Todo(TaskType type,  String description, boolean isDone) {
        super(type, description, isDone);
    }

}
