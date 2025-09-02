package kuro.storage;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import kuro.exceptions.KuroException;
import kuro.tasks.Deadline;
import kuro.tasks.Event;
import kuro.tasks.Task;
import kuro.tasks.Todo;

public class Storage {
    private final Path filepath;

    public Storage(String filepath) {
        this.filepath = Paths.get(filepath);
    }

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
                    tasks.add(new Deadline(description, by, isDone));
                    break;
                case "E":
                    String start = parts[3];
                    String end = parts[4];
                    tasks.add(new Event(description, start, end, isDone));
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
    
//    try {
//            File taskDb = new File("./data/kuro.txt");
//            //Scan kuro.txt and initialize taskList
//            Scanner dbScanner = new Scanner(taskDb);
//            while (dbScanner.hasNextLine()) {
//                String data = dbScanner.nextLine();
//                //todo 
//            }
//            dbScanner.close();
//        } catch (FileNotFoundException e) {
//            File newDb = new File("./data/kuro.txt");
//            try {
//                newDb.createNewFile();
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }
//        }
}
