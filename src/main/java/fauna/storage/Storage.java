package fauna.storage;

import fauna.task.Task;
import fauna.task.TaskList;
import fauna.exceptions.StorageException;

import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Storage {
    private final String saveFileLocation;

    public Storage(String saveFileLocation) {
        this.saveFileLocation = saveFileLocation;
    }

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
