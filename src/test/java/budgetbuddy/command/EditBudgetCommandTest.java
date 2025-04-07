package budgetbuddy.command;

import budgetbuddy.exception.InvalidInputException;
import budgetbuddy.model.BudgetManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class EditBudgetCommandTest {

    private BudgetManager budgetManager;

    @BeforeEach
    void setUp() {
        budgetManager = new BudgetManager();
        budgetManager.setBudget("Food", 100.0); // Add an initial budget for testing
    }

    @Test
    void testConstructor_validDescription_noExceptionThrown() {
        assertDoesNotThrow(() -> new EditBudgetCommand("edit-budget old/Food a/200"));
    }

    @Test
    void testExecute_validAmount_budgetUpdated() throws InvalidInputException {
        EditBudgetCommand command = new EditBudgetCommand("edit-budget old/Food a/200");

        assertDoesNotThrow(() -> command.execute(budgetManager));

        assertEquals(200.0, budgetManager.getBudgets().get("Food").getLimit(), 0.01);
    }

    @Test
    void testExecute_validName_budgetRenamed() throws InvalidInputException {
        EditBudgetCommand command = new EditBudgetCommand("edit-budget old/Food c/Groceries");

        assertDoesNotThrow(() -> command.execute(budgetManager));

        assertNull(budgetManager.getBudgets().get("Food"), "The old budget name should no longer exist.");
        assertNotNull(budgetManager.getBudgets().get("Groceries"), "The new budget name should exist.");
    }

    @Test
    void testExecute_validAmountAndName_budgetUpdatedAndRenamed() throws InvalidInputException {
        EditBudgetCommand command = new EditBudgetCommand("edit-budget old/Food a/300 c/Groceries");

        assertDoesNotThrow(() -> command.execute(budgetManager));

        assertNull(budgetManager.getBudgets().get("Food"), "The old budget name should no longer exist.");
        assertNotNull(budgetManager.getBudgets().get("Groceries"), "The new budget name should exist.");
        assertEquals(300.0, budgetManager.getBudgets().get("Groceries").getLimit(), 0.01);
    }

    @Test
    void testExecute_invalidBudget_exceptionThrown() {
        EditBudgetCommand command = new EditBudgetCommand("edit-budget old/Transport a/200");

        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> command.execute
                (budgetManager));
        assertTrue(exception.getMessage().contains("Budget 'Transport' not found"), "Exception message should "
                + "indicate invalid budget.");
    }

    @Test
    void testExecute_invalidAmount_exceptionThrown() {
        EditBudgetCommand command = new EditBudgetCommand("edit-budget old/Food a/-50");

        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> command.execute
                (budgetManager));
        assertTrue(exception.getMessage().contains("Amount must be between 0 and 100000"), "Exception " +
                "message should indicate invalid amount.");
    }

    @Test
    void testExecute_nonNumericAmount_exceptionThrown() {
        EditBudgetCommand command = new EditBudgetCommand("edit-budget old/Food a/abc");

        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> command.execute
                (budgetManager));
        assertTrue(exception.getMessage().contains("Invalid amount format"), "Exception message should " +
                "indicate invalid format.");
    }

    @Test
    void testIsExit_alwaysReturnsFalse() {
        EditBudgetCommand command = new EditBudgetCommand("edit-budget old/Food a/200");

        assertFalse(command.isExit(), "isExit should always return false for EditBudgetCommand.");
    }
}