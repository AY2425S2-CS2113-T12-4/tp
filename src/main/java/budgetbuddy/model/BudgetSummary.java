package budgetbuddy.model;

import budgetbuddy.ui.Ui;

import java.util.HashMap;
import java.util.Map;

/**
 * This class summarizes the user budget by providing total expenses and spending limit.
 * This can summarize across multiple budgets.
 */
public class BudgetSummary {

    private final BudgetManager budgetManager;

    public BudgetSummary(BudgetManager budgetManager) {
        this.budgetManager = budgetManager;
    }

    /**
     * Summarizes all budgets.
     */
    public void summariseBudget() {
        Map<String, Budget> budgets = budgetManager.getBudgets();
        Ui.printBudgetSummary(budgets);
    }

    /**
     * Summarizes only specified categories.
     */
    public void summariseBudget(String[] categories) {
        Map<String, Budget> filtered = new HashMap<>();
        for (String cat : categories) {
            Budget b = budgetManager.getBudgets().get(cat);
            if (b != null) {
                filtered.put(cat, b);
            }
        }
        Ui.printBudgetSummary(filtered);
    }
}
