import java.util.ArrayList;
import java.util.Scanner;


public class Fauna {
    private static final String chatbotName = "Fauna";
    private static ArrayList<Task> taskList = new ArrayList<Task>();
    private static boolean continueChat = true;

    private static void listTasksInTaskList() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, taskList.get(i));
        }
    }
    private static void printAddTaskPrompt(Task task) {
        System.out.println("Got it. I've added the task:\n\t" + task);
        System.out.printf("Now, you have %d tasks in your list.\n", taskList.size());
    }

    private static void addTodoToTaskList(String taskName) {
        Task task = new ToDoTask(taskName);
        taskList.add(task);
        printAddTaskPrompt(task);
    }

    private static void addDeadlineToTaskList(String taskName, String datetime) {
        Task task = new DeadlineTask(taskName, datetime);
        taskList.add(task);
        printAddTaskPrompt(task);
    }

    private static void addEventToTaskList(String taskName, String datetime) {
        Task task = new EventTask(taskName, datetime);
        taskList.add(task);
        printAddTaskPrompt(task);
    }

    private static void markTaskAsDone(int taskIndex) {
        Task modifiedTask = taskList.get(taskIndex).markAsDone();
        taskList.set(taskIndex, modifiedTask);
        System.out.println("Nice! I've marked this task as done:\n\t" + modifiedTask);
    }

    private static void markTaskAsUndone(int taskIndex) {
        Task modifiedTask = taskList.get(taskIndex).markAsUndone();
        taskList.set(taskIndex, modifiedTask);
        System.out.println("Okay, I've marked this task as undone:\n\t" + modifiedTask);
    }

    private static String getCommandFromUserInput(String userInput) {
        return userInput.split(" ", 2)[0];
    }

    private static int getTaskIndexFromUserInput(String userInput) {
        String taskIndexString = userInput.split(" ", 2)[1];
        return Integer.parseInt(taskIndexString) - 1;
    }

    private static String getTaskNameFromUserInput(String userInput) {
        return userInput.split(" ", 2)[1];
    }

    private static String getTaskNameFromUserInput(String userInput, String delimiter) {
        String taskNameAndDatetime = getTaskNameFromUserInput(userInput);
        return taskNameAndDatetime.split(delimiter, 2)[0];
    }

    private static String getTaskDatetimeFromUserInput(String userInput, String delimiter) {
        String taskNameAndDatetime = getTaskNameFromUserInput(userInput);
        return taskNameAndDatetime.split(delimiter, 2)[1];
    }

    public static void main(String[] args) {
        String logo = """
             _____ _   _   _ _   _    _    
            |  ___/ \\ | | | | \\ | |  / \\   
            | |_ / _ \\| | | |  \\| | / _ \\  
            |  _/ ___ \\ |_| | |\\  |/ ___ \\ 
            |_|/_/   \\_\\___/|_| \\_/_/   \\_\\""";
        System.out.println("Hello from\n" + logo + "\n");

        // greet the user
        System.out.println("____________________________________________________________");
        System.out.println("Hello hello! I'm " + chatbotName);
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________\n");

        // chatbot
        Scanner sc = new Scanner(System.in);
        while (continueChat) {
            // get user's input
            String userInput = sc.nextLine();
            System.out.println("____________________________________________________________");

            String command = getCommandFromUserInput(userInput);
            switch (command) {
                case "list": {
                    listTasksInTaskList();
                    break;
                }
                case "done": {
                    int taskIndex = getTaskIndexFromUserInput(userInput);
                    markTaskAsDone(taskIndex);
                    break;
                }
                case "undone": {
                    int taskIndex = getTaskIndexFromUserInput(userInput);
                    markTaskAsUndone(taskIndex);
                    break;
                }
                case "bye": {
                    continueChat = false;
                    break;
                }
                case "todo": {
                    String taskName = getTaskNameFromUserInput(userInput);
                    addTodoToTaskList(taskName);
                    break;
                }
                case "deadline": {
                    String taskName = getTaskNameFromUserInput(userInput, "/by");
                    String deadline = getTaskDatetimeFromUserInput(userInput, "/by");
                    addDeadlineToTaskList(taskName, deadline);
                    break;
                }
                case "event": {
                    String taskName = getTaskNameFromUserInput(userInput, "/at");
                    String eventTime = getTaskDatetimeFromUserInput(userInput, "/at");
                    addEventToTaskList(taskName, eventTime);
                    break;
                }
                default: {
                    System.out.println("Unknown command???");
                    break;
                }
            }

            System.out.println("____________________________________________________________");
        }

        // print exit message
        System.out.println("Faunwell! Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }
}