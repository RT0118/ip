import fauna.parser.ParsedUserInput;
import fauna.parser.UserInputParser;
import fauna.storage.Storage;
import fauna.task.ToDoTask;
import fauna.task.DeadlineTask;
import fauna.task.EventTask;
import fauna.task.TaskList;
import fauna.task.Task;
import fauna.ui.Ui;
import fauna.exceptions.InvalidUserInputException;
import fauna.exceptions.StorageException;
import fauna.exceptions.TaskListException;

import java.time.LocalDateTime;


public class Fauna {
    private static final Ui ui = new Ui();
    private static final String SAVE_FILE_LOCATION = "./fauna.txt";
    private static TaskList taskList;
    private static boolean continueChat = true;



    private static void addTodoToTaskList(String taskName) {
        Task task = new ToDoTask(taskName);
        taskList = taskList.addTask(task);
        ui.printAddTaskPrompt(task, taskList.size());
    }

    private static void addDeadlineToTaskList(String taskName, LocalDateTime datetime) {
        Task task = new DeadlineTask(taskName, datetime);
        taskList = taskList.addTask(task);
        ui.printAddTaskPrompt(task, taskList.size());
    }

    private static void addEventToTaskList(String taskName, LocalDateTime from, LocalDateTime to) {
        Task task = new EventTask(taskName, from, to);
        taskList = taskList.addTask(task);
        ui.printAddTaskPrompt(task, taskList.size());
    }

    private static void markTaskAsDone(int taskIndex) {
        try {
            taskList = taskList.markTaskAsDone(taskIndex);
            ui.printMarkTaskAsDone(taskList.getTask(taskIndex));
        } catch (TaskListException taskListException) {
            ui.printErrorMessage(taskListException);
        }
    }

    private static void markTaskAsUndone(int taskIndex) {
        try {
            taskList = taskList.markTaskAsUndone(taskIndex);
            ui.printMarkTaskAsUndone(taskList.getTask(taskIndex));
        } catch (TaskListException taskListException) {
            ui.printErrorMessage(taskListException);
        }
    }

    private static void deleteTask(int taskIndex) {
        try {
            Task deletedTask = taskList.getTask(taskIndex);
            taskList = taskList.removeTask(taskIndex);
            ui.printDeleteTask(deletedTask, taskList.size());
        } catch (TaskListException taskListException) {
            ui.printErrorMessage(taskListException);
        }
    }

    public static void main(String[] args) {
        // startup
        Storage storage = new Storage(SAVE_FILE_LOCATION);
        Ui ui = new Ui();
        taskList = storage.restore();

        // run
        ui.showWelcomeMessage();

        // chatbot
        while (continueChat) {
            // get user's input
            String userInput = ui.getUserInput();

            try {
                int taskIndex;
                String taskName;

                ParsedUserInput parsedInput = UserInputParser.parse(userInput);
                switch (parsedInput.getCommand()) {
                case LIST:
                    ui.listTasksInTaskList(taskList);
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
                    ui.printUnknownCommandErrorMessage();
                    break;
                }
            } catch (InvalidUserInputException | TaskListException exception) {
                System.out.println(exception.getMessage());
            }
        }

        // print exit message
        ui.showGoodbyeMessage();

        // save and cleanup
        try {
            storage.save(taskList);
        } catch (StorageException storageException) {
            System.out.println(storageException.getMessage());
        }
    }

}