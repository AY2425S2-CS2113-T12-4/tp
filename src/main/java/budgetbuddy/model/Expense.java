package budgetbuddy.model;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Represents an expense with an amount, description, and timestamp.
 * <p>
 * This class encapsulates all the necessary information related to an expense,
 * including the amount spent, a textual description, and the date and time the expense was recorded.
 * It offers multiple constructors, including one that accepts a date and time as a string.
 * If the string is in the correct format, that value is used; otherwise, the current system date and time is used.
 * </p>
 */
public class Expense {
    // Formatter to display date and time.
    protected static final DateTimeFormatter DATETIME_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy 'at' HH:mm");

    // The date and time when the expense was recorded.
    public LocalDateTime dateTime;
    // A textual description of the expense.
    protected String description;
    // The monetary amount of the expense.
    protected double amount;

    /**
     * Creates a new Expense with a specified amount and description.
     * <p>
     * The timestamp is automatically set to the current date and time.
     * </p>
     *
     * @param amount      The amount spent. Must be non-negative.
     * @param description The description of the expense. Cannot be null.
     * @throws IllegalArgumentException If the description is null or the amount is negative.
     */
    public Expense(double amount, String description) {
        // Validate the description.
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }
        // Validate the amount.
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        // Initialize instance variables.
        this.description = description;
        this.amount = amount;
        // Set dateTime to the system's current date and time.
        this.dateTime = LocalDateTime.now();
        assert dateTime != null : "DateTime cannot be null.";
    }

    /**
     * Creates a new Expense with a specified amount, description, and user-provided dateTime.
     * <p>
     * The provided dateTime is accepted only if it matches the expected format. If the format is incorrect,
     * the system's current date and time is used instead.
     * </p>
     *
     * @param amount         The amount spent. Must be non-negative.
     * @param description    The description of the expense. Cannot be null.
     * @param dateTimeString The user provided date and time as a string.
     * @throws IllegalArgumentException If the description is null or the amount is negative.
     */
    public Expense(double amount, String description, String dateTimeString) {
        // Validate description.
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }
        // Validate amount.
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        // Initialize fields.
        this.description = description;
        this.amount = amount;

        if (dateTimeString == "") {
            this.dateTime = LocalDateTime.now();
        } else {
            // Use the DateTimeUtil to parse the provided string.
            this.dateTime = budgetbuddy.parser.DateTimeParser.parseOrDefault(dateTimeString, false);
            //we want to print error messages here, if applicable.Hence, noErrorPrint is false
        }
    }

    /**
     * This constructor is used when loading from .txt so that we can bypass all the error messages
     * @param amount The amount spent. Must be non-negative.
     * @param description The description of the expense. Cannot be null.
     * @param dateTimeString The user provided LocalDateTime.
     * @param noErrorPrint Whether to print error messages or not. Connected to DateTimeParser
     *                     where messages is toggled on/off.
     */

    public Expense(double amount, String description, String dateTimeString, Boolean noErrorPrint) {
        // Validate description.
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }
        // Validate amount.
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        // Initialize fields.
        this.description = description;
        this.amount = amount;
        // Use the DateTimeUtil to parse the provided string.
        this.dateTime = budgetbuddy.parser.DateTimeParser.parseOrDefault(dateTimeString, noErrorPrint);
        //when noErrorPrint is true then we don't print error messages
    }

    /**
     * Creates a new Expense with a specified amount, description, and a LocalDateTime value.
     * <p>
     * This constructor is useful when the dateTime value has already been parsed and validated.
     * </p>
     *
     * @param amount      The amount spent. Must be non-negative.
     * @param description The description of the expense. Cannot be null.
     * @param dateTime    The user provided LocalDateTime.
     * @throws IllegalArgumentException If the description is null, the amount is negative, or the dateTime is null.
     */
    public Expense(double amount, String description, LocalDateTime dateTime) {
        // Validate description.
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }
        // Validate amount.
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        // Validate dateTime.
        if (dateTime == null) {
            throw new IllegalArgumentException("DateTime cannot be null.");
        }
        // Initialize instance variables.
        this.description = description;
        this.amount = amount;
        this.dateTime = (LocalDateTime) dateTime;
    }

    /**
     * Returns a string representation of the expense, including the amount and timestamp.
     * <p>
     * The amount is formatted as currency, and the timestamp is formatted using a predefined pattern.
     * </p>
     *
     * @return A formatted string representing the expense.
     */
    @Override
    public String toString() {
        // Format the dateTime using the specified formatter.
        String formattedDateTime = dateTime.format(DATETIME_FORMAT);
        // Format the amount as currency.
        String formattedAmount = String.format("$%,.2f", amount);
        // Build and return the full string representation.
        return formattedAmount + " spent on " + description + " (" + formattedDateTime + ")";
    }

    /**
     * Edits the details of an existing expense.
     *
     * This method allows users to modify the amount, description, and date/time of an expense.
     * The amount must be a positive value, and if not provided, it remains unchanged.
     * The description and date/time are optional and only updated if provided.
     * If the date/time is in an incorrect format, the method uses the current date and time by default.
     *
     * @param amountStr A string representing the new amount of the expense. This value must be positive.
     *                  If the string is empty, the amount remains unchanged.
     * @param description A string representing the new description of the expense. If the string is empty,
     *                    the description remains unchanged.
     * @param dateTime A string representing the new date and time of the expense in a specific format
     *                 (MMM dd yyyy at HH:mm). If the string is empty or the format is incorrect,
     *                 the current date and time are used.
     *
     * @throws IllegalArgumentException If the provided amount is zero or negative.
     */
    public void editExpense(String amountStr, String description, String dateTime){
        if (!amountStr.isEmpty()) {
            double amount = Double.parseDouble(amountStr);
            if (amount <= 0)  {
                throw new IllegalArgumentException ("Amount cannot be zero or negative");
            }
            this.amount = amount;
        }
        if (!description.isEmpty()) {
            this.description = description;
        }
        if (!dateTime.isEmpty()) {
            this.dateTime = budgetbuddy.parser.DateTimeParser.parseOrDefault(dateTime, false);
        }
    }

    /**
     * Retrieves the monetary amount of the expense.
     *
     * @return The expense amount.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Retrieves the description of the expense.
     *
     * @return The expense description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * this return string type of date time to preserve format
     * @return date time in string
     */
    public String getDateTimeString() {
        return dateTime.format(DATETIME_FORMAT);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
