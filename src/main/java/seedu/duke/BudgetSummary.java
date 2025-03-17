package seedu.duke;
import java.util.Map;

/**
 * this class summarises the user budget by providing total expenses and spending limit.
 * this can summarise across multiple budgets
 */
public class BudgetSummary {

    private final BudgetManager budgetManager;

    /**
     * instantiate the class with a budgetManger to access its private variable "budgets"
     * @param budgetManager is taken in the Duke class (main class)
     */
    public BudgetSummary(BudgetManager budgetManager) {
        this.budgetManager = budgetManager;
    }

    /**
     * this is the method called to get the summary printed
     */
    public void summariseBudget(){
            Map<String, Budget> budgets = budgetManager.getBudgets(); // Access the budgets map

            System.out.println("Budget Summary:");
            for (String category : budgets.keySet()) {
                Budget budget = budgets.get(category);
                System.out.println("Category: " + category);
                System.out.println("Total Expenses: $" + budget.getTotalExpenses());
                System.out.println("Spending Limit: $" + budget.getLimit());
                System.out.println("----------------------");
            }
    }
}
