package kuro.constants;

/**
 * A class that has all the constant messages.
 */
public class Messages {
    public static final String WELCOME_MESSAGE = """
             Konnichiwa! I'm Kuro
             What can I do for you?""";
    public static final String GOODBYE_MESSAGE = """
             Sayonara! Hope to see you again soon!""";
    public static final String MARKING_MESSAGE = """
             Sugoi, I have marked this task as done:
             %s""";
    public static final String UNMARKING_MESSAGE = """
             Hai, I have marked this task as not done yet:
             %s""";
    public static final String ADDING_MESSAGE = """
             Wakarimashita, I have added this task:
             %s
             Now, you have %d tasks in the list.""";
    public static final String REMOVE_MESSAGE = """
             Hai, I have removed this task:
             %s
             Now, you have %d tasks in the list.""";
    public static final String SHOW_LIST_MESSAGE = """
             Douzo,Here are the task in your list:
             %s""";
    public static final String SHOW_FILTERED_LIST_MESSAGE = """
             Douzo,Here are the matching tasks in your list:
             %s""";
    public static final String ERROR_MESSAGE = """
             Error while interacting with Kuro: %s""";
}
