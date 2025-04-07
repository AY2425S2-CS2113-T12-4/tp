package budgetbuddy.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import budgetbuddy.model.BudgetManager;
import budgetbuddy.exception.InvalidInputException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class DeleteExpenseCommandTest {

    private BudgetManager budgetManager;

    @BeforeEach
    public void setUp() {
        budgetManager = new BudgetManager();
        // Add some initial expenses for testing
        budgetManager.addExpenseToBudget("", 50.0, "Lunch", "Jan 15 2023 at 12:30");
        budgetManager.addExpenseToBudget("", 20.0, "Bus fare", "Jan 16 2023 at 08:00");
    }

    @Test
    public void testExecute_validExpense() {
        try {
            DeleteCommand command = new DeleteCommand("delete 1");
            command.execute(budgetManager);
            // Verify the expense was deleted
            assertTrue(budgetManager.getBudgets().get("Overall").getExpenses().size() == 1,
                    "Expected one expense to remain after deletion");
        } catch (InvalidInputException e) {
            fail("Unexpected InvalidInputException: " + e.getMessage());
        }
    }

    @Test
    public void testExecute_invalidExpense_throwsException() {
        DeleteCommand command = new DeleteCommand("delete 999");
        assertThrows(InvalidInputException.class, () -> {
            command.execute(budgetManager);
        }, "Expected InvalidInputException for non-existent expense");
    }

    @Test
    public void testExecute_deleteFromEmptyBudget_throwsException() {
        BudgetManager emptyBudgetManager = new BudgetManager();
        DeleteCommand command = new DeleteCommand("delete 1");
        assertThrows(InvalidInputException.class, () -> {
            command.execute(emptyBudgetManager);
        }, "Expected InvalidInputException for deleting from an empty budget");
    }

    @Test
    public void testExecute_deleteMultipleExpenses() {
        try {
            DeleteCommand command1 = new DeleteCommand("delete 1");
            command1.execute(budgetManager);
            DeleteCommand command2 = new DeleteCommand("delete 1");
            command2.execute(budgetManager);
            // Verify all expenses were deleted
            assertTrue(budgetManager.getBudgets().get("Overall").getExpenses().isEmpty(),
                    "Expected the expense list to be empty after deleting all expenses");
        } catch (InvalidInputException e) {
            fail("Unexpected InvalidInputException: " + e.getMessage());
        }
    }

    @Test
    void testExecute_nonNumericInput_exceptionThrown() {
        DeleteCommand command = new DeleteCommand("delete abc");

        NumberFormatException exception = assertThrows(NumberFormatException.class, () -> command.execute
                (budgetManager));
    }

    @Test
    void testIsExit_alwaysReturnsFalse() {
        DeleteCommand command = new DeleteCommand("delete 1");

        assertFalse(command.isExit(), "isExit should always return false for DeleteCommand.");
    }
}
