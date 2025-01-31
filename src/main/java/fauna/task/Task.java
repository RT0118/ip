package fauna.task;

import fauna.exceptions.StorageException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Task {
    private final String taskName;
    private final boolean isDone;
    protected static final DateTimeFormatter
            DATETIME_PRINT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, HHmm");

    public Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
    }

    public Task(String taskName, boolean isDone) {
        this.taskName = taskName;
        this.isDone = isDone;
    }

    protected Task(Task task, boolean isDone) {
        this.taskName = task.taskName;
        this.isDone = isDone;
    }

    public abstract Task markAsDone();
    public abstract Task markAsUndone();

    public String serialize() {
        return String.format("%s\t%s", this.taskName, this.isDone);
    }

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

    public String toString() {
        return String.format("[%s] %s",
                this.isDone ? "X" : " ",
                this.taskName);
    }

}
