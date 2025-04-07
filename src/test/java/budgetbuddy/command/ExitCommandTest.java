package budgetbuddy.command;

import budgetbuddy.model.BudgetManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExitCommandTest {

    private BudgetManager budgetManager;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        budgetManager = new BudgetManager();

        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testConstructor_validDescription_noExceptionThrown() {
        assertDoesNotThrow(() -> new ExitCommand("exit"));
    }

    @Test
    void testExecute_printsGoodbyeMessage() {
        ExitCommand command = new ExitCommand("exit");

        command.execute(budgetManager);

        String output = outputStream.toString().trim();
        System.setOut(System.out);

        assertTrue(output.contains("Goodbye!"), "The goodbye message should be printed.");
    }

    @Test
    void testIsExit_alwaysReturnsTrue() {
        ExitCommand command = new ExitCommand("exit");

        assertTrue(command.isExit(), "isExit should always return true for ExitCommand.");
    }
}
