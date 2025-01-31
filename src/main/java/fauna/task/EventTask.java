package fauna.task;

import java.time.LocalDateTime;

public class EventTask extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;

    private EventTask(EventTask eventTask, boolean isDone) {
        super(eventTask, isDone);
        this.from = eventTask.from;
        this.to = eventTask.to;
    }

    public EventTask(String taskName, boolean isDone, LocalDateTime from, LocalDateTime to) {
        super(taskName, isDone);
        this.from = from;
        this.to = to;
    }

    public EventTask(String taskName, LocalDateTime from, LocalDateTime to) {
        super(taskName);
        this.from = from;
        this.to = to;
    }

    public Task markAsDone() {
        return new EventTask(this, true);
    }

    public Task markAsUndone() {
        return new EventTask(this, false);
    }

    @Override
    public String serialize() {
        return String.format("E\t%s\t%s\t%s\n", super.serialize(), this.from, this.to);
    }

    public String toString() {
        return String.format("[E]%s (from: %sH to: %sH)", super.toString(),
                this.from.format(DATETIME_PRINT_FORMATTER),
                this.to.format(DATETIME_PRINT_FORMATTER));
    }
}
