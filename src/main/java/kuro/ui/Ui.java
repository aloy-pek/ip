package kuro.ui;


import java.util.Scanner;

import kuro.constants.Messages;
import kuro.tasks.TaskList;

/**
 * Ui class that handles the interaction with the user through CLI
 */
public class Ui {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Return the String that user type using scanner.
     *
     * @return String the string that user type in CLI.
     */
    public String readCommand() {
        System.out.printf(">%n");
        return scanner.nextLine();
    }

    private String wrapMessage(String message) {
        return String.format("""
                ____________________________________________________________
                %s
                ____________________________________________________________
                %n""", message);
    }


    /**
     * Prints the inputted String.
     *
     * @param message The String to be printed.
     */
    public void printMessage(String message) {
        System.out.printf(wrapMessage(message));
    }


    // Function returning all the potential messages.

    /**
     * Returns the marking command message.
     *
     * @param task The String representation of task that was marked.
     * @return String message for marking task.
     */
    public String showMark(String task) {
        return String.format(Messages.MARKING_MESSAGE, task);
    }

    /**
     * Returns the unmarking command message.
     *
     * @param task The String representation of task that was unmarked.
     * @return String message for unmarking task.
     */
    public String showUnmark(String task) {
        return String.format(Messages.UNMARKING_MESSAGE, task);
    }

    /**
     * Returns the add Task command message.
     *
     * @param task          The String representation of task that was added.
     * @param numberOfTasks The integer showing the number of task in taskList.
     * @return String message for adding task.
     */
    public String showAdd(String task, int numberOfTasks) {
        return String.format(Messages.ADDING_MESSAGE, task, numberOfTasks);
    }

    /**
     * Returns the remove Task command message.
     *
     * @param task          The String representation of task that was removed.
     * @param numberOfTasks The integer showing the number of task left in taskList.
     * @return String message for removing task.
     */
    public String showRemove(String task, int numberOfTasks) {
        return String.format(Messages.REMOVE_MESSAGE, task, numberOfTasks);
    }

    /**
     * Returns the list command message.
     *
     * @param taskList The TaskList at its current state.
     * @return String message for showing List of task.
     */
    public String showList(TaskList taskList) {
        return String.format(Messages.SHOW_LIST_MESSAGE, taskList.toString());
    }

    /**
     * Returns the find command message.
     *
     * @param taskList The filtered TaskList.
     * @return String message for showing List of task.
     */
    public String showFilteredList(TaskList taskList) {
        return String.format(Messages.SHOW_FILTERED_LIST_MESSAGE, taskList.toString());
    }

    /**
     * Returns the error message.
     *
     * @param message The error message to be shown.
     * @return String message for showing List of task.
     */
    public String showError(String message) {
        return String.format(Messages.ERROR_MESSAGE, message);
    }
}
