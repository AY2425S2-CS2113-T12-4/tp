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

            assertEquals(100.0, budgetManager.getBudgetAlert().getAlertAmount(),
                    "Budget alert should be set to 100");
        } catch (InvalidInputException e) {
            fail("Unexpected InvalidInputException: " + e.getMessage());
        }
    }

    @Test
    public void testExecute_editAlertCommand() {
        try {
            // First set it
            AlertCommand alertCommand = new AlertCommand("alert 100");
            alertCommand.execute(budgetManager);

            // Then edit
            EditAlertCommand editAlertCommand = new EditAlertCommand("alert 150");
            editAlertCommand.execute(budgetManager);

            assertEquals(150.0, budgetManager.getBudgetAlert().getAlertAmount(),
                    "Alert should be updated to 150");
        } catch (InvalidInputException e) {
            fail("Unexpected InvalidInputException: " + e.getMessage());
        }
    }

    @Test
    public void testAlertCheck_exactlyHitsLimit_shouldNotExceed() {
        try {
            AlertCommand alertCommand = new AlertCommand("alert 200");
            alertCommand.execute(budgetManager);

            // Add an expense that hits 200
            budgetManager.addExpenseToBudget("", 200, "Lunch", "2024-01-01 12:00");

            assertEquals(200.0, budgetManager.getTotalExpenses(), 0.01,
                    "Total expenses should exactly match alert");
            assertEquals(200.0, budgetManager.getBudgetAlert().getAlertAmount(),
                    "Alert should remain at 200");

        } catch (InvalidInputException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
}
