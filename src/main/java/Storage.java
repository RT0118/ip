import exceptions.StorageException;

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
        try (FileWriter saveFileWriter = new FileWriter(this.saveFileLocation)) {
            for(Task task : taskList) {
                String serializedTaskString = task.serialize();
                saveFileWriter.write(serializedTaskString);
            }
        } catch (IOException ioException) {
            throw new StorageException(
                    String.format("the save file in %s can't be written to!", this.saveFileLocation));
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
                    String.format("the save file in %s can't be read from!", this.saveFileLocation));
        }

        return taskList;

    }



}
