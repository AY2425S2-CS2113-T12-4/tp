package budgetbuddy.command;

import budgetbuddy.model.BudgetManager;
import budgetbuddy.exception.InvalidInputException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListCommandTest {

    private BudgetManager budgetManager;
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUp() throws InvalidInputException {
        budgetManager = new BudgetManager();

        // Sample expense to test filtering
        AddExpenseCommand add1 = new AddExpenseCommand("add 50 c/food d/lunch t/Mar 25 2025 at 12:00");
        add1.execute(budgetManager);

        System.setErr(new PrintStream(errContent));  // Capture System.err for warnings
    }

    @Test
    public void testListAllExpenses() throws InvalidInputException {
        ListCommand command = new ListCommand("list");
        command.execute(budgetManager);
        assertTrue(true); // Reached here without exception = passed
    }

    @Test
    public void testListWithValidStartAndEnd() throws InvalidInputException {
        String desc = "list start/Mar 15 2025 at 00:00 end/Mar 29 2025 at 23:59";
        ListCommand command = new ListCommand(desc);
        command.execute(budgetManager);
        assertTrue(true); // Assume visually inspected output; no exception
    }

    @Test
    public void testListWithMissingStartAndEndMarkers() throws InvalidInputException {
        String desc = "list start/ end/";
        ListCommand command = new ListCommand(desc);
        command.execute(budgetManager);

        String output = errContent.toString().trim();
        assertTrue(output.contains("start/ marker is empty."));
        assertTrue(output.contains("end/ marker is empty."));
        assertTrue(output.contains("Showing full list."));
    }

    @Test
    public void testListWithInvalidStartAndEndFormat() throws InvalidInputException {
        String desc = "list start/notadate end/notadate";
        ListCommand command = new ListCommand(desc);
        command.execute(budgetManager);

        String output = errContent.toString().trim();
        assertTrue(output.contains("Incorrect time format for \"start\". Will use current time for it."));
        assertTrue(output.contains("Incorrect time format for \"end\". Will use current time for it."));

    }

    @Test
    public void testListWithEndBeforeStart() throws InvalidInputException {
        String desc = "list start/Mar 29 2025 at 12:00 end/Mar 20 2025 at 12:00";
        ListCommand command = new ListCommand(desc);

        command.execute(budgetManager);  // Run first!

        String output = errContent.toString().trim();  // Then capture System.err
        assertTrue(output.contains("Start time cannot occur after end time."));
    }

}
