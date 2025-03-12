package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExpenseTest {

    @Test
    public void testValidExpenseCreation() {
        double amount = 100.50;
        String description = "Groceries";

        Expense expense = new Expense(amount, description);

        assertEquals(amount, expense.amount, "Amount should be 100.50");
        assertEquals(description, expense.description, "Description should be 'Groceries'");
    }

    @Test
    public void addExpense_negativeAmount_exceptionThrown() {
        double amount = -50.00;
        String description = "Transport";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Expense(amount, description);
        });
        assertEquals("Amount cannot be negative.", exception.getMessage());
    }

    @Test
    public void addExpense_nullDescription_exceptionThrown() {
        double amount = 100.00;
        String description = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Expense(amount, description);
        });
        assertEquals("Description cannot be empty.", exception.getMessage());
    }

    @Test
    public void testToString() {
        // Arrange
        double amount = 250.00;
        String description = "Dinner";
        Expense expense = new Expense(amount, description);

        // Act
        String result = expense.toString();

        // Assert
        DateTimeFormatter expectedFormatter = DateTimeFormatter.ofPattern("E, MMM dd 'at' HH:mm");
        String expectedDateTime = LocalDateTime.now().format(expectedFormatter);
        String expectedString = String.format("$%,.2f", amount) + ", spent on " + expectedDateTime;

        assertTrue(result.startsWith("$250.00, spent on"),
                "The string should start with formatted amount and 'spent on'");
        assertTrue(result.contains(expectedDateTime), "The string should contain the formatted date and time");
    }
}
