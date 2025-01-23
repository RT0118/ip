public class EventTask extends Task {
    private final String at;

    private EventTask(EventTask eventTask, Boolean isDone) {
        super(eventTask, isDone);
        this.at = eventTask.at;
    }

    public EventTask(String taskName, String at) {
        super(taskName);
        this.at = at;
    }

    public Task markAsDone() {
        return new EventTask(this, true);
    }

    public Task markAsUndone() {
        return new EventTask(this, false);
    }

    public String toString() {
        return String.format("[E]%s (by: %s)",
                super.toString(), this.at);
    }
}
