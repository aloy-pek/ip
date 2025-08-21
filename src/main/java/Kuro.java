import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    public static void main(String[] args) {
        boolean isOperating = true;

        System.out.println(welcomeMsg);

        //continue wait for input until user type bye
        while (isOperating) {
            Scanner scannerObj = new Scanner(System.in);
            String command = scannerObj.nextLine();
            int index = 0;
            
            //pre-processing of command
            if (command.contains("unmark")) {
                index = Integer.parseInt(command.substring(7)) - 1;
                command = "unmark";
                if (index > taskList.size() - 1) {
                    System.out.println("Sumimasen, Please check your index again!");
                    continue;
                }
            } else if (command.contains("mark")) {
                index = Integer.parseInt(command.substring(5)) - 1;
                command = "mark";
                if (index > taskList.size() - 1) {
                    System.out.println("Sumimasen, Please check your index again!");
                    continue;
                }
            }
            
            Task newTask = new Task(command);

            switch (newTask.command.toLowerCase()) {
            case "bye":
                isOperating = false;
                break;
            case "mark":
                taskList.get(index).setStatus(true);
                System.out.printf("""
                        ____________________________________________________________
                        Sugoi, I have marked this task as done:
                        %s
                        ____________________________________________________________
                        %n""", taskList.get(index).toString());
                break;
            case "unmark":
                taskList.get(index).setStatus(false);
                System.out.printf("""
                        ____________________________________________________________
                        Hai, I have marked this task as not done yet:
                        %s
                        ____________________________________________________________
                        %n""", taskList.get(index).toString());
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
                        added: %s
                        ____________________________________________________________
                        %n""", newTask.command);
            }
        }
        System.out.println(goodbyeMsg);
    }
}
