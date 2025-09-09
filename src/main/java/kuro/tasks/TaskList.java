package kuro.tasks;

import java.util.ArrayList;

import kuro.constants.Messages;
import kuro.exceptions.KuroException;
/**
 * Class to store all the task that user inputted.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        assert tasks != null : "Task list cannot be null";
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
            throw new KuroException(Messages.OUT_OF_BOUND_ERROR);
        }
        this.tasks.remove(index);
    }

    /**
     * Adds task to taskList.
     *
     * @param task Task to be added.
     */
    public void addTask(Task task) {
        assert task != null : "Task cannot be null";
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
            throw new KuroException(Messages.OUT_OF_BOUND_ERROR);
        }
        return this.tasks.get(index);
    }

    /**
     * Returns the filtered taskList with the given searchString and is case insensitive.
     *
     * @return TaskList that contains searchString.
     * @throws KuroException If searchString cannot be found in taskList.
     */
    public TaskList filterTaskByKeyword(String searchString) throws KuroException {
        TaskList filteredList = new TaskList(new ArrayList<Task>());
        for (Task task : this.tasks) {
            if (task.getDescription().toLowerCase().contains(searchString.toLowerCase())) {
                filteredList.addTask(task);
            }
        }
        if (filteredList.getSize() == 0) {
            throw new KuroException(Messages.NONMATCHING_FILTER);
        }
        return filteredList;
    }
    
    public boolean hasDuplicate(String description) {
        try  {
            TaskList filteredTasks = this.filterTaskByKeyword(description);
        } catch(KuroException e) {
            return false;
        }
        return true;
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
