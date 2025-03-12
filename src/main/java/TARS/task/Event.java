package TARS.task;

/**
 * Represents an event task with a start and end time.
 */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Constructs an {@code Event} task with specified type, description, status, start, and end time.
     *
     * @param type The type of the task.
     * @param description The description of the event.
     * @param isDone Whether the event is completed.
     * @param from The start time of the event.
     * @param to The end time of the event.
     */
    public Event(TaskType type, String description, boolean isDone, String from, String to){
        super(type, description, isDone);
        this.from = from;
        this.to = to;
    }

    public String getTo(){
        return to;
    }
    public String getFrom(){
        return from;
    }

    public void setTo(String to){
        this.to = to;
    }

    @Override
    public boolean contains(String term) {
        return super.contains(term) || getTo().contains(term) || getFrom().contains(term);
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + this.from + " to: " + this.to + ")";
    }
}
