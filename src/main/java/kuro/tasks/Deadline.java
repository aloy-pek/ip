package kuro.tasks;

public class Deadline extends Task {
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    public Deadline(String description, String by, boolean isCompleted) {
        super(description, isCompleted);
        this.by = by;
    }

    @Override
    public String toSaveFormat() {
        return String.format("D | %d | %s | %s", isCompleted ? 1 : 0, command, by);
    }
    
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
