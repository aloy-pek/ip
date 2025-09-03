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

    public void welcome() {
        System.out.println(Messages.WELCOME_MESSAGE);
    }

    public void bye() {
        System.out.println(Messages.GOODBYE_MESSAGE);
    }

    public void showMark(String task) {
        System.out.printf("""
                        ____________________________________________________________
                        Sugoi, I have marked this task as done:
                        %s
                        ____________________________________________________________
                        %n""", task);
    }
    
    public void showUnmark(String task) {
        System.out.printf("""
                        ____________________________________________________________
                        Hai, I have marked this task as not done yet:
                        %s
                        ____________________________________________________________
                        %n""", task);
    }
    
    public void showAdd(String task, int numberOfTasks) {
        System.out.printf("""
                        ____________________________________________________________
                        Wakarimashita, I have added this task:
                        %s
                        Now, you have %d tasks in the list.
                        ____________________________________________________________
                        %n""", task, numberOfTasks);
    }

    public void showRemove(String task, int numberOfTasks) {
        System.out.printf("""
                        ____________________________________________________________
                        Hai, I have removed this task:
                        %s
                        Now, you have %d tasks in the list.
                        ____________________________________________________________
                        %n""", task, numberOfTasks);
    }

    public void showList(TaskList taskList) {
        System.out.printf("""
                        ____________________________________________________________
                        Douzo,Here are the task in your list:
                        %s
                        ____________________________________________________________
                        %n""", taskList.toString());
    }

    public void showFilteredList(TaskList taskList) {
        System.out.printf("""
                        ____________________________________________________________
                        Douzo,Here are the matching tasks in your list:
                        %s
                        ____________________________________________________________
                        %n""", taskList.toString());
    }

    public void showError(String message) {
        System.out.printf("""
                        ____________________________________________________________
                        Error while interacting with Kuro: %s
                        ____________________________________________________________
                        %n""", message);
    }
    
    
}
