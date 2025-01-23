package parser;

public enum FaunaCommand {
    LIST("Lists all tasks"),
    BYE("Exit from the chatbot"),
    TODO("Adds a ToDo task to the list"),
    DEADLINE("Adds a deadline task to the list"),
    EVENT("Adds an event to the list"),
    MARK("Marks a task as done"),
    UNMARK("Marks a task as undone"),
    DELETE("Deletes a task from the list"),
    INVALID("This command does not exist");

    private final String description;

    FaunaCommand(String description) {
        this.description = description;
    }

    public static FaunaCommand fromString(String commandString) {
        switch (commandString) {
            case "list": return LIST;
            case "bye": return BYE;
            case "todo": return TODO;
            case "deadline": return DEADLINE;
            case "event": return EVENT;
            case "mark": return MARK;
            case "unmark": return UNMARK;
            case "delete": return DELETE;
            default: return INVALID;
        }
    }

    public String getDescription() {
        return this.description;
    }
}
