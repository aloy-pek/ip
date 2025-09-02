package kuro.chatbot;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;

import kuro.exceptions.KuroException;
import kuro.parser.CommandParser;
import kuro.tasks.Task;
import kuro.tasks.TaskList;
import kuro.ui.Ui;


public class Kuro {
    private final TaskList taskList;
    private final Ui ui;
    private final CommandParser parser;

    public Kuro(String filePath) {
        this.ui = new Ui();
        this.taskList = new TaskList();
        this.parser = new CommandParser();

//        storage = new Storage(filePath);
//        try {
//            tasks = new TaskList(storage.load());
//        } catch (DukeException e) {
//            ui.showLoadingError();
//            tasks = new TaskList();
//        }
    }

    public void listTasks() {
        this.ui.showList(taskList);
    }

    public void addTask(Task task) {
        taskList.addTask(task);
        ui.showAdd(task.toString(), taskList.getSize());
    }

    public void deleteTask(int index) {
        try {
            String taskRemoved = taskList.getTask(index).toString();
            taskList.deleteTask(index);
            ui.showRemove(taskRemoved, taskList.getSize());
        } catch (KuroException e) {
            ui.showError(e.getMessage());
        }
    }

    public void markTaskAsDone(int index) {
        try {
            taskList.getTask(index).setStatus(true);
            ui.showMark(taskList.getTask(index).toString());
        } catch (KuroException e) {
            ui.showError(e.getMessage());
        }
    }

    public void markTaskAsNotDone(int index) {
        try {
            taskList.getTask(index).setStatus(false);
            ui.showUnmark(taskList.getTask(index).toString());
        } catch (KuroException e) {
            ui.showError(e.getMessage());
        }
    }

    public void run() {
        boolean isOperating = true;
//
//        try {
//            File taskDb = new File("./data/kuro.txt");
//            //Scan kuro.txt and initialize taskList
//            Scanner dbScanner = new Scanner(taskDb);
//            while (dbScanner.hasNextLine()) {
//                String data = dbScanner.nextLine();
//                //todo 
//            }
//            dbScanner.close();
//        } catch (FileNotFoundException e) {
//            File newDb = new File("./data/kuro.txt");
//            try {
//                newDb.createNewFile();
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }
//        }
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
