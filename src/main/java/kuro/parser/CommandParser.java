package kuro.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import kuro.tasks.Task;
import kuro.tasks.Todo;
import kuro.tasks.Deadline;
import kuro.tasks.Event;
import kuro.exceptions.KuroException;


public class CommandParser {
    
    public Task parse(String fullCommand) throws KuroException {
        String command = fullCommand.split(" ")[0].toLowerCase();

        return switch (command) {
            case "todo" -> parseTodo(fullCommand);
            case "deadline" -> parseDeadline(fullCommand);
            case "event" -> parseEvent(fullCommand);
            case "mark", "unmark", "delete" -> parseMarkUnmarkDelete(fullCommand);
            case "list", "bye" -> new Task(command); //misc task
            default -> throw new KuroException("Sumimasen, specified command is not a registered command");
        };
    }

    private Task parseTodo(String fullCommand) throws KuroException {
        if (fullCommand.length() > 4) {
            return new Todo(fullCommand.substring(fullCommand.indexOf(" ") + 1));
        } else {
            throw new KuroException("Sumimasen, please specify the task description.");
        }
    }

    private Task parseDeadline(String fullCommand) throws KuroException {
        if (!fullCommand.contains("/by") || fullCommand.length() < 13) {
            throw new KuroException("Sumimasen, invalid command or format. Please try again.");
        }

        String description = fullCommand.substring(9, fullCommand.indexOf("/by")).trim();
        String by = fullCommand.substring(fullCommand.indexOf("/by") + 4).trim();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(by, formatter);
            return new Deadline(description, dateTime);
        } catch (DateTimeParseException e) {
            throw new KuroException("Invalid date format, Please use yyyy-MM-dd HH:mm");
        }
    }

    private Task parseEvent(String fullCommand) throws KuroException {
        if (!fullCommand.contains("/from")
                || !fullCommand.contains("/to")
                || fullCommand.indexOf("/to") < fullCommand.indexOf("/from")
                || fullCommand.length() < 18) {
            throw new KuroException("Sumimasen, invalid command or format. Please try again.");
        }
        String description = fullCommand.substring(6, fullCommand.indexOf("/from")).trim();
        String start = fullCommand.substring(fullCommand.indexOf("/from") + 6, fullCommand.indexOf("/to")).trim();
        String end = fullCommand.substring(fullCommand.indexOf("/to") + 4).trim();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime startDateTime = LocalDateTime.parse(start, formatter);
            LocalDateTime endDateTime = LocalDateTime.parse(end, formatter);
            return new Event(description, startDateTime, endDateTime);
        } catch (DateTimeParseException e) {
            throw new KuroException("Invalid date format, Please use yyyy-MM-dd HH:mm");
        }
    }

    private Task parseMarkUnmarkDelete(String fullCommand) throws KuroException {
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
