package budgetbuddy.command;

import budgetbuddy.model.BudgetManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HelpCommandTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream originalSystemOut = System.out;

    @BeforeEach
    public void setUp() {
        // Redirect System.out to capture the output for testing
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void testExecute_validHelpCommand_displaysHelpMessage() {
        // Create a HelpCommand with "help" input
        HelpCommand command = new HelpCommand("help");

        command.execute(new BudgetManager());

        // Assert that the output contains expected help instructions
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Available Commands:"));
        assertTrue(output.contains("Add Expense:"));
        assertTrue(output.contains("Delete Expense:"));
        assertTrue(output.contains("Edit Budget:"));
    }

    @Test
    public void testIsExit_alwaysReturnsFalse() {
        HelpCommand command = new HelpCommand("help");
        assertFalse(command.isExit());
    }
}
