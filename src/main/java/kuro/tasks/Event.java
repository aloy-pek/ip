package kuro.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A class that inherit from Task that has additional information on start and end dates.
 */
public class Event extends Task {
    protected LocalDateTime start;
    protected LocalDateTime end;

    /**
     * Constructor for Event class.
     */
    public Event(String description, LocalDateTime start, LocalDateTime end) {
        super(description);
        this.start = start;
        this.end = end;
    }
    /**
     * Constructor for Event class.
     */
    public Event(String description, LocalDateTime start, LocalDateTime end, boolean isCompleted) {
        super(description, isCompleted);
        this.start = start;
        this.end = end;
    }

    @Override
    public String toSaveFormat() {
        return String.format("E | %d | %s | %s | %s", isCompleted ? 1 : 0, description, start, end);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (From: "
                + start.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                + " to: "
                + end.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + ")";
    }
}
