package fauna.ui;

import java.time.LocalDateTime;

import fauna.exceptions.InvalidUserInputException;
import fauna.exceptions.StorageException;
import fauna.exceptions.TaskListException;
import fauna.parser.FaunaCommand;
import fauna.parser.ParsedUserInput;
import fauna.parser.UserInputParser;
import fauna.storage.Storage;
import fauna.task.DeadlineTask;
import fauna.task.EventTask;
import fauna.task.Task;
import fauna.task.TaskList;
import fauna.task.ToDoTask;

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

    private void cliModeShowWelcomeMessage() {
        System.out.println(ui.displayLogo());
        System.out.println(ui.showWelcomeMessage());

    }

    private String addTodoToTaskList(String taskName) {
        Task task = new ToDoTask(taskName);
        taskList = taskList.addTask(task);
        return ui.printAddTaskPrompt(task, taskList.size());
    }

    private String addDeadlineToTaskList(String taskName, LocalDateTime datetime) {
        Task task = new DeadlineTask(taskName, datetime);
        taskList = taskList.addTask(task);
        return ui.printAddTaskPrompt(task, taskList.size());
    }

    private String addEventToTaskList(String taskName, LocalDateTime from, LocalDateTime to) {
        Task task = new EventTask(taskName, from, to);
        taskList = taskList.addTask(task);
        return ui.printAddTaskPrompt(task, taskList.size());
    }

    private String markTaskAsDone(int taskIndex) {
        try {
            taskList = taskList.markTaskAsDone(taskIndex);
            return ui.printMarkTaskAsDone(taskList.getTask(taskIndex));
        } catch (TaskListException taskListException) {
            return ui.printErrorMessage(taskListException);
        }
    }

    private String markTaskAsUndone(int taskIndex) {
        try {
            taskList = taskList.markTaskAsUndone(taskIndex);
            return ui.printMarkTaskAsUndone(taskList.getTask(taskIndex));
        } catch (TaskListException taskListException) {
            return ui.printErrorMessage(taskListException);
        }
    }

    private String deleteTask(int taskIndex) {
        try {
            Task deletedTask = this.taskList.getTask(taskIndex);
            taskList = taskList.removeTask(taskIndex);
            return ui.printDeleteTask(deletedTask, taskList.size());
        } catch (TaskListException taskListException) {
            return ui.printErrorMessage(taskListException);
        }
    }

    private String findTask(String searchTerm) {
        String searchResults = taskList.findTasksByKeyword(searchTerm);
        return ui.printFindTask(searchResults, searchTerm);
    }

    private String handleExecution(ParsedUserInput parsedInput) {
        String response = "";
        try {
            int taskIndex;
            String taskName;

            switch (parsedInput.getCommand()) {
            case BYE:
                response = ui.showGoodbyeMessage();
                break;
            case LIST:
                response = ui.listTasksInTaskList(taskList);
                break;
            case MARK:
                taskIndex = parsedInput.getTaskNumber();
                response = markTaskAsDone(taskIndex);
                break;
            case UNMARK:
                taskIndex = parsedInput.getTaskNumber();
                response = markTaskAsUndone(taskIndex);
                break;
            case TODO:
                taskName = parsedInput.getTaskName();
                response = addTodoToTaskList(taskName);
                break;
            case DEADLINE:
                taskName = parsedInput.getTaskName();
                LocalDateTime deadline = parsedInput.getTaskByDatetime();
                response = addDeadlineToTaskList(taskName, deadline);
                break;
            case EVENT:
                taskName = parsedInput.getTaskName();
                LocalDateTime from = parsedInput.getTaskFromDatetime();
                LocalDateTime to = parsedInput.getTaskToDatetime();
                response = addEventToTaskList(taskName, from, to);
                break;
            case DELETE:
                taskIndex = parsedInput.getTaskNumber();
                response = deleteTask(taskIndex);
                break;
            case FIND:
                taskName = parsedInput.getTaskName();
                response = findTask(taskName);
                break;
            default:
                response = ui.printUnknownCommandErrorMessage() + "\n";
                response += ui.printAllAvailableCommands();
                break;
            }
        } catch (InvalidUserInputException | TaskListException exception) {
            response = exception.getMessage();
        }

        return response;
    }

    /**
     * Save the tasks before exiting
     */
    public void saveAndCleanup() {
        // save and cleanup
        try {
            storage.save(taskList);
        } catch (StorageException storageException) {
            System.out.println(storageException.getMessage());
        }
    }

    /**
     * Get the welcome message as a String object (for GUI mode)
     * @return welcome message string
     */
    public String getWelcomeMessageForGui() {
        return ui.showWelcomeMessage();
    }

    /**
     * Processes the raw user input, runs it through Fauna and get Fauna's response
     * @param userInput unprocessed user input
     * @return Fauna's response
     */
    public String getResponse(String userInput) {
        try {
            ParsedUserInput parsedInput = UserInputParser.parse(userInput);
            return handleExecution(parsedInput);
        } catch (InvalidUserInputException invalidUserInputException) {
            return invalidUserInputException.getMessage();
        }
    }

    /**
     * Begin the execution of Fauna chatbot (in console mode)
     */
    public void run() {
        cliModeShowWelcomeMessage();

        boolean continueChat = true;
        while (continueChat) {
            String response = "";
            try {
                String userInput = ui.getUserInput();
                ParsedUserInput parsedInput = UserInputParser.parse(userInput);
                if (parsedInput.getCommand() == FaunaCommand.BYE) {
                    continueChat = false;
                }
                response = handleExecution(parsedInput);
            } catch (InvalidUserInputException invalidUserInputException) {
                response = invalidUserInputException.getMessage();
            } finally {
                System.out.println(response);
            }
        }

        // exit from loop, do saving and cleanup tasks
        saveAndCleanup();
    }

    public static void main(String[] args) {
        new Fauna("./fauna.txt").run();
    }
}
