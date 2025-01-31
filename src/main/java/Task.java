import exceptions.InvalidUserInputException;
import exceptions.StorageException;
import jdk.jfr.Event;

public abstract class Task {
    private final String taskName;
    private final boolean isDone;

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
        boolean taskIsDone = splitString[2].equals("TRUE");
        switch(taskType) {
        case "T":
            return new ToDoTask(taskName, taskIsDone);
        case "D":
            String by = splitString[3];
            return new DeadlineTask(taskName, taskIsDone, by);
        case "E":
            String from = splitString[3];
            String to = splitString[4];
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
