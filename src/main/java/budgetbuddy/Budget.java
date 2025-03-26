package budgetbuddy;

import budgetbuddy.exception.InvalidInputException;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Budget that tracks expenses within a specific category.
 * The budget can have an optional spending limit.
 */
public class Budget {
    private final String category;
    private double limit; //Optional
    private final ArrayList<Expense> expenses;

    /**
     * Constructs a Budget object with the given category and spending limit.
     *
     * @param category The name of the budget category (e.g., "Food", "Transport").
     * @param limit The spending limit for the budget.
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
     * Adds an expense to the list of expenses for this budget.
     *
     * @param expense The expense to add to this budget.
     */
    public void addExpense (Expense expense) {
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
            System.out.println("No expenses recorded.");
            return;
        }
        for (int i = expenses.size() - 1; i >= 0 ; i--) {
            System.out.println((expenses.size() - i) + ". " + expenses.get(i));
        }
    }

    public void deleteExpense(int index) throws InvalidInputException {
        if (index < 1 || index > expenses.size()) {
            throw new InvalidInputException("Invalid index. Please provide a valid expense number.");
        }
        System.out.println("Expense deleted successfully.");
        System.out.println("    " + expenses.get(expenses.size() - index));
        expenses.remove(expenses.size() - index);
    }

    public List<Expense> getExpenses() {
        return expenses;
    }


}
