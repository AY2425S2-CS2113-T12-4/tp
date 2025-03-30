package budgetbuddy.command;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import budgetbuddy.model.BudgetManager;
import budgetbuddy.exception.InvalidInputException;

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
}
