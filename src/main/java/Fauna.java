import exceptions.InvalidUserInputException;
import exceptions.TaskListIndexOutOfBounds;
import parser.ParsedUserInput;
import parser.UserInputParser;

import java.util.ArrayList;
import java.util.Scanner;

import static parser.FaunaCommand.*;


public class Fauna {
    private static final String chatbotName = "Fauna";
    private static ArrayList<Task> taskList = new ArrayList<Task>();
    private static boolean continueChat = true;

    private static void listTasksInTaskList() {
        if (taskList.isEmpty()) {
            System.out.println("Ooh, you don't have any tasks available!");
            return;
        }

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

    private static boolean taskListIndexInvalid(int taskIndex) {
        return taskIndex < 0 || taskIndex >= taskList.size();
    }

    private static void markTaskAsDone(int taskIndex) {
        if (taskListIndexInvalid(taskIndex)) {
            throw new TaskListIndexOutOfBounds(
                String.format("task %d does not exist!", taskIndex));
        }

        Task modifiedTask = taskList.get(taskIndex).markAsDone();
        taskList.set(taskIndex, modifiedTask);
        System.out.println("Nice! I've marked this task as done:\n\t" + modifiedTask);
    }

    private static void markTaskAsUndone(int taskIndex) {
        if (taskListIndexInvalid(taskIndex)) {
            throw new TaskListIndexOutOfBounds(
                String.format("task %d does not exist!", taskIndex));
        }

        Task modifiedTask = taskList.get(taskIndex).markAsUndone();
        taskList.set(taskIndex, modifiedTask);
        System.out.println("Okay, I've marked this task as undone:\n\t" + modifiedTask);
    }

    private static void deleteTask(int taskIndex) {
        if (taskListIndexInvalid(taskIndex)) {
            throw new TaskListIndexOutOfBounds(
                String.format("task %d does not exist!", taskIndex));
        }

        Task deletedTask = taskList.remove(taskIndex);
        System.out.println("Alright, I've removed the task:\n\t" + deletedTask);
        System.out.printf("Now, you have %d tasks in your list.\n", taskList.size());
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

            try {
                ParsedUserInput parsedInput = UserInputParser.parse(userInput);
                switch (parsedInput.getCommand()) {
                    case LIST: {
                        listTasksInTaskList();
                        break;
                    }
                    case MARK: {
                        int taskIndex = parsedInput.getTaskNumber();
                        markTaskAsDone(taskIndex);
                        break;
                    }
                    case UNMARK: {
                        int taskIndex = parsedInput.getTaskNumber();
                        markTaskAsUndone(taskIndex);
                        break;
                    }
                    case BYE: {
                        continueChat = false;
                        break;
                    }
                    case TODO: {
                        String taskName = parsedInput.getTaskName();
                        addTodoToTaskList(taskName);
                        break;
                    }
                    case DEADLINE: {
                        String taskName = parsedInput.getTaskName();
                        String deadline = parsedInput.getTaskDatetime();
                        addDeadlineToTaskList(taskName, deadline);
                        break;
                    }
                    case EVENT: {
                        String taskName = parsedInput.getTaskName();
                        String eventTime = parsedInput.getTaskDatetime();
                        addEventToTaskList(taskName, eventTime);
                        break;
                    }
                    case DELETE: {
                        int taskIndex = parsedInput.getTaskNumber();
                        deleteTask(taskIndex);
                        break;
                    }
                    default: {
                        System.out.println("Uuuu, I don't know what you mean by that :(");
                        break;
                    }
                }
            } catch (InvalidUserInputException | TaskListIndexOutOfBounds exception) {
                System.out.println(exception.getMessage());
            }

            System.out.println("____________________________________________________________");
        }

        // print exit message
        System.out.println("Faunwell! Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }

}