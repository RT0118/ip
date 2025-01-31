package fauna.task;

public class ToDoTask extends Task{
    public ToDoTask(ToDoTask toDoTask, boolean isDone) {
        super(toDoTask, isDone);
    }

    public ToDoTask(String taskName, boolean isDone) {
        super(taskName, isDone);
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

    @Override
    public String serialize() {
        return String.format("T\t%s\n", super.serialize());
    }

    public String toString() {
        return String.format("[T]%s", super.toString());
    }
}
