package budgetbuddy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import budgetbuddy.model.BudgetManager;
import budgetbuddy.model.BudgetSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class BudgetSummaryTest {

    private BudgetSummary budgetSummary;
    private BudgetManager budgetManager;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        // Redirect system output for testing
        System.setOut(new PrintStream(outputStream));

        // Set up a new BudgetManager and BudgetSummary before each test
        budgetManager = new BudgetManager();
        budgetSummary = new BudgetSummary(budgetManager);
    }

    @Test
    public void testSummariseBudget_noBudgets_printsEmptySummary() {
        budgetSummary.summariseBudget();
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Budget Summary:"), "Output should contain 'Budget Summary:'");
    }

    @Test
    public void testSummariseBudget_withBudgets_correctSummaryPrinted() {
        // Set up budgets
        budgetManager.setBudget("", 2000);
        budgetManager.setBudget("Food", 500);
        budgetManager.setBudget("Transport", 300);

        // Call the method
        budgetSummary.summariseBudget();
        String output = outputStream.toString().trim();

        // Assert the summary contains expected values
        assertTrue(output.contains("Budget Summary:"), "Output should contain 'Budget Summary:'");
        assertTrue(output.contains("Category: Overall"), "Output should contain 'Category: Overall'");
        assertTrue(output.contains("Spending Limit: $2000"), "Output should show the overall" +
                " budget limit of 2000");
        assertTrue(output.contains("Category: Food"), "Output should contain 'Category: Food'");
        assertTrue(output.contains("Spending Limit: $500"), "Output should show the Food category " +
                "budget limit of 500");
        assertTrue(output.contains("Category: Transport"), "Output should contain 'Category: Transport'");
        assertTrue(output.contains("Spending Limit: $300"), "Output should show the Transport category " +
                "budget limit of 300");
    }
}
