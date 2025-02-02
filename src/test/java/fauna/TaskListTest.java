package fauna;

import fauna.task.Task;
import fauna.task.TaskList;
import fauna.task.ToDoTask;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {
    @Test
    public void addTask_threeTasksInList_success() {
        Task task1 = new ToDoTask("task1");
        Task task2 = new ToDoTask("task1");
        Task task3 = new ToDoTask("task1");
        TaskList tasks = new TaskList().addTask(task1).addTask(task2).addTask(task3);

        assertEquals(3, tasks.size());
    }

    @Test
    public void markTask_secondTaskInList_success() {
        Task task1 = new ToDoTask("task1");
        Task task2 = new ToDoTask("task1");
        Task task3 = new ToDoTask("task1");
        String expected = task2.markAsDone().toString();
        TaskList tasks = new TaskList().addTask(task1).addTask(task2).addTask(task3);
        String result = tasks.markTaskAsDone(2).getTask(2).toString();

        assertEquals(expected, result);
    }
}
