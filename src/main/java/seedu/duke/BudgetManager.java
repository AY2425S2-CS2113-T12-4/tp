package seedu.duke;

import java.util.HashMap;

/**
 * The BudgetManager class is responsible for managing multiple budgets.
 * It allows the addition of new budgets and the ability to add expenses to specific budgets.
 */
public class BudgetManager {
    private final HashMap<String, Budget> budgets;


    /**
     * Constructs a BudgetManager with an initial "Monthly" budget.
     * The "Monthly" budget is created with a default limit of 0.
     */
    public BudgetManager() {
        this.budgets = new HashMap<>();
        budgets.put("Monthly", new Budget("Monthly", 0));
    }

    /**
     * Adds a new budget to the BudgetManager.
     *
     * @param category The name of the budget category (e.g., "Food", "Transport").
     * @param limit The spending limit for the new budget.
     */
    public void addBudget(String category, double limit) {
        budgets.put(category, new Budget(category, limit));
    }

    /**
     * Adds an expense to a specific budget category.
     * If the category is empty or invalid, the expense is added to the "Monthly" budget.
     *
     * @param category The budget category to which the expense should be added (e.g., "Food", "Transport").
     * @param amount The amount of the expense.
     * @param description A brief description of the expense.
     */
    public void addExpenseToBudget(String category, double amount, String description) {
        Expense expense = new Expense(amount, description);
        if (category.equalsIgnoreCase("")) {
            category = "Monthly";
        }
        if (!budgets.containsKey(category)) {
            System.out.println("Budget category not found.");
            return;
        }
        budgets.get(category).addExpense(expense);
        System.out.println("Expense Added: " + expense);
    }

}
