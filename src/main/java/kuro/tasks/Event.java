package kuro.tasks;

public class Event extends Task {
    protected String start;
    protected String end;

    public Event(String description, String start, String end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    public Event(String description, String start, String end, boolean isCompleted) {
        super(description, isCompleted);
        this.start = start;
        this.end = end;
    }

    @Override
    public String toSaveFormat() {
        return String.format("E | %d | %s | %s | %s", isCompleted ? 1 : 0, command, start, end);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (From: " + start + " to: " + end + ")";
    }
}
