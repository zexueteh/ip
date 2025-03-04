package TARS.task;

public class Event extends Task {
    protected String from;
    protected String to;

    public Event(TaskType type, String description, String from, String to){
        super(type, description);
        this.from = from;
        this.to = to;
    }
    public Event(TaskType type, String description) {
        this(type, description, "", "");
    }


    public String getFrom(){
        return from;
    }
    public String getTo(){
        return to;
    }

    public void setFrom(String from){
        this.from = from;
    }
    public void setTo(String to){
        this.to = to;
    }


    @Override
    public String toString() {
        return super.toString() + " (from: " + this.from + " to: " + this.to + ")";
    }
}
