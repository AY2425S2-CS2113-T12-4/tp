package seedu.duke;

import seedu.duke.exception.InvalidInputException;

import java.util.HashMap;
import java.util.Map;

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
     * Sets the spending limit for the "Monthly" budget or a specific category.
     *
     * @param command The command to set the budget (e.g., "set-budget 1000" or "set-budget c/Food 300").
     */
    public void setBudget(String command) {
        String[] parts = command.split(" ");
        if (parts.length != 2 && parts.length != 3) {
            System.out.println("Invalid command format. Usage: set-budget AMOUNT or set-budget c/CATEGORY AMOUNT");
            return;
        }

        // If it's setting the monthly budget
        if (parts.length == 2) {
            try {
                double amount = Double.parseDouble(parts[1]);
                budgets.put("Monthly", new Budget("Monthly", amount));
                System.out.println("Monthly budget set to: $" + amount);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid amount format.");
            }
        }
        // If it's setting a budget for a specific category
        else if (parts.length == 3 && parts[1].startsWith("c/")) {
            String category = parts[1].substring(2); // Remove "c/"
            try {
                double amount = Double.parseDouble(parts[2]);
                // If the category does not exist, create it
                if (!budgets.containsKey(category)) {
                    budgets.put(category, new Budget(category, amount));
                } else {
                    budgets.get(category).setLimit(amount); // Update the existing category's limit
                }
                System.out.println("Budget for category " + category + " set to: $" + amount);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid amount format.");
            }
        } else {
            System.out.println("Invalid command format.");
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

    /**
     * Displays all expenses categorized under each budget.
     */
    public void listAllExpenses() {
        for (Map.Entry<String, Budget> entry : budgets.entrySet()) {
            Budget budget = entry.getValue();
            budget.printExpenses();
            System.out.println("----------------------");
        }
    }

    /**
     * Deletes an expense based on index.
     * @param index The index of the expense to delete.
     * @throws InvalidInputException if index is invalid.
     */
    public void deleteExpense(int index) throws InvalidInputException {
        budgets.get("Monthly").deleteExpense(index);
        System.out.println("----------------------");
    }
