package budgetbuddy.ui;

//import wag.tasks.Task;
import java.util.Scanner;

public class Ui {
    private static final String SEPARATOR = "___________________________________________________";
    private Scanner scanner;

    public void printWelcomeMessage(){
        System.out.println(" _____________________ ");
        System.out.println("|                     |");
        System.out.println("|  $$$$       $$$$    |");
        System.out.println("| $    $     $    $   |");
        System.out.println("| $    $     $    $   |");
        System.out.println("|  $$$$       $$$$    |");
        System.out.println("|_____________________|");
        printSeparator();
        System.out.println("Hello! I'm your Budget Buddy");
        System.out.println("What can I do for you?");
        System.out.println("Input 'help' if you wants to know what I can do!!");
        printSeparator();
    }

    public void printSeparator() {
        System.out.println(SEPARATOR);
    }
}
