package TARS.task;

public abstract class Task {
    protected String description;
    protected boolean isDone;
    protected TaskType type;

    public Task(TaskType type, String description) {
        this.description = description;
        this.type = type;
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return isDone;
    }
    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }


    @Override
    public String toString() {
        return "[" + type + "][" + (this.isDone ? "X" : " ") + "] " + this.description;
    }

}
