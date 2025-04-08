package budgetbuddy.model;

import budgetbuddy.exception.InvalidInputException;
import budgetbuddy.ui.Ui;

import java.util.ArrayList;
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
            // Instantiate a new expense
            Expense expense = new Expense(amount, description, time);
            boolean addedToCategory = false;
            String message = "";

            // Handle Overall budget
            if (!budgets.containsKey("Overall")) {
                budgets.put("Overall", new Budget("Overall", 0));
                logger.warning("Overall budget was missing. Initialized a new Overall budget.");
                assert budgets.get("Overall") != null : "Overall budget should be initialized.";
            }
            budgets.get("Overall").addExpense(expense);

            // Handle specific category if provided
            if (category != null && !category.trim().isEmpty() && !category.equals("Overall")) {
                if (!budgets.containsKey(category)) {
                    message = "Budget category '" + category + "' not found. Added to Overall Budget.";
                    logger.warning(message);
                } else {
                    budgets.get(category).addExpense(expense);
                    addedToCategory = true;
                }
                logger.info("Expense Added: " + expense);
            }

            // Call UI with all relevant information
            Ui.printAddExpense(expense, category, addedToCategory, message);

            checkBudgetAlert();
            checkBudgetLimit("Overall");
            if (category != null) {
                assert category != null;
                String trimmedCategory = category.trim();
                checkBudgetLimit(trimmedCategory);
            }
        } catch (IllegalArgumentException e) {
            Ui.printError(e.getMessage()); // Move error printing to UI as well
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
        checkBudgetAlert();
    }

    /**
     * Checks if total expenses exceed the alert limit.
     */
    public void checkBudgetAlert() {
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
                Ui.printSetOverallBudget(amount);
                checkBudgetAlert();
                checkBudgetLimit("Overall");
            } else {
                if (!budgets.containsKey(category)) {
                    budgets.put(category, new Budget(category, amount));
                    logger.info("Created new budget category: " + category + " with limit $" + amount);
                } else {
                    budgets.get(category).setLimit(amount);
                    logger.info("Updated budget for category " + category + " to: $" + amount);
                }
                Ui.printSetCategoryBudget(category, amount);
                checkBudgetLimit(category);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Checks if a specific category exists in the budget manager.
     *
     * @param category The name of the budget category to check.
     * @return true if the category exists, false otherwise.
     */
    public boolean categoryExists(String category) {
        return budgets.containsKey(category);
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
     * Displays all expenses categorized under Overall budget in a particular
     * date and time range
     */
    public void listPartialExpenses(String start, String end) {
        assert budgets.containsKey("Overall") : "Error: 'Overall' budget should exist before listing expenses.";
        Budget overall = budgets.get("Overall");
        overall.printExpenses(start, end);
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

        ArrayList<Expense> expenses = overallBudget.getExpenses();
        Expense expenseToDelete = expenses.get(overallBudget.getExpenses().size() - index);
        Ui.printDeleteExpense(expenses, index);

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
                    Ui.printDeleteExpenseCategory(category);
                    logger.info("Expense deleted from category '" + category + "'.");
                    break;
                }
            }
        }
    }

    /**
     * Edits an existing expense in the Overall budget.
     * <p>
     * This method allows users to modify an expense at a specific index in the Overall budget.
     * It updates the expense's amount, description, and date/time, provided they meet the necessary conditions.
     * If no Overall budget is found, or the index is invalid, an exception is thrown.
     * The method will then call the `editExpense` method to update the selected expense and print a confirmation
     * message.
     *
     * @param index       The index of the expense to edit, where the first expense in the list is 1.
     *                    The index must be a valid number between 1 and the size of the expense list.
     * @param amount      A string representing the new amount of the expense. If the string is valid and positive,
     *                    it updates the amount of the expense.
     * @param description A string representing the new description of the expense. If the string is not empty,
     *                    it updates the description.
     * @param dateTime    A string representing the new date and time of the expense. If the string is valid,
     *                    it updates the date/time; otherwise, it remains unchanged.
     * @throws InvalidInputException If no Overall budget is found or if the provided index is invalid.
     */
    public void editExpense(int index, String amount, String description, String dateTime)
            throws InvalidInputException {
        if (!budgets.containsKey("Overall")) {
            throw new InvalidInputException("No Overall budget found.");
        }

        Budget overallBudget = budgets.get("Overall");
        if (index < 1 || index > overallBudget.getExpenses().size()) {
            throw new InvalidInputException("Invalid index. Please provide a valid expense number.");
        }

        if (amount.isEmpty() && description.isEmpty() && dateTime.isEmpty()) {
            throw  new InvalidInputException("Invalid input. Please provide at least one of: amount, description, " +
                    "or date, to update.");
        }

        Expense expenseToEdit = overallBudget.getExpenses().get(overallBudget.getExpenses().size() - index);
        String category = "";
        for (Map.Entry<String, Budget> entry : budgets.entrySet()) {
            category = entry.getKey();
            Budget categoryBudget = entry.getValue();

            if (category.equals("Overall")) {
                continue;
            }

            for (int i = 0; i < categoryBudget.getExpenses().size(); i++) {
                Expense categoryExpense = categoryBudget.getExpenses().get(i);
                if (categoryExpense.equals(expenseToEdit)) {
                    break;
                }
            }
        }

        expenseToEdit.editExpense(amount, description, dateTime);
        Ui.printExpenseEditedMessage(overallBudget.getExpenses(), index);
        checkBudgetAlert();
        checkBudgetLimit("Overall");
        checkBudgetLimit(category);
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
            Ui.printCheckBudget("", totalBudget, spent, remaining);
        } else {
            if (!budgets.containsKey(category)) {
                Ui.printBudgetNotFound(category);
                logger.warning("Budget category '" + category + "' not found.");
                return;
            }
            assert budgets.get(category) != null : "Category budget should exist when checking.";

            Budget categoryBudget = budgets.get(category);
            double totalBudget = categoryBudget.getLimit();
            double spent = categoryBudget.getTotalExpenses();
            double remaining = Math.max(0, totalBudget - spent);
            Ui.printCheckBudget(category, totalBudget, spent, remaining);
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

        Budget overallBudget = budgets.get("Overall");
        boolean found = false;


        Ui.printSearchHeader(keyword);
        for (int i = 0; i < overallBudget.getExpenses().size(); i++) {
            Expense expense = overallBudget.getExpenses().get(i);
            if (expense.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                Ui.printMatchingExpense(i + 1, expense);
                found = true;
            }
        }

        if (!found) {
            Ui.printNoMatchesFound(keyword);
            return;
        }

        Ui.printSeparator();
    }

    /**
     * Edits an existing budget by updating its name and/or limit.
     *
     * @param currentName The current name of the budget to edit.
     * @param newAmount   The new budget limit to set.
     * @param newName     The new name for the budget.
     * @throws InvalidInputException if the specified budget does not exist.
     */
    public void editBudget(String currentName, double newAmount, String newName) throws InvalidInputException {
        if (!budgets.containsKey(currentName)) {
            throw new InvalidInputException("Budget '" + currentName + "' not found.");
        }

        Budget budgetToEdit = budgets.get(currentName);

        if (currentName.equalsIgnoreCase("Overall") && !newName.isEmpty() && !newName.equalsIgnoreCase("Overall")) {
            throw new InvalidInputException("The 'Overall' budget cannot be renamed.");
        }

        // Update the budget limit if specified
        if (newAmount >= 0) {
            budgetToEdit.setLimit(newAmount);
            Ui.printUpdateBudgetLimit(currentName, newAmount);
            checkBudgetLimit(currentName);
        }

        // Rename the budget if a new name is provided and different from the current one
        if (newName != null && !newName.equals(currentName) && !newName.isEmpty()) {
            budgets.remove(currentName);
            budgetToEdit.setCategory(newName);
            budgets.put(newName, budgetToEdit);
            Ui.printRenamedBudget(currentName, newName);
        }

        logger.info("Budget edited: Name - " + newName + ", Limit - " + newAmount);
    }

    /**
     * Removes the current budget alert from the system.
     * <p>
     * This method calls {@code removeAlert()} on the {@code alert} object to clear any existing
     * budget-related alerts and logs the removal action.
     */
    public void removeBudgetAlert() {
        alert.removeAlert();
        logger.info("Budget alert removed.");
    }

    /**
     * Checks whether the budget for a specified category has reached or exceeded its limit.
     * <p>
     * If the provided category is not empty and exists in the {@code budgets} map,
     * this method calls {@code checkLimit()} on the corresponding {@code Budget} instance
     * to trigger any necessary alerts or warnings.
     *
     * @param category the name of the budget category to check
     */
    public void checkBudgetLimit(String category) {
        if (category == null || category.isEmpty()) {
            return;
        }
        Budget budget = budgets.get(category);
        if (budget == null) {
            return;
        }
        budget.checkLimit();
    }
}
