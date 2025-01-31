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

    public Task markAsDone() {
        return new DeadlineTask(this, true);
    }

    public Task markAsUndone() {
        return new DeadlineTask(this, false);
    }

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
