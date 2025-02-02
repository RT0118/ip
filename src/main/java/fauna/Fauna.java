package fauna.ui;

import fauna.exceptions.InvalidUserInputException;
import fauna.exceptions.StorageException;
import fauna.exceptions.TaskListIndexOutOfBounds;
import fauna.parser.ParsedUserInput;
import fauna.parser.UserInputParser;
import fauna.storage.Storage;
import fauna.task.Task;
import fauna.task.ToDoTask;
import fauna.task.DeadlineTask;
import fauna.task.EventTask;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;


public class Fauna {
    private static final String CHATBOT_NAME = "Fauna";
    private static final String SAVE_FILE_LOCATION = "./fauna.txt";
    private static ArrayList<Task> taskList;
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

    private static void addDeadlineToTaskList(String taskName, LocalDateTime datetime) {
        Task task = new DeadlineTask(taskName, datetime);
        taskList.add(task);
        printAddTaskPrompt(task);
    }

    private static void addEventToTaskList(String taskName, LocalDateTime from, LocalDateTime to) {
        Task task = new EventTask(taskName, from, to);
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
        // startup
        Storage storage = new Storage(SAVE_FILE_LOCATION);
        taskList = storage.restore();

        // run
        String logo = """
             _____ _   _   _ _   _    _    
            |  ___/ \\ | | | | \\ | |  / \\   
            | |_ / _ \\| | | |  \\| | / _ \\  
            |  _/ ___ \\ |_| | |\\  |/ ___ \\ 
            |_|/_/   \\_\\___/|_| \\_/_/   \\_\\""";
        System.out.println("Hello from\n" + logo + "\n");

        // greet the user
        System.out.println("____________________________________________________________");
        System.out.println("Hello hello! I'm " + CHATBOT_NAME);
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________\n");

        // chatbot
        Scanner sc = new Scanner(System.in);
        while (continueChat) {
            // get user's input
            String userInput = sc.nextLine();
            System.out.println("____________________________________________________________");

            try {
                int taskIndex;
                String taskName;

                ParsedUserInput parsedInput = UserInputParser.parse(userInput);
                switch (parsedInput.getCommand()) {
                case LIST:
                    listTasksInTaskList();
                    break;
                case MARK:
                    taskIndex = parsedInput.getTaskNumber();
                    markTaskAsDone(taskIndex);
                    break;
                case UNMARK:
                    taskIndex = parsedInput.getTaskNumber();
                    markTaskAsUndone(taskIndex);
                    break;
                case BYE:
                    continueChat = false;
                    break;
                case TODO:
                    taskName = parsedInput.getTaskName();
                    addTodoToTaskList(taskName);
                    break;
                case DEADLINE:
                    taskName = parsedInput.getTaskName();
                    LocalDateTime deadline = parsedInput.getTaskByDatetime();
                    addDeadlineToTaskList(taskName, deadline);
                    break;
                case EVENT:
                    taskName = parsedInput.getTaskName();
                    LocalDateTime from = parsedInput.getTaskFromDatetime();
                    LocalDateTime to = parsedInput.getTaskToDatetime();
                    addEventToTaskList(taskName, from, to);
                    break;
                case DELETE:
                    taskIndex = parsedInput.getTaskNumber();
                    deleteTask(taskIndex);
                    break;
                default:
                    System.out.println("Uuuu, I don't know what you mean by that :(");
                    break;
                }
            } catch (InvalidUserInputException | TaskListIndexOutOfBounds exception) {
                System.out.println(exception.getMessage());
            }

            System.out.println("____________________________________________________________");
        }

        // print exit message
        System.out.println("Faunwell! Hope to see you again soon!");
        System.out.println("____________________________________________________________");

        // save and cleanup
        try {
            storage.save(taskList);
        } catch (StorageException storageException) {
            System.out.println(storageException.getMessage());
        }
        sc.close();
    }

}