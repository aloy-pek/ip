package kuro.parser;

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
        Task expected = parser.parse("event Party /from 2024-09-25 18:00 /to 2024-09-25 00:00", tasks);
        Event actual = new Event("Party",
                LocalDateTime.parse("2024-09-25T18:00", DateTimeFormatter.ISO_DATE_TIME),
                LocalDateTime.parse("2024-09-25T00:00", DateTimeFormatter.ISO_DATE_TIME));
        assertEquals(actual.toString(), expected.toString());
    }

    @Test
    public void commandParsing_invalidList() {
        assertThrows(KuroException.class, () -> parser.parse("lis", tasks));
    }
}
