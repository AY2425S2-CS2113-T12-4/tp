package budgetbuddy;

import budgetbuddy.model.Budget;
import budgetbuddy.model.BudgetManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import budgetbuddy.exception.InvalidInputException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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
        assertEquals(
                1000,
                monthlyBudget.getLimit(),
                "Monthly budget should be set to 1000"
        );
    }

    @Test
    public void testSetCategorySpecificBudget_validAmount_budgetSet() {
        // Set a budget of 300 for the "Food" category
        budgetManager.setBudget("Food", 300);

        // Assert the budget for the "Food" category is set to 300
        Budget foodBudget = budgetManager.getBudgets().get("Food");
        assertEquals(
                300,
                foodBudget.getLimit(),
                "Food category budget should be set to 300"
        );
    }

    @Test
    public void testDeleteExpense_validIndex_expenseDeleted() throws InvalidInputException {
        // Add expenses to the Monthly budget
        budgetManager.addExpenseToBudget("", 50, "Lunch","Oct 05 2025 at 12:30");
        budgetManager.addExpenseToBudget("", 20, "Coffee", "Oct 05 2025 at 12:30");

        // Ensure 2 expenses exist before deletion
        Budget monthlyBudget = budgetManager.getBudgets().get("Monthly");
        assertEquals(
                70,
                monthlyBudget.getTotalExpenses(),
                "The total expenses should be 70"
        );

        // Delete the most recent expense (Coffee at index 1)
        budgetManager.deleteExpense(1);

        // Ensure 1 expense remains after deletion
        assertEquals(
                50,
                monthlyBudget.getTotalExpenses(),
                "The total expenses should be 20"
        );
    }

    @Test
    public void testDeleteExpense_invalidIndex_throwsException() {
        // Ensure Monthly budget exists but has no expenses
        assertThrows(InvalidInputException.class, () -> {
            budgetManager.deleteExpense(0); // Invalid index
        }, "Should throw InvalidInputException when deleting a non-existent expense");
    }

    @Test
    public void testCreateMonthlyBudget_defaultExists() {
        Budget monthlyBudget = budgetManager.getBudgets().get("Monthly");
        assertNotNull(monthlyBudget, "Monthly budget should exist by default");
        assertEquals(0,
                monthlyBudget.getLimit(),
                "Default monthly budget should have a limit of 0"
        );
    }

    @Test
    public void testAddExpenseToMonthlyBudget() {
        budgetManager.addExpenseToBudget("", 100, "Groceries","Oct 05 2025 at 12:30");

        Budget monthlyBudget = budgetManager.getBudgets().get("Monthly");
        assertEquals(100,
                monthlyBudget.getTotalExpenses(),
                "Monthly budget total expenses should be 100"
        );
    }



    @Test
    public void testAddExpenseToNewCategory_budgetDoesNotExist() {
        budgetManager.addExpenseToBudget("Travel", 50, "Bus fare",
                "Oct 05 2025 at 12:30");

        assertNull(
                budgetManager.getBudgets().get("Travel"),
                "Budget category 'Travel' should not be created automatically"
        );
    }

    @Test
    public void testAddExpenseToExistingCategory() {
        budgetManager.setBudget("Food", 500);
        budgetManager.addExpenseToBudget("Food", 50, "Lunch","Oct 05 2025 at 12:30");

        Budget foodBudget = budgetManager.getBudgets().get("Food");
        assertNotNull(foodBudget, "Food budget should exist");
        assertEquals(50,
                foodBudget.getTotalExpenses(),
                "Food budget total expenses should be 50"
        );
    }

}
