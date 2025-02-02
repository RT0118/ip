package fauna.storage;

import fauna.task.Task;
import fauna.task.TaskList;
import fauna.exceptions.StorageException;

import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Storage class to save and restore tasks saved by the user
 * @author RT0118
 */
public class Storage {
    /**
     * The path pointing to the save file
     */
    private final String saveFileLocation;

    public Storage(String saveFileLocation) {
        this.saveFileLocation = saveFileLocation;
    }

    /**
     * <p>saves the tasks created by the user in a txt file
     * with the path provided on creation
     * </p>
     * @param taskList TaskList that contains stored tasks
     * @throws StorageException the save file cannot be created/written to
     */
    public void save(TaskList taskList) throws StorageException {
        File saveFile = new File(this.saveFileLocation);

        try (FileWriter saveFileWriter = new FileWriter(saveFile)) {
            if (!saveFile.exists() && !saveFile.createNewFile()) {
                throw new StorageException(
                        String.format("the save file cant be created at %s", saveFile.getAbsoluteFile()));
            }

            for (Task task : taskList.getTasksAsList()) {
                String serializedTaskString = task.serialize();
                saveFileWriter.write(serializedTaskString);
            }
        } catch (IOException | SecurityException exception) {
            throw new StorageException(
                    String.format("the save file in %s can't be written to!", saveFile.getAbsoluteFile()));
        }
    }

    /**
     * <p>Restores the tasks saved from the save file
     * </p>
     * @return TaskList object containing all tasks saved
     * @throws StorageException the save file cannot be read from
     */
    public TaskList restore() throws StorageException {
        TaskList taskList = new TaskList();
        File saveFile = new File(this.saveFileLocation);

        if (!saveFile.exists()) {
            return taskList;
        }

        try (Scanner saveFileScanner = new Scanner(saveFile)) {
            while (saveFileScanner.hasNextLine()) {
                String line = saveFileScanner.nextLine();
                Task task = Task.fromSerializedString(line);
                taskList = taskList.addTask(task);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            throw new StorageException(
                    String.format("the save file in %s can't be read from!", saveFile.getAbsoluteFile()));
        }

        return taskList;

    }



}
