package kuro.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import kuro.exceptions.KuroException;

public class TaskListTest {
    private static final TaskList lists = new TaskList(new ArrayList<Task>());
    
    @Test
    public void taskList_addTask_noExceptionThrown() {
        Todo task = new Todo("Clean up");
        Assertions.assertDoesNotThrow(() -> {
            lists.addTask(task);;
        });
    }
    
    @Test
    public void taskList_getTask_returnTask() {
        Todo actualTask = new Todo("Clean up");
        lists.addTask(actualTask);
        Task expectedTask = new Task("Failed Example");
        try {
            expectedTask = lists.getTask(0);
        } catch (KuroException e) {
            fail();
        }
        assertEquals(actualTask, expectedTask);
    }
}
