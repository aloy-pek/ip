package kuro.parser;

import kuro.chatbot.Kuro;
import kuro.tasks.Task;
import kuro.tasks.Todo;
import kuro.tasks.Deadline;
import kuro.tasks.Event;
import kuro.exceptions.KuroException;


public class CommandParser {
    public static Task parse(String fullCommand) throws KuroException {
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

    private static Task parseTodo(String fullCommand) throws KuroException {
        if (fullCommand.length() > 4) {
            return new Todo(fullCommand.substring(fullCommand.indexOf(" ") + 1));
        } else {
            throw new KuroException("Sumimasen, please specify the task description.");
        }
    }

    private static Task parseDeadline(String fullCommand) throws KuroException {
        if (!fullCommand.contains("/by")) {
            throw new KuroException("Sumimasen, invalid command or format. Please try again.");
        }

        String description = fullCommand.substring(9, fullCommand.indexOf("/by")).trim();
        String by = fullCommand.substring(fullCommand.indexOf("/by") + 4).trim();
        return new Deadline(description, by);
    }

    private static Task parseEvent(String fullCommand) throws KuroException {
        if (!fullCommand.contains("/from")
                || !fullCommand.contains("/to")
                || fullCommand.indexOf("/to") < fullCommand.indexOf("/from")) {
            throw new KuroException("Sumimasen, invalid command or format. Please try again.");
        }
        String description = fullCommand.substring(6, fullCommand.indexOf("/from")).trim();
        String start = fullCommand.substring(fullCommand.indexOf("/from") + 6, fullCommand.indexOf("/to")).trim();
        String end = fullCommand.substring(fullCommand.indexOf("/to") + 4).trim();
        return new Event(description, start, end);
    }

    private static Task parseMarkUnmarkDelete(String fullCommand) throws KuroException {
        try {
            int index = Integer.parseInt(fullCommand.split(" ")[1]) - 1;
            if (index > taskList.size() - 1) {
                throw new Exception();
            }
            return new Task(fullCommand.split(" ")[0]);
        } catch (Exception e) {
            throw new KuroException("Sumimasen, invalid command or format. Please try again.");
        }
    }
}
}
