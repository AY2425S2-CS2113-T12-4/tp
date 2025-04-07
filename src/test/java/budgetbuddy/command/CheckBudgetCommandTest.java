package budgetbuddy.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import budgetbuddy.model.BudgetManager;
import budgetbuddy.exception.InvalidInputException;

import static org.junit.jupiter.api.Assertions.*;

public class CheckBudgetCommandTest {

    private BudgetManager budgetManager;

    @BeforeEach
    public void setUp() {
        budgetManager = new BudgetManager();
    }

    @Test
    public void testExecute_validCategory() {
        try {
            budgetManager.setBudget("Food", 100.0);

            CheckBudgetCommand command = new CheckBudgetCommand("check-budget c/Monthly");
            command.execute(budgetManager);

        } catch (InvalidInputException e) {
            fail("Unexpected InvalidInputException: " + e.getMessage());
        }
    }

    @Test
    public void testExecute_invalidCategory_throwsException() {
        CheckBudgetCommand command = new CheckBudgetCommand("check-budget UnknownCategory");

        assertThrows(InvalidInputException.class, () -> {
            command.execute(budgetManager);
        }, "Expected InvalidInputException for invalid category");
    }


    @Test
    public void testIsExit_alwaysReturnsFalse() {
        CheckBudgetCommand command = new CheckBudgetCommand("check-budget c/Food");
        assertFalse(command.isExit());
    }
}
