public class Task {
    private final String taskName;
    private final boolean isDone;


    public Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
    }

    private Task(Task task, boolean isDone) {
        this.taskName = task.taskName;
        this.isDone = isDone;
    }

    // mark task as done
    public Task markAsDone() {
        return new Task(this, true);
    }

    public Task unmarkAsDone() {
        return new Task(this, false);
    }

    public String toString() {
        return String.format("[%s] %s",
            this.isDone ? "X" : " ",
            this.taskName);
    }

}
