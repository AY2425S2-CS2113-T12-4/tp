package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.exception.InvalidInputException;

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

    @Test
    public void testDeleteExpense_validIndex_expenseDeleted() throws InvalidInputException {
        // Add expenses to the Monthly budget
        budgetManager.addExpenseToBudget("", 50, "Lunch");
        budgetManager.addExpenseToBudget("", 20, "Coffee");

        // Ensure 2 expenses exist before deletion
        Budget monthlyBudget = budgetManager.getBudgets().get("Monthly");
        assertEquals(70, monthlyBudget.getTotalExpenses(), "The total expenses should be 70");

        // Delete the most recent expense (Coffee at index 1)
        budgetManager.deleteExpense(1);

        // Ensure 1 expense remains after deletion
        assertEquals(50, monthlyBudget.getTotalExpenses(), "The total expenses should be 20");
    }

    @Test
    public void testDeleteExpense_invalidIndex_throwsException() {
        // Ensure Monthly budget exists but has no expenses
        assertThrows(InvalidInputException.class, () -> {
            budgetManager.deleteExpense(0); // Invalid index
        }, "Should throw InvalidInputException when deleting a non-existent expense");
    }

    

}
