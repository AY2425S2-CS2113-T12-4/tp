package budgetbuddy.command;

import budgetbuddy.exception.InvalidInputException;
import budgetbuddy.model.BudgetManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;

class EditAlertCommandTest {

    private BudgetManager budgetManager;

    @BeforeEach
    void setUp() {
        budgetManager = new BudgetManager();
        budgetManager.setBudgetAlert(100.0);
    }

    @Test
    void testConstructor_validDescription_noExceptionThrown() {
        assertDoesNotThrow(() -> new EditAlertCommand("edit-alert 200"));
    }

    @Test
    void testExecute_validAmount_alertUpdated() throws InvalidInputException {
        EditAlertCommand command = new EditAlertCommand("edit-alert 200");

        assertDoesNotThrow(() -> command.execute(budgetManager));

        assertEquals(200.0, budgetManager.getBudgetAlert().getAlertAmount(), 0.01);
    }

    @Test
    void testExecute_invalidAmount_exceptionThrown() {
        EditAlertCommand command = new EditAlertCommand("edit-alert -50");

        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> command.execute
                (budgetManager));
    }

    @Test
    void testExecute_nonNumericInput_exceptionThrown() {
        EditAlertCommand command = new EditAlertCommand("edit-alert abc");

        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> command.execute
                (budgetManager));
    }

    @Test
    void testIsExit_alwaysReturnsFalse() {
        EditAlertCommand command = new EditAlertCommand("edit-alert 200");

        assertFalse(command.isExit(), "isExit should always return false for EditAlertCommand.");
    }
}