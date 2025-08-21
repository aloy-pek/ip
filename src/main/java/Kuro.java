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
    
    public static void main(String[] args) {
        boolean isOperating = true;
        
        System.out.println(welcomeMsg);
        
        while (isOperating) {
            Scanner scannerObj = new Scanner(System.in);
            String command = scannerObj.nextLine();
            
            if  (command.equalsIgnoreCase("bye")) {
                isOperating = false;
            } else {
                System.out.printf("""
                        ____________________________________________________________
                        %s
                        ____________________________________________________________
                        %n""", command);
            }
        }
        System.out.println(goodbyeMsg);
    }
}
