package budgetbuddy.exception;

import budgetbuddy.ui.Ui;

/**
 * Exception thrown when user input is invalid.
 */
public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }

    public void print() {
        Ui.printSeparator();
        System.out.println("Invalid input format: " + getMessage());
        Ui.printSeparator();
    }

}

