package fauna.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import fauna.exceptions.StorageException;

/**
 * Task contains the basic implementation and variables of a task
 */
public abstract class Task {
    protected static final DateTimeFormatter
            DATETIME_PRINT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, HHmm");
    private final String taskName;
    private final boolean isDone;

    /**
     * Constructor for new Task
     * @param taskName name of the task
     */
    public Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
    }

    /**
     * Constructor for new Task when isDone should be modified
     * @param taskName name of the task
     * @param isDone boolean variable representing "is task done?"
     */
    public Task(String taskName, boolean isDone) {
        this.taskName = taskName;
        this.isDone = isDone;
    }

    /**
     * Constructor for new Task using old Task object
     * @param task previous state of Task to copy over
     * @param isDone boolean variable representing "is task done?"
     */
    protected Task(Task task, boolean isDone) {
        this.taskName = task.taskName;
        this.isDone = isDone;
    }

    /**
     * Abstract method for marking task as done
     * @return modified task
     */
    public abstract Task markAsDone();

    /**
     * Abstract method for marking task as undone
     * @return modified task
     */
    public abstract Task markAsUndone();

    public String serialize() {
        return String.format("%s\t%s", this.taskName, this.isDone);
    }

    /**
     * Converts serialized task string to Task object
     * @param serializedTask output from calling serialize()
     * @return Task object after deserialization
     */
    public static Task fromSerializedString(String serializedTask) {
        String[] splitString = serializedTask.split("\t");
        String taskType = splitString[0];
        String taskName = splitString[1];
        boolean taskIsDone = splitString[2].equals("true");
        switch(taskType) {
        case "T":
            return new ToDoTask(taskName, taskIsDone);
        case "D":
            LocalDateTime by = LocalDateTime.parse(splitString[3]);
            return new DeadlineTask(taskName, taskIsDone, by);
        case "E":
            LocalDateTime from = LocalDateTime.parse(splitString[3]);
            LocalDateTime to = LocalDateTime.parse(splitString[4]);
            return new EventTask(taskName, taskIsDone, from, to);
        default:
            throw new StorageException("the task string is invalid!");
        }
    }

    /**
     * Lookup keywords within task name
     * @param keyword words to look for
     * @return boolean value if words are contained in task name
     */
    public boolean keywordInTaskName(String keyword) {
        return this.taskName.contains(keyword);
    }

    /**
     * Display the task as a string
     * @return task as String object
     */
    public String toString() {
        return String.format("[%s] %s",
                this.isDone ? "X" : " ",
                this.taskName);
    }

}
