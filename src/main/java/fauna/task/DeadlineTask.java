package fauna.task;

import java.time.LocalDateTime;

public class DeadlineTask extends Task {
    private final LocalDateTime by;

    private DeadlineTask(DeadlineTask deadlineTask, boolean isDone) {
        super(deadlineTask, isDone);
        this.by = deadlineTask.by;
    }

    public DeadlineTask(String taskName, boolean isDone, LocalDateTime by) {
        super(taskName, isDone);
        this.by = by;
    }

    public DeadlineTask(String taskName, LocalDateTime by) {
        super(taskName);
        this.by = by;
    }

    /**
     * <p>Marks the task as done and returns a new instance
     * that is immutable
     * </p>
     * @return immutable Task object
     */
    public Task markAsDone() {
        return new DeadlineTask(this, true);
    }

    /**
     * <p>Marks the task as undone and returns a new instance
     * that is immutable
     * </p>
     * @return immutable Task object
     */
    public Task markAsUndone() {
        return new DeadlineTask(this, false);
    }

    /**
     * <p>Serializes the task into string format used for saving
     * </p>
     * @return string representation of task serialized
     */
    @Override
    public String serialize() {
        return String.format("D\t%s\t%s\n", super.serialize(), this.by);
    }

    public String toString() {
        return String.format("[D]%s (by: %sH)",
                super.toString(),
                this.by.format(DATETIME_PRINT_FORMATTER));
    }
}
