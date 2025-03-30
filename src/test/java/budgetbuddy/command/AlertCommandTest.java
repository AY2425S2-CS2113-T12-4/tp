package budgetbuddy.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import budgetbuddy.model.BudgetManager;
import budgetbuddy.exception.InvalidInputException;

public class AlertCommandTest {

    private BudgetManager budgetManager;

    @BeforeEach
    public void setUp() {
        budgetManager = new BudgetManager();
    }

    @Test
    public void testExecute_validAlert_setsBudgetAlert() {
        try {
            AlertCommand alertCommand = new AlertCommand("alert 100");
            alertCommand.execute(budgetManager);

            assertEquals(100.0, budgetManager.getBudgetAlert().getAlertAmount(), "Budget alert " +
                    "should be set to 100");
        } catch (InvalidInputException e) {
            fail("Unexpected InvalidInputException: " + e.getMessage());
        }
    }

}
