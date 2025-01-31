public class DeadlineTask extends Task {
    private final String by;

    private DeadlineTask(DeadlineTask deadlineTask, boolean isDone) {
        super(deadlineTask, isDone);
        this.by = deadlineTask.by;
    }

    public DeadlineTask(String taskName, boolean isDone, String by) {
        super(taskName, isDone);
        this.by = by;
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

    @Override
    public String serialize() {
        return String.format("T\t%s\t%s\n", super.serialize(), this.by);
    }

    public String toString() {
        return String.format("[D]%s (by: %s)",
                super.toString(), this.by);
    }
}
