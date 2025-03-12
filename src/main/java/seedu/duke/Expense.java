package seedu.duke;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Expense {
    protected static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("E, MMM dd 'at' HH:mm");
    protected String description;
    protected double amount;
    protected LocalDateTime dateTime;


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
    }

    @Override
    public String toString() {
        String formattedDateTime = dateTime.format(DATETIME_FORMAT);
        String formattedAmount = String.format("$%,.2f", amount);
        return formattedAmount + ", spent on " + formattedDateTime;
    }
}
