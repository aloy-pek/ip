package kuro.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import kuro.constants.Messages;
import kuro.exceptions.KuroException;
import kuro.tasks.Deadline;
import kuro.tasks.Event;
import kuro.tasks.Task;
import kuro.tasks.TaskList;
import kuro.tasks.Todo;

/**
 * A class that parse users' input and create the task.
 */
public class CommandParser {

    /**
     * Returns the created Task from parsing user input
     *
     * @param fullCommand The String that user input in CLI.
     * @param tasks The current active TaskList.
     * @return A specific task depending on input.
     * @throws KuroException If user command is not a valid command.
     */
    public Task parse(String fullCommand, TaskList tasks) throws KuroException {
        String command = fullCommand.split(" ")[0].toLowerCase();

        switch (command) {
        case "todo":
            return parseTodo(fullCommand, tasks);
        case "deadline":
            return parseDeadline(fullCommand, tasks);
        case "event":
            return parseEvent(fullCommand, tasks);
        case "mark", "unmark", "delete", "find":
            return parseTaskCommand(fullCommand);
        case "list", "bye":
            return new Task(command); //misc task
        default:
            throw new KuroException("Sumimasen, specified command is not a registered command");
        }
    }

    private Task parseTodo(String fullCommand, TaskList tasks) throws KuroException {
        if (fullCommand.length() < 4) {
            throw new KuroException("Sumimasen, please specify the task description.");
        }
        
        String description = fullCommand.substring(fullCommand.indexOf(" ") + 1);
        if (tasks.hasDuplicate(description)) {
            throw new KuroException(Messages.DUPLICATE_ERROR);
        }
        return new Todo(description);
    }

    private Task parseDeadline(String fullCommand, TaskList tasks) throws KuroException {
        if (!fullCommand.contains("/by") || fullCommand.length() < 13) {
            throw new KuroException("Sumimasen, invalid command or format. Please try again.");
        }

        String description = fullCommand.substring(9, fullCommand.indexOf("/by")).trim();
        String by = fullCommand.substring(fullCommand.indexOf("/by") + 4).trim();
        if (tasks.hasDuplicate(description)) {
            throw new KuroException(Messages.DUPLICATE_ERROR);
        }
        
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(by, formatter);
            return new Deadline(description, dateTime);
        } catch (DateTimeParseException e) {
            throw new KuroException("Invalid date format, Please use yyyy-MM-dd HH:mm");
        }
    }

    private Task parseEvent(String fullCommand, TaskList tasks) throws KuroException {
        if (!fullCommand.contains("/from")
                || !fullCommand.contains("/to")
                || fullCommand.indexOf("/to") < fullCommand.indexOf("/from")
                || fullCommand.length() < 18) {
            throw new KuroException("Sumimasen, invalid command or format. Please try again.");
        }
        String description = fullCommand.substring(6, fullCommand.indexOf("/from")).trim();
        String start = fullCommand.substring(fullCommand.indexOf("/from") + 6, fullCommand.indexOf("/to")).trim();
        String end = fullCommand.substring(fullCommand.indexOf("/to") + 4).trim();

        if (tasks.hasDuplicate(description)) {
            throw new KuroException(Messages.DUPLICATE_ERROR);
        }
        
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime startDateTime = LocalDateTime.parse(start, formatter);
            LocalDateTime endDateTime = LocalDateTime.parse(end, formatter);
            return new Event(description, startDateTime, endDateTime);
        } catch (DateTimeParseException e) {
            throw new KuroException("Invalid date format, Please use yyyy-MM-dd HH:mm");
        }
    }

    //handles Mark, Unmark, Delete, Find commands.
    private Task parseTaskCommand(String fullCommand) throws KuroException {
        try {
            if (fullCommand.split(" ").length < 2) {
                throw new KuroException("Sumimasen, invalid command or format. Please try again.");
            }
            return new Task(fullCommand.split(" ")[0]);
        } catch (Exception e) {
            throw new KuroException("Sumimasen, invalid command or format. Please try again.");
        }
    }

}
