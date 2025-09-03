package kuro.ui;

import java.util.List;
import java.util.Scanner;

import kuro.constants.Messages;
import kuro.tasks.TaskList;

public class Ui {
    private final Scanner scanner = new Scanner(System.in);

    public String readCommand() {
        System.out.printf(">%n");
        return scanner.nextLine();
    }

    /**
     * Prints the welcome message.
     */
    public void welcome() {
        System.out.println(Messages.WELCOME_MESSAGE);
    }

    /**
     * Prints the goodbye message.
     */
    public void bye() {
        System.out.println(Messages.GOODBYE_MESSAGE);
    }

    /**
     * Prints the marking command message.
     *
     * @param task The String representation of task that was marked.
     */
    public void showMark(String task) {
        System.out.printf("""
                ____________________________________________________________
                Sugoi, I have marked this task as done:
                %s
                ____________________________________________________________
                %n""", task);
    }

    /**
     * Prints the unmarking command message.
     *
     * @param task The String representation of task that was unmarked.
     */
    public void showUnmark(String task) {
        System.out.printf("""
                ____________________________________________________________
                Hai, I have marked this task as not done yet:
                %s
                ____________________________________________________________
                %n""", task);
    }
    
    /**
     * Prints the add Task command message.
     *
     * @param task The String representation of task that was added.
     * @param numberOfTasks The integer showing the number of task in taskList.
     */
    public void showAdd(String task, int numberOfTasks) {
        System.out.printf("""
                ____________________________________________________________
                Wakarimashita, I have added this task:
                %s
                Now, you have %d tasks in the list.
                ____________________________________________________________
                %n""", task, numberOfTasks);
    }

    /**
     * Prints the remove Task command message.
     *
     * @param task The String representation of task that was removed.
     * @param numberOfTasks The integer showing the number of task left in taskList.
     */
    public void showRemove(String task, int numberOfTasks) {
        System.out.printf("""
                ____________________________________________________________
                Hai, I have removed this task:
                %s
                Now, you have %d tasks in the list.
                ____________________________________________________________
                %n""", task, numberOfTasks);
    }

    /**
     * Prints the list command message.
     *
     * @param taskList The TaskList at its current state.
     */
    public void showList(TaskList taskList) {
        System.out.printf("""
                ____________________________________________________________
                Douzo,Here are the task in your list:
                %s
                ____________________________________________________________
                %n""", taskList.toString());
    }

    /**
     * Prints the error message.
     *
     * @param message The error message to be shown.
     */
    public void showError(String message) {
        System.out.printf("""
                ____________________________________________________________
                Error while interacting with Kuro: %s
                ____________________________________________________________
                %n""", message);
    }
}
