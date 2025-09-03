package kuro.tasks;

/**
 * A class that inherit from Task that has no additional information
 */
public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    public Todo(String description, Boolean isCompleted) {
        super(description, isCompleted);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
