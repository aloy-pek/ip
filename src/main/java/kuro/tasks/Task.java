package kuro.tasks;

/**
 * The main Task class that is a superclass for other tasks classes.
 */
public class Task {
    protected String command;
    protected boolean isCompleted;

    public Task(String command) {
        this.command = command;
        this.isCompleted = false;
    }

    public Task(String command, boolean isCompleted) {
        this.command = command;
        this.isCompleted = isCompleted;
    }

    /**
     * Returns a String that represent the completeness of Task.
     * 
     * @return String "X" for completed and " " for incomplete Task.
     */
    public String getStatus() {
        return (this.isCompleted ? "X" : " ");
    }

    public String getCommand() {
        return this.command;
    }

    public void setStatus(boolean status) {
        this.isCompleted = status;
    }

    /**
     * Returns the formatted string to be saved in database.
     * 
     * @return Formatted string of Task.
     */
    public String toSaveFormat() {
        return String.format("T | %d | %s", isCompleted ? 1 : 0, command);
    }

    @Override
    public String toString() {
        return "[" + (isCompleted ? "X" : " ") + "] " + this.command;
    }
}
