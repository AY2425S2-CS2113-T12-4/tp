package budgetbuddy.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import budgetbuddy.exception.InvalidInputException;
import budgetbuddy.model.Budget;
import budgetbuddy.model.BudgetManager;
import budgetbuddy.model.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class AddRecurringExpenseCommandTest {

    private BudgetManager budgetManager;
    private AddRecurringExpenseCommand addRecurringExpenseCommand;

    @BeforeEach
    public void setUp() {
        budgetManager = new BudgetManager();
    }

    @Test
    public void testExecute_addRecurringExpensesToOverallSuccessfully() {
        String description = "add-recurring 25 c/Overall d/Gym t/Apr 01 2025 at 07:00 f/10 i/3";
        addRecurringExpenseCommand = new AddRecurringExpenseCommand(description);

        try {
            addRecurringExpenseCommand.execute(budgetManager);
            Map<String, Budget> budgets = budgetManager.getBudgets();
            Budget overallBudget = budgets.get("Overall");
            List<Expense> expenses = overallBudget.getExpenses();

            assertEquals(3, expenses.size(), "There should be 3 recurring expenses in the Overall budget");

            Expense firstExpense = expenses.get(0);
            assertEquals(25.0, firstExpense.getAmount());
            assertEquals("Gym", firstExpense.getDescription());

        } catch (InvalidInputException e) {
            System.out.println("Unexpected invalid input during test 1");
        }
    }

    @Test
    public void testExecute_maxIterationsExceeded_shouldNotAdd() {
        String description = "add-recurring 10 c/Overall d/OverLimitTest t/Apr 01 2025 at 10:00 f/5 i/12";
        addRecurringExpenseCommand = new AddRecurringExpenseCommand(description);

        try {
            addRecurringExpenseCommand.execute(budgetManager);
        } catch (InvalidInputException e) {
            System.out.println("Expected exception for exceeding max iterations.");
        }

        Map<String, Budget> budgets = budgetManager.getBudgets();
        assertTrue(!budgets.containsKey("Overall") || budgets.get("Overall").getExpenses().isEmpty(),
                "No expenses should be added when iterations exceed the limit");
    }

    @Test
    public void testExecute_maxFrequencyExceeded_shouldNotAdd() {
        String description = "add-recurring 10 c/Overall d/OverFreq t/Apr 01 2025 at 10:00 f/2000 i/2";
        addRecurringExpenseCommand = new AddRecurringExpenseCommand(description);

        try {
            addRecurringExpenseCommand.execute(budgetManager);
        } catch (InvalidInputException e) {
            System.out.println("Expected exception for exceeding max frequency.");
        }

        Map<String, Budget> budgets = budgetManager.getBudgets();
        assertTrue(!budgets.containsKey("Overall") || budgets.get("Overall").getExpenses().isEmpty(),
                "No expenses should be added when frequency exceeds the limit");
    }

    @Test
    public void testExecute_invalidDateTimeFallbacksToCurrent() {
        String description = "add-recurring 15 c/Overall d/FallbackDate t/Apr 2025 f/7 i/2";
        addRecurringExpenseCommand = new AddRecurringExpenseCommand(description);

        try {
            addRecurringExpenseCommand.execute(budgetManager);
            Map<String, Budget> budgets = budgetManager.getBudgets();
            Budget overallBudget = budgets.get("Overall");
            List<Expense> expenses = overallBudget.getExpenses();

            assertEquals(2, expenses.size(), "Fallback should still allow expenses to be added");
            assertTrue(expenses.get(0).getDescription().contains("FallbackDate"), "Description should match");

        } catch (InvalidInputException e) {
            System.out.println("Unexpected exception in fallback test.");
        }
    }
}
