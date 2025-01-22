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

    private static void addTaskToTaskList(String taskName) {
        Task task = new Task(taskName);
        taskList.add(task);
        System.out.println("I've added the task: " + taskName);
    }

    private static String getCommandFromUserInput(String userInput) {
        return userInput.split(" ", 2)[0];
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
                case "list":
                    listTasksInTaskList();
                    break;
                case "mark":
                    //markTask(userInput);
                    break;
                case "unmark":
                    //unmarkTask(userInput);
                    break;
                case "bye":
                    continueChat = false;
                    break;
                default:
                    addTaskToTaskList(userInput);
            }
        }

        // print exit message
        System.out.println("Faunwell! Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }
}