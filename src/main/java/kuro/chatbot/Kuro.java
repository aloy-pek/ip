package kuro.chatbot;

import static kuro.constants.Messages.WELCOME_MESSAGE;
import static kuro.constants.StorageConstants.FILE_PATH;

import java.io.IOException;
import java.util.ArrayList;

import kuro.constants.Messages;
import kuro.exceptions.KuroException;
import kuro.parser.CommandParser;
import kuro.storage.Storage;
import kuro.tasks.Task;
import kuro.tasks.TaskList;
import kuro.ui.Ui;

/**
 * Main class of the chatbot: Kuro.
 */
public class Kuro {
    private Storage storage;
    private TaskList tasks;
    private final Ui ui;
    private final CommandParser parser;

    /**
     * Kuro class constructor
     *
     * @param filePath the filepath of the text file that stored data of taskList
     */
    public Kuro(String filePath) {
        this.ui = new Ui();
        this.parser = new CommandParser();
        this.storage = new Storage(filePath);

        try {
            this.tasks = new TaskList(storage.load());
        } catch (KuroException e) {
            ui.showError(e.getMessage());
            this.tasks = new TaskList(new ArrayList<Task>());
        } catch (IOException e) {
            ui.showError(Messages.ERROR_LOADING_FILE_MESSAGE);
        }
    }

    /**
     * Return the String of list of tasks.
     *
     * @return String representation of the current state of list of task.
     */
    public String listTasks() {
        return this.ui.showList(tasks);
    }

    /**
     * Sends new task to added to tasks.
     * Returns add Task message.
     *
     * @param task new Task to be added.
     * @return String representation of add task message.
     */
    public String addTask(Task task) {
        tasks.addTask(task);
        return ui.showAdd(task.toString(), tasks.getSize());
    }

    /**
     * Get specific task from tasks and call tasks to delete it.
     * Returns the String obtained from sends task string representation and length of tasks to ui.
     *
     * @param index The index of task to be deleted
     * @return String representation of delete task message.
     */
    public String deleteTask(int index) {
        try {
            String taskRemoved = tasks.getTask(index).toString();
            tasks.deleteTask(index);
            return ui.showRemove(taskRemoved, tasks.getSize());
        } catch (KuroException e) {
            return ui.showError(e.getMessage());
        }
    }

    /**
     * Get specific task from tasks and call tasks to mark it.
     * Return the String obtained from sending task string representation to ui.
     *
     * @param index The index of task to be marked.
     * @return String representation of marking task message.
     */
    public String markTaskAsDone(int index) {
        try {
            tasks.getTask(index).setStatus(true);
            return ui.showMark(tasks.getTask(index).toString());
        } catch (KuroException e) {
            return ui.showError(e.getMessage());
        }
    }

    /**
     * Get specific task from tasks and call tasks to unmark it.
     * Return the String obtained from sending task string representation to ui.
     *
     * @param index The index of task to be unmarked
     * @return String representation of unmarking task message.
     */
    public String markTaskAsNotDone(int index) {
        try {
            tasks.getTask(index).setStatus(false);
            return ui.showUnmark(tasks.getTask(index).toString());
        } catch (KuroException e) {
            return ui.showError(e.getMessage());
        }
    }

    /**
     * Get filtered taskList from tasks.
     * Return the String obtained from sending filtered taskList to ui.
     *
     * @param searchString The keyword to be searched.
     * @return String representation of filter task message.
     */
    public String filterTaskList(String searchString) {
        try {
            TaskList filteredList = this.tasks.filterTaskByKeyword(searchString);
            return ui.showFilteredList(filteredList);
        } catch (KuroException e) {
            return ui.showError(e.getMessage());
        }
    }

    /**
     * Return the String obtained from carrying out the command from input.
     *
     * @param input The command typed in CLI or GUI.
     * @return String representation of response from Kuro.
     */
    public String executeCommand(String input) {
        Task newTask;
        int index;

        try {
            newTask = this.parser.parse(input);
        } catch (KuroException e) {
            return ui.showError(e.getMessage());
        }

        switch (newTask.getDescription()) {
        case "bye":
            try {
                storage.save(tasks.getAllTasks());
            } catch (IOException e) {
                return ui.showError(Messages.ERROR_SAVING_FILE_MESSAGE);
            }
            return Messages.GOODBYE_MESSAGE;
        case "find":
            String searchString = input.split(" ")[1];
            return this.filterTaskList(searchString);
        case "mark":
            index = Integer.parseInt(input.split(" ")[1]) - 1;
            return this.markTaskAsDone(index);
        case "unmark":
            index = Integer.parseInt(input.split(" ")[1]) - 1;
            return this.markTaskAsNotDone(index);
        case "delete":
            index = Integer.parseInt(input.split(" ")[1]) - 1;
            return this.deleteTask(index);
        case "list":
            return this.listTasks();
        default:
            if (newTask.getDescription().isEmpty()) {
                return ui.showError(Messages.ERROR_MISSING_COMMAND_MESSAGE);
            }
            return this.addTask(newTask);
        }
    }

    /**
     * Runs the kuro chatbot for cli.
     *
     */
    public void run() {
        boolean isOperating = true;
        ui.printMessage(WELCOME_MESSAGE);

        while (isOperating) {
            String input = ui.readCommand();
            String response = this.executeCommand(input);
            if (response.equals(Messages.GOODBYE_MESSAGE)) {
                isOperating = false;
            }
            ui.printMessage(response);
        }
    }

    public static void main(String[] args) {
        new Kuro(FILE_PATH).run();
    }
}
