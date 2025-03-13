package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BudgetManagerTest {

    private BudgetManager budgetManager;

    @BeforeEach
    public void setUp() {
        // Set up a new BudgetManager before each test
        budgetManager = new BudgetManager();
    }

    @Test
    public void testSetMonthlyBudget_validAmount_budgetSet() {
        // Set a monthly budget of 1000
        budgetManager.setBudget("set-budget 1000");

        // Assert the monthly budget is set to 1000
        Budget monthlyBudget = budgetManager.getBudgets().get("Monthly");
        assertEquals(1000, monthlyBudget.getLimit(), "Monthly budget should be set to 1000");
    }

    @Test
    public void testSetCategorySpecificBudget_validAmount_budgetSet() {
        // Set a budget of 300 for the "Food" category
        budgetManager.setBudget("set-budget c/Food 300");

        // Assert the budget for the "Food" category is set to 300
        Budget foodBudget = budgetManager.getBudgets().get("Food");
        assertEquals(300, foodBudget.getLimit(), "Food category budget should be set to 300");
    }

    @Test
    public void testSetBudget_invalidAmount_formatExceptionThrown() {
        // Try setting an invalid amount for the budget
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> budgetManager.setBudget("set-budget invalidAmount"));
        assertEquals("Invalid amount format.", exception.getMessage());
    }

    @Test
    public void testSetBudget_categoryNotFound() {
        // Try setting a budget for a non-existing category
        budgetManager.setBudget("set-budget c/NonExistentCategory 500");

        // Assert that the category does not exist
        Budget nonExistentBudget = budgetManager.getBudgets().get("NonExistentCategory");
        assertEquals(500, nonExistentBudget.getLimit(), "Non-existent category should be created with a budget of 500");
    }

}
