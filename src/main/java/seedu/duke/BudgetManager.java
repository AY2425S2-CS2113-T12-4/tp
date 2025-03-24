package seedu.duke;

import seedu.duke.exception.InvalidInputException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The BudgetManager class is responsible for managing multiple budgets.
 * It allows tracking expenses and setting alerts when expenses exceed a threshold.
 */
public class BudgetManager {
    private static final Logger logger = Logger.getLogger(BudgetManager.class.getName());
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
        logger.info("BudgetManager initialized with Monthly budget.");

        assert budgets != null : "Budgets HashMap should be initialized.";
        assert alert != null : "Alert system should be initialized.";
    }

    /**
     * Adds an expense to a specified budget category.
     * Always adds to the "Monthly" budget. If a valid category exists, it is added there too;
     * otherwise, a prompt is shown to create the category.
     * Also checks if total expenses exceed the budget alert threshold.
     *
     * @param category    The budget category (e.g., "Food"), or empty for "Monthly".
     * @param amount      The expense amount.
     * @param description A brief description of the expense.
     */
    public void addExpenseToBudget(String category, double amount, String description) {
        assert amount > 0 : "Error: Expense amount should be positive.";

        try {
            Expense expense = new Expense(amount, description);


            if (!budgets.containsKey("Monthly")) {
                budgets.put("Monthly", new Budget("Monthly", 0));
                logger.warning("Monthly budget was missing. Initialized a new Monthly budget.");
            }
            assert budgets.get("Monthly") != null : "Monthly budget should be initialized.";
            budgets.get("Monthly").addExpense(expense);

            if (category != null && !category.trim().isEmpty()) {
                if (!budgets.containsKey(category)) {
                    System.out.println("Budget category '" + category + "' not found. Added to Monthly Budget.");
                    logger.warning("Budget category '" + category + "' not found. Added to Monthly Budget.");
                } else {
                    budgets.get(category).addExpense(expense);
                }
                logger.info("Expense Added: " + expense);
            }

            System.out.println("Expense Added: " + expense);

            checkBudgetAlert();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Sets a budget alert at the specified amount.
     * If total expenses exceed this limit, a notification will be triggered.
     *
     * @param amount The alert threshold. If 0, the alert is removed.
     */
    public void setBudgetAlert(double amount) {
        assert amount >= 0 : "Error: Budget amount should not be negative.";
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

    /**
     * Sets the budget for a given category or the "Monthly" budget if no category is specified.
     * Creates a new budget if the category does not exist, otherwise updates its limit.
     *
     * @param category The budget category (e.g., "Food"), or empty for "Monthly".
     * @param amount   The budget limit to set.
     */
    public void setBudget(String category, double amount) {
        assert amount >= 0 : "Error: Budget amount should not be negative.";

        try {
            if (Objects.equals(category, "")) { // Monthly budget setting
                budgets.put("Monthly", new Budget("Monthly", amount));
                System.out.println("Monthly budget set to: $" + amount);
            } else { // Category budget setting
                if (!budgets.containsKey(category)) {
                    budgets.put(category, new Budget(category, amount));
                    logger.info("Created new budget category: " + category + " with limit $" + amount);
                } else {
                    budgets.get(category).setLimit(amount);
                    logger.info("Updated budget for category " + category + " to: $" + amount);
                }
                System.out.println("Budget for category " + category + " set to: $" + amount);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Calculates the total expenses across all budgets.
     *
     * @return The sum of all expenses.
     */
    public double getTotalExpenses() {
        Budget monthlyBudget = budgets.get("Monthly");
        return (monthlyBudget != null) ? monthlyBudget.getTotalExpenses() : 0.0;
    }

    /**
     * Displays all expenses categorized under Monthly budget.
     */
    public void listAllExpenses() {
        assert budgets.containsKey("Monthly") : "Error: 'Monthly' budget should exist before listing expenses.";
        Budget monthly = budgets.get("Monthly");
        monthly.printExpenses();
    }

    /**
     * Deletes an expense from the Monthly Budget based on index.
     *
     * @param index The index of the expense to delete.
     * @throws InvalidInputException if index is invalid.
     */
    public void deleteExpense(int index) throws InvalidInputException {
        assert index >= 0 : "Expense index should not be negative.";
        if (!budgets.containsKey("Monthly")) {
            throw new InvalidInputException("No Monthly budget found.");
        }
        budgets.get("Monthly").deleteExpense(index);
        logger.info("Expense at index " + index + " deleted from Monthly Budget.");
        System.out.println("----------------------");
    }

    /**
     * Displays the budget allocation, amount spent, and remaining balance.
     * If a category is specified, it shows details for that category.
     * Otherwise, it shows the overall budget usage for the current month.
     *
     * @param category The budget category (e.g., "Food"), or empty for "Monthly".
     */
    public void checkBudget(String category) {
        if (category == "" || category.trim().isEmpty()) {
            Budget monthlyBudget = budgets.get("Monthly");
            assert monthlyBudget != null : "Error: 'Monthly' budget should always exist.";

            double totalBudget = monthlyBudget.getLimit();
            double spent = monthlyBudget.getTotalExpenses();
            double remaining = Math.max(0, totalBudget - spent);

            System.out.println("===== Overall Budget Usage =====");
            System.out.println("Total Budget: $" + totalBudget);
            System.out.println("Spent: $" + spent);
            System.out.println("Remaining: $" + remaining);
            System.out.println("===============================");
        } else {
            if (!budgets.containsKey(category)) {
                System.out.println("Budget category '" + category + "' not found.");
                logger.warning("Budget category '" + category + "' not found.");
                return;
            }
            assert budgets.get(category) != null : "Category budget should exist when checking.";

            Budget categoryBudget = budgets.get(category);
            double totalBudget = categoryBudget.getLimit();
            double spent = categoryBudget.getTotalExpenses();
            double remaining = Math.max(0, totalBudget - spent);

            System.out.println("===== Budget for " + category + " =====");
            System.out.println("Total Budget: $" + totalBudget);
            System.out.println("Spent: $" + spent);
            System.out.println("Remaining: $" + remaining);
            System.out.println("===============================");
        }
    }

    public Alert getBudgetAlert() {
        return alert;
    }
}
