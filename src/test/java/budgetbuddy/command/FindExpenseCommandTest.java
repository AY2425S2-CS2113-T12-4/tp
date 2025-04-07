package budgetbuddy.command;

import budgetbuddy.exception.InvalidInputException;
import budgetbuddy.model.BudgetManager;
import budgetbuddy.model.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FindExpenseCommandTest {

    private BudgetManager budgetManager;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream originalSystemOut = System.out;

    @BeforeEach
    public void setUp() {
        budgetManager = new BudgetManager();
        System.setOut(new PrintStream(outputStreamCaptor));

        // Add some sample expenses
        budgetManager.addExpenseToBudget("", 10.0, "Lunch", "");
        budgetManager.addExpenseToBudget("", 20.0, "Dinner", "");
        budgetManager.addExpenseToBudget("", 50.0, "Groceries", "");
    }

    @Test
    public void testFindExpenseSuccess() throws InvalidInputException {
        FindExpenseCommand command = new FindExpenseCommand("find lunch");

        command.execute(budgetManager);

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Lunch"));
    }

    @Test
    public void testFindExpenseNoMatch() throws InvalidInputException {
        FindExpenseCommand command = new FindExpenseCommand("find breakfast");

        command.execute(budgetManager);

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("No matching expenses found"));
    }

    @Test
    public void testFindExpenseEmptyKeyword() throws InvalidInputException {
        FindExpenseCommand command = new FindExpenseCommand("find ");

        assertThrows(InvalidInputException.class, () -> {
            command.execute(budgetManager);
        });
    }

    @Test
    public void testFindExpenseCaseInsensitive() throws InvalidInputException {
        FindExpenseCommand command = new FindExpenseCommand("find LUNCH");

        command.execute(budgetManager);

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Lunch"));
    }

    @Test
    public void testFindExpenseSpecialCharacters() throws InvalidInputException {
        budgetManager.addExpenseToBudget("", 15.0, "Lunch@Home", "");
        FindExpenseCommand command = new FindExpenseCommand("find Lunch@Home");

        command.execute(budgetManager);

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Lunch@Home"));
    }

    @Test
    public void testIsExit() {
        FindExpenseCommand command = new FindExpenseCommand("find lunch");
        assertFalse(command.isExit());
    }
}