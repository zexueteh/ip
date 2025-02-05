public class Event extends Task{
    protected String from;
    protected String to;

    public Event(String description, String from, String to){
        super(description);
        this.from = from;
        this.to = to;
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
    public String getSymbol() {
        return "E";
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + this.from + " to: " + this.to + ")";
    }
}
