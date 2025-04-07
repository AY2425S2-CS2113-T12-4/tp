package budgetbuddy.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import budgetbuddy.model.BudgetManager;
import budgetbuddy.exception.InvalidInputException;
import budgetbuddy.model.Budget;
import budgetbuddy.model.Expense;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AddExpenseCommandTest {

    private BudgetManager budgetManager;
    private AddExpenseCommand addExpenseCommand;

    @BeforeEach
    public void setUp() {
        budgetManager = new BudgetManager();
    }

    @Test
    public void testExecute_addExpenseSuccessfully() {
        String description = "add 50 c/ d/ lunch";
        addExpenseCommand = new AddExpenseCommand(description);

        try {
            addExpenseCommand.execute(budgetManager);

            Map<String, Budget> budgets = budgetManager.getBudgets();
            Budget monthlyBudget = budgets.get("Monthly");
            List<Expense> expenses = monthlyBudget.getExpenses();

            assertEquals(1, expenses.size(), "Expense should be added to the monthly budget");

            Expense addedExpense = expenses.get(0);
            assertEquals(50.0, addedExpense.getAmount(), "Expense amount should be 50");
            assertEquals("lunch", addedExpense.getDescription(), "Expense description should be " +
                    "'lunch'");
        } catch (InvalidInputException e) {
            System.out.println("Invalid input");
        }
    }

    @Test
    public void testExecute_validFullInput_addsCorrectly() throws InvalidInputException {
        String description = "add 25.5 c/Food d/Brunch t/Apr 06 2025 at 10:30";
        addExpenseCommand = new AddExpenseCommand(description);
        addExpenseCommand.execute(budgetManager);

        Budget overallBudget = budgetManager.getBudgets().get("Overall");
        Expense expense = overallBudget.getExpenses().get(0);

        assertEquals(25.5, expense.getAmount());
        assertEquals("Brunch", expense.getDescription());
        assertEquals("Apr 06 2025 at 10:30", expense.getDateTimeString());
    }
    @Test
    public void testExecute_invalidTimeFormat_usesSystemTime() throws InvalidInputException {
        String description = "add 100 c/Transport d/TaxiRide t/06-April-2025";
        addExpenseCommand = new AddExpenseCommand(description);
        addExpenseCommand.execute(budgetManager);

        Budget overallBudget = budgetManager.getBudgets().get("Overall");
        Expense expense = overallBudget.getExpenses().get(0);

        assertEquals(100, expense.getAmount());
        assertEquals("TaxiRide", expense.getDescription());
        assertNotEquals("06-April-2025", expense.getDateTimeString()); //System time should be registered
    }

    @Test
    public void testExecute_missingAmount_throwsInvalidInputException() {
        String description = "add c/Food d/Lunch t/Apr 06 2025 at 12:00";
        addExpenseCommand = new AddExpenseCommand(description);

        try {
            addExpenseCommand.execute(budgetManager);
        } catch (InvalidInputException | NumberFormatException e) {
            assertEquals(0, budgetManager.getBudgets().get("Overall").getExpenses().size());
        }
    }
    @Test
    public void testExecute_unknownCategory_addsToOverallOnly() throws InvalidInputException {
        String description = "add 40 c/UnknownCategory d/Snack t/Apr 06 2025 at 15:00";
        addExpenseCommand = new AddExpenseCommand(description);
        addExpenseCommand.execute(budgetManager);

        Budget overallBudget = budgetManager.getBudgets().get("Overall");
        assertEquals(1, overallBudget.getExpenses().size());
        assertFalse(budgetManager.getBudgets().containsKey("UnknownCategory")); // UnknownCategory should not be created
    }



}
