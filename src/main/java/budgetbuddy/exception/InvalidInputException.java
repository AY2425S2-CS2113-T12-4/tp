package budgetbuddy.exception;

/**
 * Exception thrown when user input is invalid.
 */
public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }

    public void print() {
        System.out.println("Invalid input format: " + getMessage());
    }

}

