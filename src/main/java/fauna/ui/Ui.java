package fauna.ui;

import fauna.exceptions.FaunaRuntimeException;
import fauna.parser.FaunaCommand;
import fauna.task.Task;
import fauna.task.TaskList;

import java.util.Scanner;

public class Ui {
    private static final String LINE_SEPARATOR = "____________________________________________________________";
    private static final String CHATBOT_NAME = "Fauna";
    private static final String CHATBOT_LOGO = """
             _____ _   _   _ _   _    _    
            |  ___/ \\ | | | | \\ | |  / \\   
            | |_ / _ \\| | | |  \\| | / _ \\  
            |  _/ ___ \\ |_| | |\\  |/ ___ \\ 
            |_|/_/   \\_\\___/|_| \\_/_/   \\_\\""";

    private final Scanner userInputScanner;

    public Ui() {
        this.userInputScanner = new Scanner(System.in);
    }

    /**
     * <p>Print the welcome message on startup
     * </p>
     */
    public void showWelcomeMessage() {
        System.out.println("Hello from\n" + CHATBOT_LOGO + "\n");

        // greet the user
        System.out.println(LINE_SEPARATOR);
        System.out.println("Hello hello! I'm " + CHATBOT_NAME);
        System.out.println("What can I do for you?");
        System.out.println(LINE_SEPARATOR + "\n");
    }

    /**
     * <p>Print the goodbye message when exiting
     * </p>
     */
    public void showGoodbyeMessage() {
        System.out.println("Faunwell! Hope to see you again soon!");
        System.out.println(LINE_SEPARATOR);
    }

    /**
     * <p>Obtain user input from Scanner
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
     */
    public void listTasksInTaskList(TaskList taskList) {
        if (taskList.isEmpty()) {
            System.out.println("Ooh, you don't have any tasks available!");
            return;
        }

        System.out.println("Here are the tasks in your list:");
        System.out.println(taskList);
        System.out.println(LINE_SEPARATOR);
    }

    /**
     * <p>Print the task added and new size of TaskList
     * </p>
     * @param task the Task object added
     * @param taskListSize the size of TaskList after add
     */
    public void printAddTaskPrompt(Task task, int taskListSize) {
        System.out.println("Got it. I've added the task:\n\t" + task);
        System.out.printf("Now, you have %d tasks in your list.\n", taskListSize);
        System.out.println(LINE_SEPARATOR);
    }

    /**
     * <p>Print the task marked as done
     * </p>
     * @param task the Task object modified
     */
    public void printMarkTaskAsDone(Task task) {
        System.out.println("Nice! I've marked this task as done:\n\t" + task);
        System.out.println(LINE_SEPARATOR);
    }

    /**
     * <p>Print the task marked as undone
     * </p>
     * @param task the Task object modified
     */
    public void printMarkTaskAsUndone(Task task) {
        System.out.println("Okay, I've marked this task as undone:\n\t" + task);
        System.out.println(LINE_SEPARATOR);
    }

    /**
     * <p>Print the task removed and new size of TaskList
     * </p>
     * @param task the Task object removed
     * @param taskListSize the size of TaskList after removal
     */
    public void printDeleteTask(Task task, int taskListSize) {
        System.out.println("Alright, I've removed the task:\n\t" + task);
        System.out.printf("Now, you have %d tasks in your list.\n", taskListSize);
        System.out.println(LINE_SEPARATOR);
    }

    /**
     * <p>Print any exceptions/error messages
     * </p>
     * @param exception FaunaRuntimeException caught
     */
    public void printErrorMessage(FaunaRuntimeException exception) {
        System.out.println(exception.getMessage());
    }

    /**
     * <p>Print an error message when an unknown command is provided
     * </p>
     */
    public void printUnknownCommandErrorMessage() {
        System.out.println("Uuuu, I don't know what you mean by that :(");
    }
}