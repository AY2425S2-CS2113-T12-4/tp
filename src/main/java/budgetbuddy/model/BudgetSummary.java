package budgetbuddy.model;

import budgetbuddy.ui.Ui;

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
        Ui.printBudgetSummary(budgets);
    }
}
