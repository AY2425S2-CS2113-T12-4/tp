package budgetbuddy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import budgetbuddy.model.Expense;
import org.junit.jupiter.api.Test;

public class ExpenseTest {

    @Test
    public void testValidExpenseCreation() {
        double amount = 100.50;
        String description = "Groceries";

        Expense expense = new Expense(amount, description);

        assertEquals(amount, expense.getAmount(), "Amount should be 100.50");
        assertEquals(description, expense.getDescription(), "Description should be 'Groceries'");
    }

    @Test
    public void addExpense_negativeAmount_exceptionThrown() {
        double amount = -50.00;
        String description = "Transport";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Expense(amount, description));
        assertEquals("Amount cannot be negative.", exception.getMessage());
    }

    @Test
    public void addExpense_nullDescription_exceptionThrown() {
        double amount = 100.00;
        String description = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Expense(amount, description));
        assertEquals("Description cannot be empty.", exception.getMessage());
    }

    @Test
    public void testToString() {
        double amount = 250.00;
        String description = "Dinner";
        Expense expense = new Expense(amount, description);

        String result = expense.toString();

        assertTrue(result.startsWith("$250.00 spent on"),
                "The string should start with formatted amount and 'spent on'");

        assertTrue(result.contains("Dinner"), "The string should contain the description");
    }

}
