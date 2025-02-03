package fauna;

import java.time.LocalDateTime;

import fauna.exceptions.InvalidUserInputException;
import fauna.exceptions.StorageException;
import fauna.exceptions.TaskListException;
import fauna.parser.ParsedUserInput;
import fauna.parser.UserInputParser;
import fauna.storage.Storage;
import fauna.task.DeadlineTask;
import fauna.task.EventTask;
import fauna.task.Task;
import fauna.task.TaskList;
import fauna.task.ToDoTask;
import fauna.ui.Ui;

/**
 * Fauna contains the main logic for the Fauna chatbot
 */
public class Fauna {
    private final Ui ui;
    private final Storage storage;
    private TaskList taskList;

    /**
     * Create a new instance of Fauna
     * @param saveFileLocation txt save file to load tasks from
     */
    public Fauna(String saveFileLocation) {
        this.ui = new Ui();
        this.storage = new Storage(saveFileLocation);
        this.taskList = this.storage.restore();
    }

    private void addTodoToTaskList(String taskName) {
        Task task = new ToDoTask(taskName);
        taskList = taskList.addTask(task);
        ui.printAddTaskPrompt(task, taskList.size());
    }

    private void addDeadlineToTaskList(String taskName, LocalDateTime datetime) {
        Task task = new DeadlineTask(taskName, datetime);
        taskList = taskList.addTask(task);
        ui.printAddTaskPrompt(task, taskList.size());
    }

    private void addEventToTaskList(String taskName, LocalDateTime from, LocalDateTime to) {
        Task task = new EventTask(taskName, from, to);
        taskList = taskList.addTask(task);
        ui.printAddTaskPrompt(task, taskList.size());
    }

    private void markTaskAsDone(int taskIndex) {
        try {
            taskList = taskList.markTaskAsDone(taskIndex);
            ui.printMarkTaskAsDone(taskList.getTask(taskIndex));
        } catch (TaskListException taskListException) {
            ui.printErrorMessage(taskListException);
        }
    }

    private void markTaskAsUndone(int taskIndex) {
        try {
            taskList = taskList.markTaskAsUndone(taskIndex);
            ui.printMarkTaskAsUndone(taskList.getTask(taskIndex));
        } catch (TaskListException taskListException) {
            ui.printErrorMessage(taskListException);
        }
    }

    private void deleteTask(int taskIndex) {
        try {
            Task deletedTask = this.taskList.getTask(taskIndex);
            taskList = taskList.removeTask(taskIndex);
            ui.printDeleteTask(deletedTask, taskList.size());
        } catch (TaskListException taskListException) {
            ui.printErrorMessage(taskListException);
        }
    }

    private void findTask(String searchTerm) {
        String searchResults = taskList.findTasksByKeyword(searchTerm);
        ui.printFindTask(searchResults, searchTerm);
    }

    public void run() {
        // greet the user
        ui.showWelcomeMessage();

        boolean continueChat = true;
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
                case FIND:
                    taskName = parsedInput.getTaskName();
                    findTask(taskName);
                    break;
                default:
                    ui.printUnknownCommandErrorMessage();
                    ui.printAllAvailableCommands();
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

    public static void main(String[] args) {
        new Fauna("./fauna.txt").run();
    }
}
