package seedu.duke;

import seedu.duke.exception.InvalidInputException;
import java.util.HashMap;
import java.util.Map;

/**
 * The BudgetManager class is responsible for managing multiple budgets.
 * It allows tracking expenses and setting alerts when expenses exceed a threshold.
 */
public class BudgetManager {
    private final HashMap<String, Budget> budgets;
    private final Alert alert;

    /**
     * Constructs a BudgetManager with an initial "Monthly" budget.
     * The "Monthly" budget is created with a default limit of 0.
     */
    public BudgetManager() {
        this.budgets = new HashMap<>();
        this.alert = new Alert(); // Initialise alert system
        budgets.put("Monthly", new Budget("Monthly", 0));

        assert budgets != null : "Budgets HashMap should be initialized.";
        assert alert != null : "Alert system should be initialized.";
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

        if (!budgets.containsKey("Monthly")) {
            budgets.put("Monthly", new Budget("Monthly", 0));
        }
        budgets.get("Monthly").addExpense(expense);

        if (category != null && !category.trim().isEmpty()) {
            if (!budgets.containsKey(category)) {
                System.out.println("Budget category '" + category + "' not found. Please create the category first.");
            } else {
                budgets.get(category).addExpense(expense);
            }
        }

        System.out.println("Expense Added: " + expense);

        checkBudgetAlert();
    }


    /**
     * Sets a budget alert at the specified amount.
     * If total expenses exceed this limit, a notification will be triggered.
     *
     * @param amount The alert threshold. If 0, the alert is removed.
     */
    public void setBudgetAlert(double amount) {
        alert.setAlert(amount);
    }

    /**
     * Checks if total expenses exceed the alert limit.
     */
    private void checkBudgetAlert() {
        double totalExpenses = getTotalExpenses();
        alert.checkAlert(totalExpenses); // Alert system will notify if limit is exceeded
    }

    /**
     * Retrieves all budgets managed by BudgetManager.
     *
     * @return A map of budget categories and their corresponding Budget objects.
     */
    public Map<String, Budget> getBudgets() {
        return this.budgets;
    }


    public void setBudget(String category, double amount) {
        if (category == "") { // Monthly budget setting
            budgets.put("Monthly", new Budget("Monthly", amount));
            System.out.println("Monthly budget set to: $" + amount);
        } else { // Category budget setting
            if (!budgets.containsKey(category)) {
                budgets.put(category, new Budget(category, amount));
            } else {
                budgets.get(category).setLimit(amount);
            }
            System.out.println("Budget for category " + category + " set to: $" + amount);
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
     *
     * @param index The index of the expense to delete.
     * @throws InvalidInputException if index is invalid.
     */
    public void deleteExpense(int index) throws InvalidInputException {
        if (!budgets.containsKey("Monthly")) {
            throw new InvalidInputException("No Monthly budget found.");
        }
        budgets.get("Monthly").deleteExpense(index);
        System.out.println("Expense removed successfully.");
        System.out.println("----------------------");
    }
}
