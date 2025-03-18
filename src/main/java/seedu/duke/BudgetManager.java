package seedu.duke;

import java.util.HashMap;

/**
 * The BudgetManager class is responsible for managing multiple budgets.
 * It allows the addition of new budgets and the ability to add expenses to specific budgets.
 */
public class BudgetManager {
    private final HashMap<String, Budget> budgets;
    private double budgetAlertLimit = 0;


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

        // 🔔 Check if the budget alert limit is exceeded
        checkBudgetAlert();
    }

    /**
     * Sets a budget alert at the specified amount.
     * If total expenses exceed this limit, a notification will be triggered.
     *
     * @param amount The alert threshold. If 0, the alert is removed.
     */

    public void setBudgetAlert(double amount) {
        if (amount < 0) {
            System.out.println("Alert amount must be a positive number.");
            return;
        }
        this.budgetAlertLimit = amount;
        if (amount == 0) {
            System.out.println("🔕 Budget alert removed.");
        } else {
            System.out.println("🔔 Budget alert set at $" + amount);
        }
    }

    /**
     * Checks if total expenses exceed the alert limit.
     */
    private void checkBudgetAlert() {
        double totalExpenses = getTotalExpenses();
        if (budgetAlertLimit > 0 && totalExpenses > budgetAlertLimit) {
            System.out.println("⚠️ ALERT: Total expenses ($" + totalExpenses + ") exceed the set limit of $" + budgetAlertLimit + "!");
        }
    }

    /**
     * Calculates the total expenses across all budgets.
     *
     * @return The sum of all expenses.
     */
    public double getTotalExpenses() {
        return budgets.values().stream().mapToDouble(Budget::getTotalExpenses).sum();
    }


}
