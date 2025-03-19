package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        // Set a Monthly budget of 1000
        budgetManager.setBudget("", 1000);

        // Assert the monthly budget is set to 1000
        Budget monthlyBudget = budgetManager.getBudgets().get("Monthly");
        assertEquals(1000, monthlyBudget.getLimit(), "Monthly budget should be set to 1000");
    }

    @Test
    public void testSetCategorySpecificBudget_validAmount_budgetSet() {
        // Set a budget of 300 for the "Food" category
        budgetManager.setBudget("Food", 300);

        // Assert the budget for the "Food" category is set to 300
        Budget foodBudget = budgetManager.getBudgets().get("Food");
        assertEquals(300, foodBudget.getLimit(), "Food category budget should be set to 300");
    }

}
