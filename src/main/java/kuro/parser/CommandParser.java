package kuro.parser;

import static kuro.constants.ParserConstants.EVENT_END_INDICATOR;
import static kuro.constants.ParserConstants.EVENT_START_INDICATOR;
import static kuro.constants.ParserConstants.MINIMUM_EVENT_LENGTH;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import kuro.constants.Messages;
import kuro.constants.ParserConstants;
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
            throw new KuroException(Messages.UNREGISTERED_COMMAND);
        }
    }
    // -------------------------------- TODOs TASK ---------------------------------------------------------------
    private Task parseTodo(String fullCommand, TaskList tasks) throws KuroException {
        validateTodoCommand(fullCommand);
        String description = extractTodoDescription(fullCommand);
        validateDuplicate(tasks, description);
        return new Todo(description);
    }

    private void validateTodoCommand(String fullCommand) throws KuroException {
        if (fullCommand.length() <= ParserConstants.MINIMUM_TODO_LENGTH) {
            throw new KuroException(Messages.MISSING_TASK_DESCRIPTION);
        }
    }

    private String extractTodoDescription(String fullCommand) throws KuroException {
        String description = fullCommand.substring(fullCommand.indexOf(" ") + 1).trim();;
        if (description.isEmpty()) {
            throw new KuroException(Messages.MISSING_TASK_DESCRIPTION);
        }
        return description;
    }
    // -------------------------------- DEADLINE TASK ---------------------------------------------------------------
    private Task parseDeadline(String fullCommand, TaskList tasks) throws KuroException {
        validateDeadlineCommand(fullCommand);
        String description = extractDeadlineDescription(fullCommand);
        validateDuplicate(tasks, description);
        LocalDateTime by = parseDateTime(extractDeadlineBy(fullCommand));
        return new Deadline(description, by);
    }
    private void validateDeadlineCommand(String fullCommand) throws KuroException {
        boolean isInvalidDeadlineCommand = !fullCommand.contains(ParserConstants.DEADLINE_INDICATOR)
                || fullCommand.length() <= ParserConstants.MINIMUM_DEADLINE_LENGTH;
        if (isInvalidDeadlineCommand) {
            throw new KuroException(Messages.INVALID_COMMAND);
        }
    }

    private String extractDeadlineDescription(String fullCommand) throws KuroException {
        String description = fullCommand.substring(9, fullCommand.indexOf(ParserConstants.DEADLINE_INDICATOR)).trim();
        if (description.isEmpty()) {
            throw new KuroException(Messages.MISSING_TASK_DESCRIPTION);
        }
        return description;
    }

    private String extractDeadlineBy(String fullCommand) {
        return fullCommand.substring(fullCommand.indexOf(ParserConstants.DEADLINE_INDICATOR) + 4).trim();
    }

    // -------------------------------- EVENT TASK ---------------------------------------------------------------
    private Task parseEvent(String fullCommand, TaskList tasks) throws KuroException {
        validateEventCommand(fullCommand);
        //Extract out the description, start, end from command.
        EventFields fields = extractEventFields(fullCommand);
        validateDuplicate(tasks, fields.description);
        LocalDateTime startDateTime = parseDateTime(fields.start);
        LocalDateTime endDateTime = parseDateTime(fields.end);
        return new Event(fields.description, startDateTime, endDateTime);
    }

    private EventFields extractEventFields(String fullCommand) throws KuroException {
        String description = fullCommand.substring(6, fullCommand.indexOf(EVENT_START_INDICATOR)).trim();
        String start = fullCommand.substring(fullCommand.indexOf(EVENT_START_INDICATOR) + 6,
                fullCommand.indexOf(EVENT_END_INDICATOR)).trim();
        String end = fullCommand.substring(fullCommand.indexOf(EVENT_END_INDICATOR) + 4).trim();
        if (description.isEmpty() || start.isEmpty() || end.isEmpty()) {
            throw new KuroException(Messages.MISSING_TASK_DESCRIPTION);
        }
        return new EventFields(description, start, end);
    }
    private void validateEventCommand(String fullCommand) throws KuroException {
        boolean isInvalidEventCommand = !fullCommand.contains(EVENT_START_INDICATOR)
                || !fullCommand.contains(EVENT_END_INDICATOR)
                || fullCommand.indexOf(EVENT_END_INDICATOR) < fullCommand.indexOf(EVENT_START_INDICATOR)
                || fullCommand.length() <= MINIMUM_EVENT_LENGTH;
        if (isInvalidEventCommand) {
            throw new KuroException(Messages.INVALID_COMMAND);
        }
    }
    //a class to contain all the fields necessary for event task
    private static class EventFields {
        private final String description;
        private final String start;
        private final String end;

        EventFields(String description, String start, String end) {
            this.description = description;
            this.start = start;
            this.end = end;
        }
    }

    // -------------------------------- COMMON HELPER ---------------------------------------------------------------
    private void validateDuplicate(TaskList tasks, String description) throws KuroException {
        if (tasks.hasDuplicate(description)) {
            throw new KuroException(Messages.DUPLICATE_ERROR);
        }
    }

    private LocalDateTime parseDateTime(String input) throws KuroException {
        try {
            return LocalDateTime.parse(input, DateTimeFormatter.ofPattern(ParserConstants.DATE_INPUT_PATTERN));
        } catch (DateTimeParseException e) {
            throw new KuroException(Messages.INVALID_DATE);
        }
    }

    // -------------------------------  Mark, Unmark, Delete, Find -------------------------------------------------
    private Task parseTaskCommand(String fullCommand) throws KuroException {
        try {
            if (fullCommand.split(" ").length < ParserConstants.MINIMUM_BASIC_TASK_LENGTH) {
                throw new KuroException(Messages.INVALID_COMMAND);
            }
            return new Task(fullCommand.split(" ")[0]);
        } catch (Exception e) {
            throw new KuroException(Messages.INVALID_COMMAND);
        }
    }
}
