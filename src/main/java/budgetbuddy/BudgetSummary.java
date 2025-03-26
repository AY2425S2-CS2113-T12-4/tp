package budgetbuddy;

import java.util.Map;

/**
 * This class summarizes the user budget by providing total expenses and spending limit.
 * This can summarize across multiple budgets.
 */
public class BudgetSummary {

    private final BudgetManager budgetManager;

    /**
     * Instantiate the class with a BudgetManager to access its private variable "budgets".
     * @param budgetManager is taken in the Duke class (main class).
     */
    public BudgetSummary(BudgetManager budgetManager) {
        this.budgetManager = budgetManager;
    }

    /**
     * This is the method called to get the summary printed.
     */
    public void summariseBudget() {
        Map<String, Budget> budgets = budgetManager.getBudgets(); // Access the budgets map

        System.out.println("Budget Summary:");
        for (String category : budgets.keySet()) {
            Budget budget = budgets.get(category);
            System.out.println("Category: " + category);
            System.out.println("Total Expenses: $" + budget.getTotalExpenses());
            System.out.println("Spending Limit: $" + budget.getLimit());
            System.out.println("----------------------");
        }
    }
}
