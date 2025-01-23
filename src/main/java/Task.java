public abstract class Task {
    private final String taskName;
    private final boolean isDone;


    public Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
    }

    protected Task(Task task, boolean isDone) {
        this.taskName = task.taskName;
        this.isDone = isDone;
    }

    public abstract Task markAsDone();

    public abstract Task markAsUndone();

    public String toString() {
        return String.format("[%s] %s",
                this.isDone ? "X" : " ",
                this.taskName);
    }

}
