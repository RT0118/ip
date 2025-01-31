package fauna.storage;

import fauna.exceptions.StorageException;
import fauna.task.Task;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final String saveFileLocation;

    public Storage(String saveFileLocation) {
        this.saveFileLocation = saveFileLocation;
    }

    public void save(ArrayList<Task> taskList) throws StorageException {
        File saveFile = new File(this.saveFileLocation);

        try (FileWriter saveFileWriter = new FileWriter(saveFile)) {
            if (!saveFile.exists() && !saveFile.createNewFile()) {
                throw new StorageException(
                        String.format("the save file cant be created at %s", saveFile.getAbsoluteFile()));
            }

            for(Task task : taskList) {
                String serializedTaskString = task.serialize();
                saveFileWriter.write(serializedTaskString);
            }
        } catch (IOException | SecurityException exception) {
            throw new StorageException(
                    String.format("the save file in %s can't be written to!", saveFile.getAbsoluteFile()));
        }
    }

    public ArrayList<Task> restore() throws StorageException {
        ArrayList<Task> taskList = new ArrayList<Task>();
        File saveFile = new File(this.saveFileLocation);

        if (!saveFile.exists()) {
            return taskList;
        }

        try (Scanner saveFileScanner = new Scanner(saveFile)) {
            while (saveFileScanner.hasNextLine()) {
                String line = saveFileScanner.nextLine();
                Task task = Task.fromSerializedString(line);
                taskList.add(task);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            throw new StorageException(
                    String.format("the save file in %s can't be read from!", saveFile.getAbsoluteFile()));
        }

        return taskList;

    }



}
