package kuro.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A class that inherit from Task that has additional deadline information.
 */
public class Deadline extends Task {
    protected LocalDateTime by;

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    public Deadline(String description, LocalDateTime by, boolean isCompleted) {
        super(description, isCompleted);
        this.by = by;
    }

    @Override
    public String toSaveFormat() {
        return String.format("D | %d | %s | %s", isCompleted ? 1 : 0, command, by);
    }
    
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + ")";
    }
}
