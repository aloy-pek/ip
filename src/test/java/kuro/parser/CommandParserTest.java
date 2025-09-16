package kuro.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import kuro.exceptions.KuroException;
import kuro.tasks.Deadline;
import kuro.tasks.Event;
import kuro.tasks.Task;
import kuro.tasks.TaskList;
import kuro.tasks.Todo;

public class CommandParserTest {
    private static final CommandParser parser = new CommandParser();
    private static final TaskList tasks = new TaskList(new ArrayList<Task>());

    @Test
    public void commandParsing_validTodo() {
        Task expected = null;
        try {
            expected = parser.parse("todo Clean up", tasks);
        } catch (KuroException e) {
            fail();
        }
        Todo actual = new Todo("Clean up");
        assertEquals(actual.toString(), expected.toString());
    }

    @Test
    public void commandParsing_validDeadline() throws KuroException {
        Task expected = parser.parse("deadline CS2103T IP /by 2024-09-25 18:00", tasks);
        Deadline actual = new Deadline("CS2103T IP",
                LocalDateTime.parse("2024-09-25T18:00", DateTimeFormatter.ISO_DATE_TIME));
        assertEquals(actual.toString(), expected.toString());
    }

    @Test
    public void commandParsing_validEvent() throws KuroException {
        Task expected = parser.parse("event Party /from 2024-09-25 18:00 /to 2024-09-26 00:00", tasks);
        Event actual = new Event("Party",
                LocalDateTime.parse("2024-09-25T18:00", DateTimeFormatter.ISO_DATE_TIME),
                LocalDateTime.parse("2024-09-26T00:00", DateTimeFormatter.ISO_DATE_TIME));
        assertEquals(actual.toString(), expected.toString());
    }

    @Test
    public void commandParsing_invalidListCommand_exceptionThrown() {
        assertThrows(KuroException.class, () -> parser.parse("lis", tasks));
    }

    @Test
    public void commandParsing_todoMissingDescription_exceptionThrown() {
        assertThrows(KuroException.class, () -> parser.parse("todo", tasks));
    }

    @Test
    public void commandParsing_duplicateTodo_exceptionThrown() {
        TaskList currentTasks = new TaskList(new ArrayList<>());
        assertDoesNotThrow(() -> currentTasks.addTask(parser.parse("todo Clean up", currentTasks)));
        assertThrows(KuroException.class, () -> parser.parse("todo Clean up", currentTasks));
    }

    @Test
    public void commandParsing_deadlineMissingDate_exceptionThrown() {
        assertThrows(KuroException.class, () -> parser.parse("deadline CS2103T IP", tasks));
    }
    @Test
    public void commandParsing_deadlineIncorrectDateFormat_exceptionThrown() {
        String command = "deadline CS2103T IP /by 25-09-2024 18:00";
        assertThrows(KuroException.class, () -> parser.parse(command, tasks));
    }

    @Test
    public void commandParsing_duplicateDeadline_exceptionThrown() {
        String command = "deadline CS2103T IP /by 2025-09-25 18:00";
        TaskList currentTasks = new TaskList(new ArrayList<>());
        assertDoesNotThrow(() -> currentTasks.addTask(parser.parse(command, currentTasks)));
        assertThrows(KuroException.class, () -> parser.parse(command, currentTasks));
    }
}
