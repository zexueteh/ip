package TARS.task;

public class Todo extends Task {

    public Todo(TaskType type,  String Description, boolean isDone) {
        super(type, Description, isDone);
    }

    public Todo(TaskType type,  String Description) {
        super(type, Description);
    }
}
