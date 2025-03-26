package seedu.duke;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an expense with an amount, description, and timestamp.
 */
public class Expense {
    protected static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("E, MMM dd 'at' HH:mm");
    protected String description;
    protected double amount;
    protected LocalDateTime dateTime;

    /**
     * Creates a new Expense with a specified amount and description.
     * The timestamp is automatically set to the current date and time.
     *
     * @param amount      The amount spent. Must be non-negative.
     * @param description The description of the expense. Cannot be null.
     * @throws IllegalArgumentException If the description is null or the amount is negative.
     */
    public Expense(double amount, String description) {

        if (description == null) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        this.description = description;
        this.amount = amount;
        dateTime = LocalDateTime.now();

        assert dateTime != null : "DateTime should be initialized properly.";
    }

    // New constructor for loading from storage
    public Expense(double amount, String description, LocalDateTime dateTime) {
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        if (dateTime == null) {
            throw new IllegalArgumentException("DateTime cannot be null.");
        }
        this.description = description;
        this.amount = amount;
        this.dateTime = dateTime;
    }

    /**
     * Returns a string representation of the expense, including the amount and timestamp.
     * The amount is formatted as currency, and the timestamp follows the predefined format.
     *
     * @return A formatted string representing the expense.
     */
    @Override
    public String toString() {
        String formattedDateTime = dateTime.format(DATETIME_FORMAT);
        String formattedAmount = String.format("$%,.2f", amount);
        return formattedAmount + " spent on " + description + " (" + formattedDateTime + ")";
    }

    public double getAmount() {
        return  amount;
    }

    public String getDescription() {
        return description;
    }
}
