package TARS.task;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
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

    public abstract String getSymbol();

    @Override
    public String toString() {
        return "[" + getSymbol() + "][" + (this.isDone ? "X" : " ") + "] " + this.description;
    }

}
