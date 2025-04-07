package budgetbuddy.command;

import budgetbuddy.model.BudgetManager;
import budgetbuddy.exception.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SetBudgetCommandTest {

    private BudgetManager budgetManager;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream originalSystemOut = System.out;

    @BeforeEach
    public void setUp() {
        budgetManager = new BudgetManager();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void testSetBudgetSuccess() throws InvalidInputException {
        SetBudgetCommand command = new SetBudgetCommand("set-budget c/Food 100");

        command.execute(budgetManager);

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Budget for Food set to: $100.0"));
    }

    @Test
    public void testSetBudgetInvalidCategory() throws InvalidInputException {
        SetBudgetCommand command = new SetBudgetCommand("set-budget c/ 100");

        command.execute(budgetManager);

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Overall Budget set to: $100.0"));
    }

    @Test
    public void testSetBudgetNegativeAmount() {
        SetBudgetCommand command = new SetBudgetCommand("Food -50");

        assertThrows(InvalidInputException.class, () -> {
            command.execute(budgetManager);
        });
    }


    @Test
    public void testSetBudgetLargeAmount() throws InvalidInputException {
        SetBudgetCommand command = new SetBudgetCommand("set-budget c/Food 1000000000");

        assertThrows(InvalidInputException.class, () -> {
            command.execute(budgetManager);
        });
    }

    @Test
    public void testSetBudgetNonNumericAmount() {
        SetBudgetCommand command = new SetBudgetCommand("set-budget c/Food abc");

        assertThrows(InvalidInputException.class, () -> {
            command.execute(budgetManager);
        });
    }

    @Test
    public void testSetBudgetSpecialCharactersInCategory() throws InvalidInputException {
        SetBudgetCommand command = new SetBudgetCommand("set-budget c/Food@Home 200");

        command.execute(budgetManager);

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Budget for Food@Home set to: $200.0"));
    }
}
