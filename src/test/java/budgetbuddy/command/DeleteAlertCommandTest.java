package budgetbuddy.command;

import budgetbuddy.exception.InvalidInputException;
import budgetbuddy.model.BudgetManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DeleteAlertCommandTest {

    private BudgetManager budgetManager;

    @BeforeEach
    void setUp() {
        budgetManager = new BudgetManager();
    }

    @Test
    void testConstructor_validDescription_noExceptionThrown() {
        assertDoesNotThrow(() -> new DeleteAlertCommand("delete-alert"));
    }

    @Test
    void testExecute_invalidCommand_exceptionThrown() {
        DeleteAlertCommand command = new DeleteAlertCommand("delete-alert extra");

        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> command.execute
                (budgetManager));
        assertTrue(exception.getMessage().contains("Invalid format"), "Exception message should indicate " +
                "invalid format.");
    }

    @Test
    void testIsExit_alwaysReturnsFalse() {
        DeleteAlertCommand command = new DeleteAlertCommand("delete-alert");

        assertFalse(command.isExit(), "isExit should always return false for DeleteAlertCommand.");
    }
}