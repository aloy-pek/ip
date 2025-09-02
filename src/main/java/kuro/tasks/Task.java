package kuro.tasks;

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

    public String getStatus() {
        return (this.isCompleted ? "X" : " ");
    }

    public String getCommand() {
        return this.command;
    }
    
    public void setStatus(boolean status) {
        this.isCompleted = status;
    }

    public String toSaveFormat() {
        return String.format("T | %d | %s", isCompleted ? 1 : 0, command);
    }

    @Override
    public String toString() {
        return "[" + (isCompleted ? "X" : " ") + "] " + this.command;
    }
}
