import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Kuro {
    final static String welcomeMsg = """
                ____________________________________________________________
                 Hello! I'm Kuro
                 What can I do for you?
                ____________________________________________________________
                """;
    final static String goodbyeMsg = """
                ____________________________________________________________
                 Sayonara! Hope to see you again soon!
                ____________________________________________________________
                """;
    
    static List<String> commandList = new ArrayList<String>();
    
    public static void main(String[] args) {
        boolean isOperating = true;
        
        System.out.println(welcomeMsg);
        
        while (isOperating) {
            Scanner scannerObj = new Scanner(System.in);
            String command = scannerObj.nextLine();

            switch (command.toLowerCase()) {
                case "bye":
                    isOperating = false;
                    break;
                case "list":
                    StringBuilder listString = new StringBuilder();
                    for (int i = 0; i < commandList.size(); i++){
                        listString.append("\n").append(i + 1).append(". ").append(commandList.get(i));
                    }
                    System.out.printf("""
                            ____________________________________________________________
                            %s
                            ____________________________________________________________
                            %n""", listString);
                    break;
                default:
                    if (command.isEmpty()) {
                        break;
                    }
                    commandList.add(command);
                    System.out.printf("""
                            ____________________________________________________________
                            added: %s
                            ____________________________________________________________
                            %n""", command);
            }
        }
        System.out.println(goodbyeMsg);
    }
}
