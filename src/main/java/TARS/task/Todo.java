package TARS.task;

public class Todo extends Task {

    public Todo(String Description) {
        super(Description);
    }

    @Override
    public String getSymbol() {
        return "T";
    }
}
