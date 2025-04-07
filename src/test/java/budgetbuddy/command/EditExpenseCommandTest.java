package budgetbuddy.command;

import budgetbuddy.exception.InvalidInputException;
import budgetbuddy.model.BudgetManager;
import budgetbuddy.model.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class EditExpenseCommandTest {

    private BudgetManager budgetManager;

    @BeforeEach
    void setUp() {
        budgetManager = new BudgetManager();
        budgetManager.addExpenseToBudget("",20.0, "Groceries", "Oct 01 2023 at 10:00)");
        budgetManager.addExpenseToBudget("",30.0, "Dinner", "Oct 02 2023 at 12:00)");
    }

    @Test
    void testConstructor_validDescription_noExceptionThrown() {
        assertDoesNotThrow(() -> new EditExpenseCommand("edit-expense 2 a/ 25"));
    }

    @Test
    void testExecute_validAmount_expenseAmountUpdated() throws InvalidInputException {
        EditExpenseCommand command = new EditExpenseCommand("edit-expense 2 a/ 25");

        assertDoesNotThrow(() -> command.execute(budgetManager));

        assertEquals(25.0, budgetManager.getBudgets().get("Overall").getExpenses().get(0).getAmount()
                , 0.01);
    }

    @Test
    void testExecute_validDescription_expenseDescriptionUpdated() throws InvalidInputException {
        EditExpenseCommand command = new EditExpenseCommand("edit-expense 2 d/ Lunch");

        assertDoesNotThrow(() -> command.execute(budgetManager));

        assertEquals("Lunch", budgetManager.getBudgets().get("Overall").getExpenses().get(0).getDescription());
    }

    @Test
    void testExecute_validDateTime_expenseDateTimeUpdated() throws InvalidInputException {
        EditExpenseCommand command = new EditExpenseCommand("edit-expense 2 t/ Oct 03 2023 at 14:00");

        assertDoesNotThrow(() -> command.execute(budgetManager));

        assertEquals("Oct 03 2023 at 14:00", budgetManager.
                getBudgets().get("Overall").getExpenses().get(0).getDateTimeString());
    }

    @Test
    void testExecute_validAllFields_expenseFullyUpdated() throws InvalidInputException {
        // Create a valid EditExpenseCommand to update all fields of the first expense
        EditExpenseCommand command = new EditExpenseCommand("edit-expense 2 a/ 40 d/ Breakfast t/ " +
                "Oct 04 2023 at 08:00");

        assertDoesNotThrow(() -> command.execute(budgetManager));

        Expense updatedExpense = budgetManager.getBudgets().get("Overall").getExpenses().get(0);
        assertEquals(40.0, updatedExpense.getAmount(), 0.01);
        assertEquals("Breakfast", updatedExpense.getDescription());
        assertEquals("Oct 04 2023 at 08:00", budgetManager.
                getBudgets().get("Overall").getExpenses().get(0).getDateTimeString());
    }

    @Test
    void testExecute_invalidIndex_exceptionThrown() {
        EditExpenseCommand command = new EditExpenseCommand("edit-expense 3 a/ 25");

        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> command.execute
                (budgetManager));
        assertTrue(exception.getMessage().contains("Invalid index"), "Exception message should " +
                "indicate invalid index.");
    }

    @Test
    void testExecute_invalidAmount_exceptionThrown() {
        EditExpenseCommand command = new EditExpenseCommand("edit-expense 2 a/ -10");

        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> command.execute
                (budgetManager));
        assertTrue(exception.getMessage().contains("Amount must be between 0 and 10000"), "Exception " +
                "message should indicate invalid amount.");
    }

    @Test
    void testIsExit_alwaysReturnsFalse() {
        EditExpenseCommand command = new EditExpenseCommand("edit-expense 2 a/ 25");

        assertFalse(command.isExit(), "isExit should always return false for EditExpenseCommand.");
    }
}
