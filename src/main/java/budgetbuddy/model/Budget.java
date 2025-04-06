package budgetbuddy.model;

import budgetbuddy.exception.InvalidInputException;
import budgetbuddy.ui.Ui;

import java.util.ArrayList;

/**
 * Represents a Budget that tracks expenses within a specific category.
 * The budget can have an optional spending limit.
 */
public class Budget {
    private String category;
    private double limit; //Optional
    private final ArrayList<Expense> expenses;

    /**
     * Constructs a Budget object with the given category and spending limit.
     *
     * @param category The name of the budget category (e.g., "Food", "Transport").
     * @param limit    The spending limit for the budget.
     */
    public Budget(String category, double limit) {

        if (category == null) {
            throw new IllegalArgumentException("Category cannot be empty.");
        }
        if (limit < 0) {
            throw new IllegalArgumentException("Limit cannot be negative.");
        }

        this.category = category;
        this.limit = limit;
        this.expenses = new ArrayList<>();
    }

    /**
     * Sets a new category name for the budget.
     *
     * @param category The new category name.
     */
    public void setCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be empty.");
        }
        this.category = category;
    }

    /**
     * Gets the category name of the budget.
     *
     * @return The current category name.
     */
    public String getCategory() {
        return this.category;
    }

    /**
     * Adds an expense to the list of expenses for this budget.
     *
     * @param expense The expense to add to this budget.
     */
    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    /**
     * Calculates the total amount of all expenses in this budget.
     *
     * @return The total expenses for the budget.
     */
    public double getTotalExpenses() {
        return expenses.stream().mapToDouble(e -> e.amount).sum();
    }

    /**
     * Sets a new spending limit for this budget.
     *
     * @param amount The new spending limit for this budget.
     */
    public void setLimit(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Budget limit cannot be negative.");
        }
        this.limit = amount;

    }

    /**
     * Gets the spending limit of this budget.
     *
     * @return The current spending limit for this budget.
     */
    public double getLimit() {
        return this.limit;
    }

    /**
     * Prints all expenses under this budget in reverse order (most recent first).
     */
    public void printExpenses() {
        if (expenses.isEmpty()) {
            Ui.printNoExpense();
        } else {
            Ui.printExpensesList(expenses);
        }
    }

    /**
     * Prints all expenses under this budget in reverse order (most recent first) in a particular
     * date and time range
     */

    public void printExpenses(String start, String end) {

        if (expenses.isEmpty()) {
            Ui.printNoExpense();

        } else {
            Ui.printExpensesList(expenses, start, end);
        }
    }

    /**
     * deletes an expense.
     *
     * @param index of the expense in list
     * @throws InvalidInputException when wrong index provided
     */
    public void deleteExpense(int index) throws InvalidInputException {
        if (index < 1 || index > expenses.size()) {
            throw new InvalidInputException("Invalid index. Please provide a valid expense number.");
        }
        expenses.remove(expenses.size() - index);
    }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    /**
     * Calculates the remaining budget by subtracting total expenses from the budget limit.
     *
     * @return The remaining budget amount as a double
     * @throws IllegalStateException if no budget has been set (limit = 0)
     */
    public double getRemainingBudget() throws IllegalStateException, ArithmeticException {
        // Check if budget is set
        if (limit <= 0) {
            return 0.0;
        }

        double totalExpenses = getTotalExpenses();
        double remaining = limit - totalExpenses;

        // Check for arithmetic overflow
        if (Double.isInfinite(remaining)) {
            throw new ArithmeticException("Budget calculation overflow - amounts too large");
        }

        return remaining;
    }

    /**
     * Checks whether the total expenses for this budget category have reached or exceeded the set limit.
     * <p>
     * If a limit is set (non-zero), this method compares the total expenses against the limit:
     * <ul>
     *     <li>If the total expenses exceed the limit, a warning message is printed via {@code Ui.printBudgetExceeded}.</li>
     *     <li>If the total expenses exactly match the limit, a notification is printed via {@code Ui.printBudgetReached}.</li>
     *     <li>If the limit is zero, no check or output is performed.</li>
     * </ul>
     */
    public void checkLimit() {
        double totalExpenses = this.getTotalExpenses();
        if (this.limit != 0) {
            if (totalExpenses > limit) {
                Ui.printBudgetExceeded(totalExpenses, limit, category);
            } else if (totalExpenses == limit) {
                Ui.printBudgetReached(totalExpenses, limit, category);
            }
        }
    }
}
