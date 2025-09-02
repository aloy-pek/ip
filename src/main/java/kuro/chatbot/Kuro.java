package kuro.chatbot;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;

import kuro.exceptions.KuroException;
import kuro.parser.CommandParser;
import kuro.storage.Storage;
import kuro.tasks.Task;
import kuro.tasks.TaskList;
import kuro.ui.Ui;


public class Kuro {
    private Storage storage;
    private TaskList tasks;
    private final Ui ui;
    private final CommandParser parser;

    public Kuro(String filePath) {
        this.ui = new Ui();
        this.parser = new CommandParser();
        this.storage = new Storage(filePath);

        try {
            this.tasks = new TaskList(storage.load());
        } catch (KuroException e) {
            ui.showError(e.getMessage());
            this.tasks = new TaskList();
        }
    }

    public void listTasks() {
        this.ui.showList(tasks);
    }

    public void addTask(Task task) {
        tasks.addTask(task);
        ui.showAdd(task.toString(), tasks.getSize());
    }

    public void deleteTask(int index) {
        try {
            String taskRemoved = tasks.getTask(index).toString();
            tasks.deleteTask(index);
            ui.showRemove(taskRemoved, tasks.getSize());
        } catch (KuroException e) {
            ui.showError(e.getMessage());
        }
    }

    public void markTaskAsDone(int index) {
        try {
            tasks.getTask(index).setStatus(true);
            ui.showMark(tasks.getTask(index).toString());
        } catch (KuroException e) {
            ui.showError(e.getMessage());
        }
    }

    public void markTaskAsNotDone(int index) {
        try {
            tasks.getTask(index).setStatus(false);
            ui.showUnmark(tasks.getTask(index).toString());
        } catch (KuroException e) {
            ui.showError(e.getMessage());
        }
    }

    public void run() {
        boolean isOperating = true;
        ui.welcome();

        while (isOperating) {
            String input = ui.readCommand();
            Task newTask;
            int index;

            try {
                newTask = this.parser.parse(input);
            } catch (KuroException e) {
                ui.showError(e.getMessage());
                continue;
            }

            switch (newTask.getCommand()) {
            case "bye":
                isOperating = false;
                ui.bye();
                break;
            case "mark":
                index = Integer.parseInt(input.split(" ")[1]) - 1;
                this.markTaskAsDone(index);
                break;
            case "unmark":
                index = Integer.parseInt(input.split(" ")[1]) - 1;
                this.markTaskAsNotDone(index);
                break;
            case "delete":
                index = Integer.parseInt(input.split(" ")[1]) - 1;
                this.deleteTask(index);
                break;
            case "list":
                this.listTasks();
                break;
            default:
                if (newTask.getCommand().isEmpty()) {
                    break;
                }
                this.addTask(newTask);
            }
        }
    }

    public static void main(String[] args) {
        new Kuro("data/tasks.txt").run();
    }
}
