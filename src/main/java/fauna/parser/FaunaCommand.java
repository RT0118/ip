package fauna.parser;

/**
 * FaunaCommand stores all available commands,
 * command descriptions and command regex for
 * extracting variables from user input
 */
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

    private static final String COMMAND = "(?<command>\\w+)";
    private static final String TASK_NAME_OR_INDEX = "(?:\\s+(?<name>.+?))?";
    private static final String TASK_BY_DATE = "(?:\\s+/by\\s+(?<byDate>.+))?";
    private static final String TASK_FROM_DATE = "(?:\\s+/from\\s+(?<fromDate>.+?)";
    private static final String TASK_TO_DATE = "(?:\\s+/to\\s+(?<toDate>.+))?)?";
    private final String description;


    FaunaCommand(String description) {
        this.description = description;
    }

    /**
     * <p>Gets command as string, converting it to a FaunaCommand
     * </p>
     * @param commandString string representation of the command
     * @return a FaunaCommand that represents the commandString
     */
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

    /**
     * <p>Provides a regex pattern string with matching groups
     * to extract information from user input
     * </p>
     * @return regex pattern string
     */
    public String getCommandRegexPattern() {
        switch (this) {
        case LIST,BYE:
            return String.format("^%s$", COMMAND);
        case TODO, MARK, UNMARK, DELETE:
            return String.format("^%s%s$", COMMAND, TASK_NAME_OR_INDEX);
        case DEADLINE:
            return String.format("^%s%s%s$", COMMAND, TASK_NAME_OR_INDEX, TASK_BY_DATE);
        case EVENT:
            return String.format("^%s%s%s%s$",
                COMMAND, TASK_NAME_OR_INDEX, TASK_FROM_DATE, TASK_TO_DATE);
        default:
            return ".*";
        }
    }

}
