package kuro.storage;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

import kuro.exceptions.KuroException;
import kuro.tasks.Deadline;
import kuro.tasks.Event;
import kuro.tasks.Task;
import kuro.tasks.Todo;
/**
 * A class that manage the storage of taskList.
 */
public class Storage {
    private final Path filepath;

    public Storage(String filepath) {
        this.filepath = Paths.get(filepath);
    }

    /**
     * Returns an ArrayList of Task that is created from loaded file.
     * Create file and filepath if it did not exist and return an empty ArrayList.
     * 
     * @return ArrayList<Task>.
     * @throws IOException If file or directory cannot be created.
     * @throws KuroException If Scanner cannot be created.
     */
    public ArrayList<Task> load() throws IOException, KuroException {
        ArrayList<Task> tasks = new ArrayList<Task>();

        if (!Files.exists(filepath)) {
            Files.createDirectories(filepath.getParent());
            Files.createFile(filepath);
            return tasks;
        }
        try (Scanner sc = new Scanner(filepath.toFile())) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(" \\| ");

                String type = parts[0];      // T/D/E
                boolean isDone = parts[1].equals("1");
                String description = parts[2];

                switch (type) {
                case "T":
                    tasks.add(new Todo(description, isDone));
                    break;
                case "D":
                    String by = parts[3];
                    try {
                        LocalDateTime dateTime = LocalDateTime.parse(by, DateTimeFormatter.ISO_DATE_TIME);
                        tasks.add(new Deadline(description, dateTime, isDone));
                    } catch (DateTimeParseException e) {
                        throw new KuroException("Invalid date format, Please use yyyy-MM-dd HH:mm");
                    }
                    break;
                case "E":
                    String start = parts[3];
                    String end = parts[4];
                    try {
                        LocalDateTime startDateTime = LocalDateTime.parse(start, DateTimeFormatter.ISO_DATE_TIME);
                        LocalDateTime endDateTime = LocalDateTime.parse(end, DateTimeFormatter.ISO_DATE_TIME);
                        tasks.add(new Event(description, startDateTime, endDateTime, isDone));
                    } catch (DateTimeParseException e) {
                        throw new KuroException("Invalid date format, Please use yyyy-MM-dd HH:mm");
                    }

                    break;
                default:
                    throw new KuroException("Unknown task type in file");
                }
            }
        } catch (FileNotFoundException e) {
            throw new KuroException("File not found: ");
        }

        return tasks;
    }

    /**
     * Saves current taskList into local txt file.
     *
     * @param tasks ArrayList of Task.
     * @throws IOException If the file cannot be modified.
     */
    public void save(ArrayList<Task> tasks) throws IOException {
        Files.createDirectories(filepath.getParent());
        try (BufferedWriter writer = Files.newBufferedWriter(filepath)) {
            for (Task task : tasks) {
                writer.write(task.toSaveFormat());
                writer.newLine();
            }
        }
        System.out.println(filepath.toString());
    }
}
