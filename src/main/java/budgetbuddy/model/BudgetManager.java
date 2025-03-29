package budgetbuddy.model;

import budgetbuddy.exception.InvalidInputException;
import budgetbuddy.ui.Ui;

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
     * Constructs a BudgetManager with an initial "Overall" budget.
     * The "Overall" budget is created with a default limit of 0.
     */
    public BudgetManager() {
        this.budgets = new HashMap<>();
        this.alert = new Alert(); // Initialise alert system
        budgets.put("Overall", new Budget("Overall", 0));
        logger.info("BudgetManager initialized with Overall budget.");

        assert budgets != null : "Budgets HashMap should be initialized.";
        assert alert != null : "Alert system should be initialized.";
    }

    /**
     * Adds an expense to a specified budget category.
     * Always adds to the "Overall" budget. If a valid category exists, it is added there too;
     * otherwise, a prompt is shown to create the category.
     * Also checks if total expenses exceed the budget alert threshold.
     *
     * @param category    The budget category (e.g., "Food"), or empty for "Overall".
     * @param amount      The expense amount.
     * @param description A brief description of the expense.
     */
    public void addExpenseToBudget(String category, double amount, String description, String time) {
        assert amount > 0 : "Error: Expense amount should be positive.";

        try {
            //instantiate a new expense
            Expense expense = new Expense(amount, description, time);


            if (!budgets.containsKey("Overall")) {
                budgets.put("Overall", new Budget("Overall", 0));
                logger.warning("Overall budget was missing. Initialized a new Overall budget.");

                assert budgets.get("Overall") != null : "Overall budget should be initialized.";
            }
            budgets.get("Overall").addExpense(expense);

            if (category != null && !category.trim().isEmpty() && !category.equals("Overall")) {
                if (!budgets.containsKey(category)) {
                    System.out.println("Budget category '" + category + "' not found. Added to Overall Budget.");
                    logger.warning("Budget category '" + category + "' not found. Added to Overall Budget.");
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
     * Sets the budget for a given category or the "Overall" budget if no category is specified.
     * Creates a new budget if the category does not exist, otherwise updates its limit.
     *
     * @param category The budget category (e.g., "Food"), or empty for "Overall".
     * @param amount   The budget limit to set.
     */
    public void setBudget(String category, double amount) {
        try {
            if (Objects.equals(category, "")) {
                if (budgets.containsKey("Overall")) {
                    budgets.get("Overall").setLimit(amount);
                } else {
                    budgets.put("Overall", new Budget("Overall", amount));
                }
                System.out.println("Overall budget set to: $" + amount);
            } else {
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
        Budget overallBudget = budgets.get("Overall");
        return (overallBudget != null) ? overallBudget.getTotalExpenses() : 0.0;
    }

    /**
     * Displays all expenses categorized under Overall budget.
     */
    public void listAllExpenses() {
        assert budgets.containsKey("Overall") : "Error: 'Overall' budget should exist before listing expenses.";
        Budget overall = budgets.get("Overall");
        overall.printExpenses();
    }

    /**
     * Deletes an expense from the Overall Budget based on the index.
     * Also deletes the same expense from the corresponding category budget.
     *
     * @param index The index of the expense to delete.
     * @throws InvalidInputException if the index is invalid.
     */
    public void deleteExpense(int index) throws InvalidInputException {
        if (!budgets.containsKey("Overall")) {
            throw new InvalidInputException("No Overall budget found.");
        }

        Budget overallBudget = budgets.get("Overall");
        if (index < 1 || index > overallBudget.getExpenses().size()) {
            throw new InvalidInputException("Invalid index. Please provide a valid expense number.");
        }

        Expense expenseToDelete = overallBudget.getExpenses().get(overallBudget.getExpenses().size() - index);
        System.out.println("Expense deleted successfully from Overall Budget.");
        System.out.println("    " + expenseToDelete);

        overallBudget.deleteExpense(index);
        logger.info("Expense at index " + index + " deleted from Overall Budget.");

        for (Map.Entry<String, Budget> entry : budgets.entrySet()) {
            String category = entry.getKey();
            Budget categoryBudget = entry.getValue();

            if (category.equals("Overall")) {
                continue;
            }

            for (int i = 0; i < categoryBudget.getExpenses().size(); i++) {
                Expense categoryExpense = categoryBudget.getExpenses().get(i);
                if (categoryExpense.equals(expenseToDelete)) {
                    categoryBudget.getExpenses().remove(i);
                    System.out.println("Expense also deleted from category '" + category + "'.");
                    logger.info("Expense deleted from category '" + category + "'.");
                    break;
                }
            }
        }

        System.out.println("----------------------");
    }

    public void editExpense(int index, double amount, String description, String dateTime)
            throws InvalidInputException {
        if (!budgets.containsKey("Overall")) {
            throw new InvalidInputException("No Overall budget found.");
        }

        Budget overallBudget = budgets.get("Overall");
        if (index < 1 || index > overallBudget.getExpenses().size()) {
            throw new InvalidInputException("Invalid index. Please provide a valid expense number.");
        }

        Expense expenseToEdit = overallBudget.getExpenses().get(overallBudget.getExpenses().size() - index);
        expenseToEdit.editExpense(amount, description, dateTime);
        Ui.printExpenseEditedMessage(overallBudget.getExpenses(), index);
    }

    /**
     * Displays the budget allocation, amount spent, and remaining balance.
     * If a category is specified, it shows details for that category.
     * Otherwise, it shows the overall budget usage.
     *
     * @param category The budget category (e.g., "Food"), or empty for "Overall".
     */
    public void checkBudget(String category) {
        if (category == "" || category.trim().isEmpty()) {
            Budget overallBudget = budgets.get("Overall");
            assert overallBudget != null : "Error: 'Overall' budget should always exist.";

            double totalBudget = overallBudget.getLimit();
            double spent = overallBudget.getTotalExpenses();
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

    /**
     * Finds and displays expenses from the Overall budget that match the given keyword.
     *
     * @param keyword The keyword to search for in expense descriptions.
     */
    public void findExpense(String keyword) {
        assert keyword != null && !keyword.trim().isEmpty() : "Error: Keyword should not be null or empty.";

        if (!budgets.containsKey("Overall")) {
            System.out.println("No Overall budget found.");
            return;
        }

        Budget overallBudget = budgets.get("Overall");
        boolean found = false;

        System.out.println("------- Expenses Matching: '" + keyword + "' -------");
        for (int i = 0; i < overallBudget.getExpenses().size(); i++) {
            Expense expense = overallBudget.getExpenses().get(i);
            if (expense.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println((i + 1) + ". " + expense);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching expenses found for keyword: " + keyword);
        }

        System.out.println("-------------------------------------");
    }

}
