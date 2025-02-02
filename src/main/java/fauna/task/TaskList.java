package fauna.task;

import fauna.exceptions.TaskListException;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * TaskList stores Task objects for easy management of
 * tasks in an immutable way
 */
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

    /**
     * <p>Get the task based on the index provided by the user
     * </p>
     * @param index index of task to get (starts from 1)
     * @return Task object
     * @exception TaskListException task at specified index does not exist
     */
    public Task getTask(int index) {
        int trueIndex = index - 1;
        if (isTaskListIndexInvalid(trueIndex)) {
            throw new TaskListException(
                    String.format("task %d does not exist!", index)
            );
        }
        return this.tasks.get(trueIndex);
    }

    /**
     * <p>Add a new task to the list
     * </p>
     * @param task Task object to be added
     * @return TaskList containing newly added task
     */
    public TaskList addTask(Task task) {
        List<Task> modifiedTasksList = Stream.concat(
                this.tasks.stream(), Stream.of(task)).toList();
        return new TaskList(modifiedTasksList);
    }

    /**
     * <p>Remove a task from the list
     * </p>
     * @param index index of task to remove (starts from 1)
     * @return TaskList excluding removed task
     * @exception TaskListException task at specific index does not exist
     */
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

    /**
     * <p>Marks a task as done
     * </p>
     * @param index index of task to mark (starts from 1)
     * @return TaskList with modified task
     * @exception TaskListException task at specific index does not exist
     */
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

    /**
     * <p>Marks a task as undone
     * </p>
     * @param index index of task to unmark (starts from 1)
     * @return TaskList with modified task
     * @exception TaskListException task at specific index does not exist
     */
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

    /**
     * <p>Getter for the raw list of tasks
     * </p>
     * @return List of Task objects
     */
    public List<Task> getTasksAsList() {
        return this.tasks;
    }

    /**
     * <p>Get the list of tasks as a string in numbered list form
     * </p>
     * @return string representation of the list of tasks
     */
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 1; i <= this.tasks.size(); i++) {
            result.append(String.format("%d. %s\n", i, this.getTask(i)));
        }
        return result.toString();
    }
}
