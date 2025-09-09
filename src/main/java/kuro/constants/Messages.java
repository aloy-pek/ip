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
    public static final String DUPLICATE_ERROR = "Sumimasen, this current has already exists.";
    public static final String ERROR_LOADING_FILE_MESSAGE = "Error Loading File";
    public static final String ERROR_SAVING_FILE_MESSAGE = "Error saving data";
    public static final String ERROR_MISSING_COMMAND_MESSAGE = "Please enter your command";
    public static final String INVALID_COMMAND = "Sumimasen, invalid command or format. Please try again.";
    public static final String UNREGISTERED_COMMAND = "Sumimasen, specified command is not a registered command";
    public static final String INVALID_DATE = "Invalid date format, Please use yyyy-MM-dd HH:mm";
    public static final String MISSING_TASK_DESCRIPTION = "Sumimasen, please specify the task description.";
    public static final String NONMATCHING_FILTER = "No task matched the keyword";
    public static final String OUT_OF_BOUND_ERROR = "Index out of bounds";
}
