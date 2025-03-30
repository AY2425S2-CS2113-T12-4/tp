package budgetbuddy.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import budgetbuddy.model.BudgetManager;
import budgetbuddy.exception.InvalidInputException;
import budgetbuddy.model.Budget;
import budgetbuddy.model.Expense;
import java.util.List;
import java.util.Map;

public class AddExpenseCommandTest {

    private BudgetManager budgetManager;
    private AddExpenseCommand addExpenseCommand;

    @BeforeEach
    public void setUp() {
        budgetManager = new BudgetManager();
    }

    @Test
    public void testExecute_addExpenseSuccessfully() {
        String description = "add 50 c/ d/ lunch";
        addExpenseCommand = new AddExpenseCommand(description);

        try {
            addExpenseCommand.execute(budgetManager);

            Map<String, Budget> budgets = budgetManager.getBudgets();
            Budget monthlyBudget = budgets.get("Monthly");
            List<Expense> expenses = monthlyBudget.getExpenses();

            assertEquals(1, expenses.size(), "Expense should be added to the monthly budget");

            Expense addedExpense = expenses.get(0);
            assertEquals(50.0, addedExpense.getAmount(), "Expense amount should be 50");
            assertEquals("lunch", addedExpense.getDescription(), "Expense description should be " +
                    "'lunch'");
        } catch (InvalidInputException e) {
            System.out.println("Invalid input");
        }
    }

}
