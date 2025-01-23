public class ToDoTask extends Task{
    private ToDoTask(ToDoTask toDoTask, Boolean isDone) {
        super(toDoTask, isDone);
    }

    public ToDoTask(String taskName) {
        super(taskName);
    }

    public Task markAsDone() {
        return new ToDoTask(this, true);
    }

    public Task markAsUndone() {
        return new ToDoTask(this, false);
    }

    public String toString() {
        return String.format("[T]%s", super.toString());
    }
}
