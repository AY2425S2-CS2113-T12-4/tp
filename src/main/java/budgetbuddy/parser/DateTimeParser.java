package budgetbuddy.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for handling date and time operations.
 * <p>
 * This class provides helper methods for parsing and validating date and time values.
 * If a user-supplied date and time string does not conform to the expected format,
 * then the system's current date and time is used instead.
 * </p>
 */
public class DateTimeParser {

    // Define the formatter for both parsing and formatting.
    public static final DateTimeFormatter DATETIME_FORMAT =
            DateTimeFormatter.ofPattern("E, MMM dd 'at' HH:mm");

    /**
     * Attempts to parse the provided date and time string using the predefined formatter.
     * <p>
     * If the string is successfully parsed, the resulting LocalDateTime is returned.
     * If the string cannot be parsed (i.e. it is in an incorrect format), the current system
     * date and time is returned as a default value.
     * </p>
     *
     * @param inputDateTimeStr The user provided date and time string.
     * @return The parsed LocalDateTime if the format is correct; otherwise, the system's current date and time.
     */
    public static LocalDateTime parseOrDefault(String inputDateTimeStr) {
        // Debug print: show the user provided string.
        boolean isCorrectDateTimeFormat = false;
        LocalDateTime dateTimeParsed = null;

        try {
            // Attempt to parse the string using the predefined formatter.
            LocalDateTime parsedDateTime = LocalDateTime.parse(inputDateTimeStr, DATETIME_FORMAT);
            isCorrectDateTimeFormat = true;
            dateTimeParsed = parsedDateTime;
        } catch (DateTimeParseException e) {
            // If parsing fails, print a debug message and fallback to system time.
            System.out.println("Wrong dateTime format used. Will use system current dateTime instead.");
            LocalDateTime systemNow = LocalDateTime.now();
            dateTimeParsed = systemNow;
            // Assert that the system time is not null.
            assert systemNow != null : "System current dateTime should never be null.";
        }
        return dateTimeParsed;
    }
}
