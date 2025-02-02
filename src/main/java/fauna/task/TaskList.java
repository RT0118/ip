package fauna.task;

import fauna.exceptions.TaskListException;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class TaskList {
    private final List<Task> tasks;

    public TaskList() {
        this.tasks = List.<Task>of();
    }

    private TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    private boolean isTaskListIndexInvalid(int index) {
        return index < 0 || index >= this.tasks.size();
    }

    public Task getTask(int index) {
        int trueIndex = index - 1;
        if (isTaskListIndexInvalid(trueIndex)) {
            throw new TaskListException(
                    String.format("task %d does not exist!", index)
            );
        }
        return this.tasks.get(trueIndex);
    }

    public TaskList addTask(Task task) {
        List<Task> modifiedTasksList = Stream.concat(
                this.tasks.stream(), Stream.of(task)).toList();
        return new TaskList(modifiedTasksList);
    }

    public TaskList removeTask(int index) throws TaskListException {
        int trueIndex = index - 1;
        if (isTaskListIndexInvalid(trueIndex)) {
            throw new TaskListException(
                    String.format("task %d does not exist!", index));
        }

        List<Task> modifiedTaskList = IntStream
                .range(0, this.tasks.size())
                .filter(i -> i != trueIndex)
                .mapToObj(i -> this.tasks.get(i))
                .toList();
        return new TaskList(modifiedTaskList);
    }

    public TaskList markTaskAsDone(int index) throws TaskListException {
        int trueIndex = index - 1;
        if (isTaskListIndexInvalid(trueIndex)) {
            throw new TaskListException(
                    String.format("task %d does not exist!", index));
        }

        List<Task> modifiedTaskList = IntStream
                .range(0, this.tasks.size())
                .mapToObj(i -> {
                    if (i == trueIndex) {
                        return this.tasks.get(i).markAsDone();
                    }
                    return this.tasks.get(i);
                }).toList();
        return new TaskList(modifiedTaskList);
    }

    public TaskList markTaskAsUndone(int index) throws TaskListException {
        int trueIndex = index - 1;
        if (isTaskListIndexInvalid(trueIndex)) {
            throw new TaskListException(
                    String.format("task %d does not exist!", index));
        }

        List<Task> modifiedTaskList = IntStream
                .range(0, this.tasks.size())
                .mapToObj(i -> {
                    if (i == trueIndex) {
                        return this.tasks.get(i).markAsUndone();
                    }
                    return this.tasks.get(i);
                }).toList();
        return new TaskList(modifiedTaskList);
    }

    public int size() {
        return this.tasks.size();
    }

    public boolean isEmpty() {
        return this.tasks.isEmpty();
    }

    public List<Task> getTasksAsList() {
        return this.tasks;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 1; i <= this.tasks.size(); i++) {
            result.append(String.format("%d. %s\n", i, this.getTask(i)));
        }
        return result.toString();
    }
}
