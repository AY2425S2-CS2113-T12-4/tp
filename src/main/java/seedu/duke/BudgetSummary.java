package seedu.duke;
import java.util.Map;


public class BudgetSummary {

    private final BudgetManager budgetManager;


    public BudgetSummary(BudgetManager budgetManager) {
        this.budgetManager = budgetManager;
    }


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
