package kuro.tasks;

public class Task {
    protected String command;
    protected boolean isCompleted;

    public Task(String command) {
        this.command = command;
        this.isCompleted = false;
    }

    public String getStatus() {
        return (isCompleted ? "X" : " ");
    }

    public void setStatus(boolean status) {
        this.isCompleted = status;
    }

    @Override
    public String toString() {
        return "[" + (isCompleted ? "X" : " ") + "] " + this.command;
    }
}
