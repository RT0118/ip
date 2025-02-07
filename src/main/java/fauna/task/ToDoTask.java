package fauna.task;

public class ToDoTask extends Task {
    public ToDoTask(ToDoTask toDoTask, boolean isDone) {
        super(toDoTask, isDone);
    }

    public ToDoTask(String taskName, boolean isDone) {
        super(taskName, isDone);
    }

    public ToDoTask(String taskName) {
        super(taskName);
    }

    /**
     * <p>Marks the task as done and returns a new instance
     * that is immutable
     * </p>
     * @return immutable Task object
     */
    public Task markAsDone() {
        return new ToDoTask(this, true);
    }

    /**
     * <p>Marks the task as undone and returns a new instance
     * that is immutable
     * </p>
     * @return immutable Task object
     */
    public Task markAsUndone() {
        return new ToDoTask(this, false);
    }

    /**
     * <p>Serializes the task into string format used for saving
     * </p>
     * @return string representation of task serialized
     */
    @Override
    public String serialize() {
        return String.format("T\t%s\n", super.serialize());
    }

    public String toString() {
        return String.format("[T]%s", super.toString());
    }
}
