package fauna.ui;

import java.util.Scanner;

import fauna.exceptions.FaunaRuntimeException;
import fauna.parser.FaunaCommand;
import fauna.task.Task;
import fauna.task.TaskList;

/**
 * Ui returns the messages and chatbot responses to display depending on action performed
 */
public class Ui {
    private static final String LINE_SEPARATOR = "_________________________________________________________________";
    private static final String CHATBOT_LOGO = " _____ _   _   _ _   _    _    "
            + "\n|  ___/ \\ | | | | \\ | |  / \\   "
            + "\n| |_ / _ \\| | | |  \\| | / _ \\  "
            + "\n|  _/ ___ \\ |_| | |\\  |/ ___ \\ "
            + "\n|_|/_/   \\_\\___/|_| \\_/_/   \\_\\";


    private final Scanner userInputScanner;

    public Ui() {
        this.userInputScanner = new Scanner(System.in);
    }

    /**
     * <p>Print the logo for startup
     * </p>
     * @return Fauna logo
     */
    public String displayLogo() {
        return "Hello from\n" + CHATBOT_LOGO + "\n" + LINE_SEPARATOR;
    }

    /**
     * <p>Print the welcome message on startup
     * </p>
     */
    public String showWelcomeMessage() {
        return "Konfauna~ this is your chatbot kirin Fauna here!" + "\n"
                + "What would you like me to do?" + "\n"
                + LINE_SEPARATOR;
    }

    /**
     * <p>Print the goodbye message when exiting
     * </p>
     */
    public String showGoodbyeMessage() {
        return "Faunwell! Hope to see you again soon!" + "\n"
                + LINE_SEPARATOR;
    }

    /**
     * <p>Obtain user input from Scanner (for console only)
     * </p>
     * @return raw user input
     */
    public String getUserInput() {
        String userInput = this.userInputScanner.nextLine();
        System.out.println(LINE_SEPARATOR);
        return userInput;
    }

    /**
     * <p>Print the list of tasks saved in a TaskList object
     * </p>
     * @param taskList TaskList object
     * @return Fauna's response
     */
    public String listTasksInTaskList(TaskList taskList) {
        String response = "";
        if (taskList.isEmpty()) {
            response += "Ooh, you don't have any tasks available!\n";
        } else {
            response += "Here are the tasks in your list:" + "\n";
            response += taskList;
            response += "\n";
        }
        return response + LINE_SEPARATOR;
    }

    /**
     * <p>Print the task added and new size of TaskList
     * </p>
     * @param task the Task object added
     * @param taskListSize the size of TaskList after add
     * @return Fauna's response
     */
    public String printAddTaskPrompt(Task task, int taskListSize) {
        return "Got it. I've added the task:\n\t" + task + "\n"
                + String.format("Now, you have %d tasks in your list.\n", taskListSize)
                + LINE_SEPARATOR;
    }

    /**
     * <p>Print the task marked as done
     * </p>
     * @param task the Task object modified
     * @return Fauna's response
     */
    public String printMarkTaskAsDone(Task task) {
        return "Nice! I've marked this task as done:\n\t" + task + "\n"
            + LINE_SEPARATOR;
    }

    /**
     * <p>Print the task marked as undone
     * </p>
     * @param task the Task object modified
     * @return Fauna's response
     */
    public String printMarkTaskAsUndone(Task task) {
        return "Okay, I've marked this task as undone:\n\t" + task + "\n"
                + LINE_SEPARATOR;
    }

    /**
     * <p>Print the task removed and new size of TaskList
     * </p>
     * @param task the Task object removed
     * @param taskListSize the size of TaskList after removal
     * @return Fauna's response
     */
    public String printDeleteTask(Task task, int taskListSize) {
        return "Alright, I've removed the task:\n\t" + task + "\n"
                + String.format("Now, you have %d tasks in your list.\n", taskListSize)
                + LINE_SEPARATOR;
    }

    /**
     * <p>Print the tasks found after searching in TaskList
     * </p>
     * @param tasksFound list of tasks found in a numbered list
     * @param keywords the search term used
     * @return Fauna's response
     */
    public String printFindTask(String tasksFound, String keywords) {
        String response = "";
        if (tasksFound.isBlank()) {
            response += String.format("Umm, I couldn't find anything related to '%s'.\n", keywords);
        } else {
            response += "Here, I found some matching tasks in your list:\n";
            response += tasksFound;
            response += "\n";
        }
        return response + LINE_SEPARATOR;
    }

    /**
     * <p>Print any exceptions/error messages
     * </p>
     * @param exception FaunaRuntimeException caught
     * @return Fauna's response
     */
    public String printErrorMessage(FaunaRuntimeException exception) {
        return exception.getMessage();
    }

    /**
     * <p>Print an error message when an unknown command is provided
     * </p>
     * @return Fauna's response
     */
    public String printUnknownCommandErrorMessage() {
        return "Uuuu, I don't know what you mean by that :(";
    }

    /**
     * <p>Print all available Fauna commands with description
     * </p>
     * @return Fauna's response
     */
    public String printAllAvailableCommands() {
        StringBuilder response = new StringBuilder("Here are the available commands:\n");
        for (FaunaCommand command : FaunaCommand.values()) {
            response.append(String.format("%s: %s\n", command, command.getDescription()));
        }
        return response + LINE_SEPARATOR;
    }
}
