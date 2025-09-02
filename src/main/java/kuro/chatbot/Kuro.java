package kuro.chatbot;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;


public class Kuro {
    final static String welcomeMsg = """
            ____________________________________________________________
             Konnichiwa! I'm Kuro
             What can I do for you?
            ____________________________________________________________
            """;
    final static String goodbyeMsg = """
            ____________________________________________________________
             Sayonara! Hope to see you again soon!
            ____________________________________________________________
            """;

    static List<Task> taskList = new ArrayList<>();

    //class for the task that user typed
    public static class Task {
        protected String command;
        protected boolean isCompleted;

        public Task(String command) {
            this.command = command;
            this.isCompleted = false;
        }

        public String getStatus() {
            return (isCompleted ? "X" : " ");
        }

        public void setStatus(boolean status) {
            this.isCompleted = status;
        }

        @Override
        public String toString() {
            return "[" + (isCompleted ? "X" : " ") + "] " + this.command;
        }
    }

    public static class Todo extends Task {
        public Todo(String description) {
            super(description);
        }

        @Override
        public String toString() {
            return "[T]" + super.toString();
        }
    }

    public static class Deadline extends Task {
        protected String by;

        public Deadline(String description, String by) {
            super(description);
            this.by = by;
        }

        @Override
        public String toString() {
            return "[D]" + super.toString() + " (by: " + by + ")";
        }
    }

    public static class Event extends Task {
        protected String start;
        protected String end;

        public Event(String description, String start, String end) {
            super(description);
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return "[E]" + super.toString() + " (From: " + start + " to: " + end + ")";
        }
    }

    public static class TaskParser {
        public static Task parse(String fullCommand) throws kuroException {
            String command = fullCommand.split(" ")[0].toLowerCase();

            return switch (command) {
                case "todo" -> parseTodo(fullCommand);
                case "deadline" -> parseDeadline(fullCommand);
                case "event" -> parseEvent(fullCommand);
                case "mark", "unmark", "delete" -> parseMarkUnmarkDelete(fullCommand);
                case "list", "bye" -> new Task(command); //misc task
                default -> throw new kuroException("Sumimasen, specified command is not a registered command");
            };
        }

        private static Task parseTodo(String fullCommand) throws kuroException {
            if (fullCommand.length() > 4) {
                return new Todo(fullCommand.substring(fullCommand.indexOf(" ") + 1));
            } else {
                throw new kuroException("Sumimasen, please specify the task description.");
            }
        }

        private static Task parseDeadline(String fullCommand) throws kuroException {
            if (!fullCommand.contains("/by")) {
                throw new kuroException("Sumimasen, invalid command or format. Please try again.");
            }

            String description = fullCommand.substring(9, fullCommand.indexOf("/by")).trim();
            String by = fullCommand.substring(fullCommand.indexOf("/by") + 4).trim();
            return new Deadline(description, by);
        }

        private static Task parseEvent(String fullCommand) throws kuroException {
            if (!fullCommand.contains("/from")
                    || !fullCommand.contains("/to")
                    || fullCommand.indexOf("/to") < fullCommand.indexOf("/from")) {
                throw new kuroException("Sumimasen, invalid command or format. Please try again.");
            }
            String description = fullCommand.substring(6, fullCommand.indexOf("/from")).trim();
            String start = fullCommand.substring(fullCommand.indexOf("/from") + 6, fullCommand.indexOf("/to")).trim();
            String end = fullCommand.substring(fullCommand.indexOf("/to") + 4).trim();
            return new Event(description, start, end);
        }

        private static Task parseMarkUnmarkDelete(String fullCommand) throws kuroException {
            try {
                int index = Integer.parseInt(fullCommand.split(" ")[1]) - 1;
                if (index > taskList.size() - 1) {
                    throw new Exception();
                }
                return new Task(fullCommand.split(" ")[0]);
            } catch (Exception e) {
                throw new kuroException("Sumimasen, invalid command or format. Please try again.");
            }
        }
    }

    public static class kuroException extends Exception {
        kuroException(String message) {
            super(message);
        }

        @Override
        public String toString() {
            return "Error while interacting with Kuro: " + getMessage();
        }
    }

    public static void main(String[] args) {
        boolean isOperating = true;

        try {
            File taskDb = new File("./data/kuro.txt");
            //Scan kuro.txt and initialize taskList
            Scanner dbScanner = new Scanner(taskDb);
            while (dbScanner.hasNextLine()) {
                String data = dbScanner.nextLine();
                //todo 
            }
            dbScanner.close();
        } catch (FileNotFoundException e) {
            File newDb = new File("./data/kuro.txt");
            try {
                newDb.createNewFile();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        System.out.println(welcomeMsg);

        //continue wait for input until user type bye
        Scanner scannerObj = new Scanner(System.in);

        while (isOperating) {
            String fullCommand = scannerObj.nextLine().trim();
            int index;
            Task newTask;

            try {
                newTask = TaskParser.parse(fullCommand);
            } catch (kuroException e) {
                System.out.println(e.toString());
                continue;
            }


            switch (newTask.command) {
            case "bye":
                isOperating = false;
                break;
            case "mark":
                index = Integer.parseInt(fullCommand.split(" ")[1]) - 1;
                taskList.get(index).setStatus(true);
                System.out.printf("""
                        ____________________________________________________________
                        Sugoi, I have marked this task as done:
                        %s
                        ____________________________________________________________
                        %n""", taskList.get(index).toString());
                break;
            case "unmark":
                index = Integer.parseInt(fullCommand.split(" ")[1]) - 1;
                taskList.get(index).setStatus(false);
                System.out.printf("""
                        ____________________________________________________________
                        Hai, I have marked this task as not done yet:
                        %s
                        ____________________________________________________________
                        %n""", taskList.get(index).toString());
                break;
            case "delete":
                index = Integer.parseInt(fullCommand.split(" ")[1]) - 1;
                String taskRemoved = taskList.get(index).toString();
                taskList.remove(index);
                System.out.printf("""
                        ____________________________________________________________
                        Hai, I have removed this task:
                        %s
                        Now, you have %d tasks in the list.
                        ____________________________________________________________
                        %n""", taskRemoved, taskList.size());
                break;
            case "list":
                StringBuilder listString = new StringBuilder();
                for (int i = 0; i < taskList.size(); i++) {
                    listString.append("\n")
                            .append(i + 1)
                            .append(". ")
                            .append(taskList.get(i).toString());
                }
                System.out.printf("""
                        ____________________________________________________________
                        Douzo,Here are the task in your list:
                        %s
                        ____________________________________________________________
                        %n""", listString);
                break;
            default:
                if (newTask.command.isEmpty()) {
                    break;
                }
                taskList.add(newTask);
                System.out.printf("""
                        ____________________________________________________________
                        Wakarimashita, I have added this task:
                        %s
                        Now, you have %d tasks in the list.
                        ____________________________________________________________
                        %n""", newTask.toString(), taskList.size());
            }
        }
        scannerObj.close();
        System.out.println(goodbyeMsg);
    }
}
