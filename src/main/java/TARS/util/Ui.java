package TARS.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Ui {
    private static Ui instance = null; // Field to store Singleton instance of Ui Class
    private final Scanner in;
    private final PrintStream out;

    private static final String LINE_PREFIX = "\t";
    private static final String LINE_DIVIDER = "\t____________________________________________________________";
    private static final String HELLO_MESSAGE = """
              _____  _    ____  ____
            \t |_   _|/ \\  |  _ \\/ ___|
            \t   | | / _ \\ | |_) \\___ \\
            \t   | |/ ___ \\|  _ < ___) |
            \t   |_/_/   \\_\\_| \\_\\____/
            \tHello, I'm TARS, your friendly chatbot assistant.
            \tHow can I help you today?""";
    private static final String GOODBYE_MESSAGE = "Goodnight Captain. Sleep well.";



    private Ui(){
         this(System.in, System.out);
    }
    private Ui(InputStream in, PrintStream out){
        this.in = new Scanner(in);
        this.out = new PrintStream(out);
    }

    /**
     * Static method that controls access to singleton instance of Ui
     * @return instance of Ui class
     */
    public static Ui getInstance(){
        if (instance == null) {
            instance = new Ui();
        }
        return instance;
    }

    /**
     * General Printing function, prints output text with prefix buffer.
     * @param message text to be output in the console
     */
    public void printResponseMessage(String message) {
        out.println(LINE_PREFIX + message);
    }

    public void printLineDivider() {
        out.println(LINE_DIVIDER);
    }

    /**
     * Prints the welcome message upon startup.
     */
    public void printHelloMessage() {
        printResponseMessage(HELLO_MESSAGE);
    }

    /**
     * Prints the goodbye message before exiting.
     */
    public void printGoodbyeMessage() {
        printResponseMessage(GOODBYE_MESSAGE);
    }

    /**
     * reads user input entered by user. ignores whitespace before/after command
     * @return command (full line) entered by the user
     */
    public String readScannerInput() {
        String fullInputLine = in.nextLine();

        return fullInputLine.trim();
    }

}
