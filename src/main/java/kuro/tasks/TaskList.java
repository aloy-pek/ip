package kuro.tasks;

import java.util.ArrayList;

import kuro.exceptions.KuroException;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    /**
     * Removes task of given index from taskList. 
     *
     * @param index Index of task to be removed.
     * @throws KuroException If index is out of bound.
     */
    public void deleteTask(int index) throws KuroException {
        if (index < 0 || index > this.tasks.size() - 1) {
            throw new KuroException("Index out of bounds");
        }
        this.tasks.remove(index);
    }

    /**
     * Adds task to taskList. 
     *
     * @param task Task to be added.
     */
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    /**
     * Returns the size of taskList.
     *
     * @return integer representing the length of taskList
     */
    public int getSize() {
        return this.tasks.size();
    }

    /**
     * Returns the task in taskList with given index.
     *
     * @return Task of that index.
     * @throws KuroException If the index is out of bound.
     */
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
