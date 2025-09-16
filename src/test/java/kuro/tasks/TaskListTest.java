package kuro.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import kuro.exceptions.KuroException;

public class TaskListTest {
    private static final TaskList lists = new TaskList(new ArrayList<Task>());

    @Test
    public void addTask_validTask_noExceptionThrown() {
        Todo task = new Todo("Clean up");
        Assertions.assertDoesNotThrow(() -> {
            lists.addTask(task);
        });
    }

    @Test
    public void addTask_multipleTasks_sizeIncreases() {
        TaskList activeLists = new TaskList(new ArrayList<>());

        activeLists.addTask(new Todo("Read book"));
        activeLists.addTask(new Todo("Clean room"));

        assertEquals(2, activeLists.getSize());
    }

    @Test
    public void addTask_nullTask_assertionError() {
        Assertions.assertThrows(AssertionError.class, () -> {
            lists.addTask(null);
        });
    }

    @Test
    public void getTask_validTask_returnTask() {
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
