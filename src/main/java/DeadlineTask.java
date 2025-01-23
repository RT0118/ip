public class DeadlineTask extends Task {
    private final String by;

    private DeadlineTask(DeadlineTask deadlineTask, Boolean isDone) {
        super(deadlineTask, isDone);
        this.by = deadlineTask.by;
    }

    public DeadlineTask(String taskName, String by) {
        super(taskName);
        this.by = by;
    }

    public Task markAsDone() {
        return new DeadlineTask(this, true);
    }

    public Task markAsUndone() {
        return new DeadlineTask(this, false);
    }

    public String toString() {
        return String.format("[D]%s (by: %s)",
                super.toString(), this.by);
    }
}
