public class EventTask extends Task {
    private final String from;
    private final String to;

    private EventTask(EventTask eventTask, Boolean isDone) {
        super(eventTask, isDone);
        this.from = eventTask.from;
        this.to = eventTask.to;
    }

    public EventTask(String taskName, String from, String to) {
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

    public String toString() {
        return String.format("[E]%s (from: %s to: %s)", super.toString(),
                this.from, this.to);
    }
}
