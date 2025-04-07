package budgetbuddy.command;

import budgetbuddy.exception.InvalidInputException;
import budgetbuddy.model.BudgetManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class FindExpenseCommandTest {

    private BudgetManager budgetManager;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream originalSystemOut = System.out;

    @BeforeEach
    public void setUp() {
        // Initialize a fresh BudgetManager instance before each test
        budgetManager = new BudgetManager();

        // Redirect System.out to capture printed output for assertion
        System.setOut(new PrintStream(outputStreamCaptor));

        // Add sample expenses to test search functionality
        budgetManager.addExpenseToBudget("", 10.0, "Lunch", "");
        budgetManager.addExpenseToBudget("", 20.0, "Dinner", "");
        budgetManager.addExpenseToBudget("", 50.0, "Groceries", "");
    }

    @Test
    public void execute_keywordMatchesExpense_successfullyFindsExpense() throws InvalidInputException {
        // Test searching for an existing expense ("Lunch")
        FindExpenseCommand command = new FindExpenseCommand("find lunch");

        command.execute(budgetManager);

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Lunch"));
    }

    @Test
    public void execute_keywordDoesNotMatchExpense_showsNoMatchMessage() throws InvalidInputException {
        // Test searching for a non-existing expense ("breakfast")
        FindExpenseCommand command = new FindExpenseCommand("find breakfast");

        command.execute(budgetManager);

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("No matching expenses found"));
    }

    @Test
    public void execute_emptyKeyword_throwsInvalidInputException() {
        // Test behavior when the search keyword is empty
        FindExpenseCommand command = new FindExpenseCommand("find ");

        assertThrows(InvalidInputException.class, () -> {
            command.execute(budgetManager);
        });
    }

    @Test
    public void execute_caseInsensitiveKeyword_successfullyFindsExpense() throws InvalidInputException {
        // Test case-insensitive search functionality
        FindExpenseCommand command = new FindExpenseCommand("find LUNCH");

        command.execute(budgetManager);

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Lunch"));
    }

    @Test
    public void execute_keywordWithSpecialCharacters_successfullyFindsExpense() throws InvalidInputException {
        // Add an expense with special characters in the description
        budgetManager.addExpenseToBudget("", 15.0, "Lunch@Home", "");

        FindExpenseCommand command = new FindExpenseCommand("find Lunch@Home");

        command.execute(budgetManager);

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Lunch@Home"));
    }

    @Test
    public void  testIsExit_alwaysReturnsFalse() {
        // Test that FindExpenseCommand does not signal to exit the application
        FindExpenseCommand command = new FindExpenseCommand("find lunch");
        assertFalse(command.isExit());
    }
}
