package fauna.task;

import java.time.LocalDateTime;

/**
 * Represents a deadline with an expiry date
 */
public class DeadlineTask extends Task {
    private final LocalDateTime by;

    private DeadlineTask(DeadlineTask deadlineTask, boolean isDone) {
        super(deadlineTask, isDone);
        this.by = deadlineTask.by;
    }

    /**
     * Constructor for DeadlineTask
     * @param taskName name of task
     * @param isDone boolean variable representing "is task done?"
     * @param by expiry date of task
     */
    public DeadlineTask(String taskName, boolean isDone, LocalDateTime by) {
        super(taskName, isDone);
        this.by = by;
    }

    /**
     * Constructor for DeadlineTask
     * @param taskName name of task
     * @param by expiry date of task
     */
    public DeadlineTask(String taskName, LocalDateTime by) {
        super(taskName);
        this.by = by;
    }

    public DeadlineTask(DeadlineTask deadlineTask, String tag) {
        super(deadlineTask, tag);
        this.by = deadlineTask.by;
    }

    public DeadlineTask(String taskName, boolean isDone, String taskTag, LocalDateTime by) {
        super(taskName, isDone, taskTag);
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

    public Task addTag(String tag) {
        String formattedTag = "{#" + tag + "}";
        return new DeadlineTask(this, formattedTag);
    }

    /**
     * Display the task as a string
     * @return task as String object
     */
    public String toString() {
        return String.format("[D]%s (by: %sH)",
                super.toString(),
                this.by.format(DATETIME_PRINT_FORMATTER));
    }
}
