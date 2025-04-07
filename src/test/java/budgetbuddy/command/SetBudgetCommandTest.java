package budgetbuddy.command;

import budgetbuddy.model.BudgetManager;
import budgetbuddy.exception.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class SetBudgetCommandTest {

    private BudgetManager budgetManager;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream originalSystemOut = System.out;

    @BeforeEach
    public void setUp() {
        // Create a new BudgetManager instance before each test
        budgetManager = new BudgetManager();

        // Redirect System.out to capture output for testing
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void testExecute_validCategoryAndAmount_setsCategoryBudget() throws InvalidInputException {
        // Test setting a valid category budget
        SetBudgetCommand command = new SetBudgetCommand("set-budget c/Food 100");

        command.execute(budgetManager);

        String output = outputStreamCaptor.toString();
        // Verify that the correct confirmation message is printed
        assertTrue(output.contains("Budget for Food set to: $100.0"));
    }

    @Test
    public void testExecute_emptyCategory_setsOverallBudget() throws InvalidInputException {
        // Test setting the overall budget when no category is specified
        SetBudgetCommand command = new SetBudgetCommand("set-budget c/ 100");

        command.execute(budgetManager);

        String output = outputStreamCaptor.toString();
        // Verify that the overall budget is set correctly
        assertTrue(output.contains("Overall Budget set to: $100.0"));
    }

    @Test
    public void testExecute_negativeAmount_throwsInvalidInputException() {
        // Test setting a budget with a negative amount (invalid input)
        SetBudgetCommand command = new SetBudgetCommand("Food -50");

        // Expect InvalidInputException to be thrown
        assertThrows(InvalidInputException.class, () -> {
            command.execute(budgetManager);
        });
    }

    @Test
    public void testExecute_excessivelyLargeAmount_throwsInvalidInputException() throws InvalidInputException {
        // Test setting a budget with an unrealistically large value
        SetBudgetCommand command = new SetBudgetCommand("set-budget c/Food 1000000000");

        // Expect InvalidInputException due to amount exceeding limit
        assertThrows(InvalidInputException.class, () -> {
            command.execute(budgetManager);
        });
    }

    @Test
    public void testExecute_nonNumericAmount_throwsInvalidInputException() {
        // Test setting a budget with a non-numeric value
        SetBudgetCommand command = new SetBudgetCommand("set-budget c/Food abc");

        // Expect InvalidInputException due to parsing failure
        assertThrows(InvalidInputException.class, () -> {
            command.execute(budgetManager);
        });
    }

    @Test
    public void testExecute_categoryWithSpecialCharacters_setsCategoryBudget() throws InvalidInputException {
        // Test setting a budget for a category with special characters
        SetBudgetCommand command = new SetBudgetCommand("set-budget c/Food@Home 200");

        command.execute(budgetManager);

        String output = outputStreamCaptor.toString();
        // Verify that the budget is set correctly for the special character category
        assertTrue(output.contains("Budget for Food@Home set to: $200.0"));
    }

    @Test
    public void testIsExit_alwaysReturnsFalse() {
        // Verify that the SetBudgetCommand does not signal the app to exit
        SetBudgetCommand command = new SetBudgetCommand("set-budget c/Food 100");
        assertFalse(command.isExit());
    }
}
