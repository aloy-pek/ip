package kuro.tasks;

import java.util.ArrayList;

import kuro.exceptions.KuroException;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void deleteTask(int index) throws KuroException {
        if (index < 0 || index > this.tasks.size() - 1) {
            throw new KuroException("Index out of bounds");
        }
        this.tasks.remove(index);
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public int getSize() {
        return this.tasks.size();
    }

    public Task getTask(int index) throws KuroException {
        if (index < 0 || index > this.tasks.size() - 1) {
            throw new KuroException("Index out of bounds");
        }
        return this.tasks.get(index);
    }
    
    @Override
    public String toString() {
        StringBuilder listString = new StringBuilder();
        for (int i = 0; i < this.tasks.size(); i++) {
            listString.append("\n")
                    .append(i + 1)
                    .append(". ")
                    .append(tasks.get(i).toString());
        }
        return listString.toString();
    }
}
