package budgetbuddy.command;

import budgetbuddy.exception.InvalidInputException;
import budgetbuddy.model.BudgetManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SummaryCommandTest {

    private BudgetManager budgetManager;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream originalSystemOut = System.out;

    @BeforeEach
    public void setUp() {
        budgetManager = new BudgetManager();
        budgetManager.setBudget("Food", 100);
        budgetManager.setBudget("Transport", 50);

        // Redirect System.out to capture the output
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void testExecute_noCategorySpecified_displaysSummaryForAllCategories() throws InvalidInputException {
        SummaryCommand command = new SummaryCommand("summary"); // No category = all

        command.execute(budgetManager);

        // Assert that the output contains budget summaries for both Food and Transport
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Category: Food"));
        assertTrue(output.contains("Category: Transport"));
        assertTrue(output.contains("Total Expenses"));
        assertTrue(output.contains("Remaining Budget"));
        assertTrue(output.contains("Spending Limit"));
    }

    @Test
    public void testExecute_validCategorySpecified_displaysSummaryForThatCategory() throws InvalidInputException {
        SummaryCommand command = new SummaryCommand("summary c/Food");

        command.execute(budgetManager);

        // Assert that the output contains budget summaries for Food only
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Category: Food"));
        assertTrue(output.contains("Total Expenses"));
        assertTrue(output.contains("Remaining Budget"));
        assertTrue(output.contains("Spending Limit"));
    }

    @Test
    public void testExecute_invalidCategorySpecified_throwsInvalidInputException() {
        SummaryCommand command = new SummaryCommand("summary c/Groceries");

        assertThrows(InvalidInputException.class, () -> {
            command.execute(budgetManager);
        });
    }

    @Test
    public void testExecute_emptyCategorySpecified_displaysSummaryForAllCategories() throws InvalidInputException {
        SummaryCommand command = new SummaryCommand("summary c/");

        command.execute(budgetManager);

        // Assert that the output contains all budget categories
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Budget Summary"));
        assertTrue(output.contains("Category: Food"));
        assertTrue(output.contains("Category: Transport"));
    }

    @Test
    public void  testIsExit_alwaysReturnsFalse() {
        SummaryCommand command = new SummaryCommand("summary");
        assertFalse(command.isExit());
    }
}
