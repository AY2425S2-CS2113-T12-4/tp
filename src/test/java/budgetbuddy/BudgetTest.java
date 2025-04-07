package budgetbuddy;

import budgetbuddy.exception.InvalidInputException;
import budgetbuddy.model.Budget;
import budgetbuddy.model.Expense;
import budgetbuddy.ui.Ui;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BudgetTest {

    private Budget budget;
    private Expense expense1;
    private Expense expense2;

    @BeforeEach
    void setUp() {
        budget = new Budget("Food", 100.0);
        expense1 = new Expense(20.0, "Groceries");
        expense2 = new Expense(30.0, "Dinner");
    }

    @Test
    void testValidBudgetCreation() {
        assertEquals("Food", budget.getCategory());
        assertEquals(100.0, budget.getLimit(), 0.01);
    }

    @Test
    void testConstructor_nullCategory_exceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> new Budget(null, 100.0));
    }

    @Test
    void testConstructor_negativeLimit_exceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> new Budget("Food", -10.0));
    }

    @Test
    void testValidSetCategory() {
        budget.setCategory("Transport");
        assertEquals("Transport", budget.getCategory());
    }

    @Test
    void testSetCategory_emptyCategory_exceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> budget.setCategory(""));
    }

    @Test
    void testAddExpense() {
        budget.addExpense(expense1);
        assertEquals(1, budget.getExpenses().size());
        assertEquals(expense1, budget.getExpenses().get(0));
    }

    @Test
    void testGetTotalExpenses() {
        budget.addExpense(expense1);
        budget.addExpense(expense2);
        assertEquals(50.0, budget.getTotalExpenses(), 0.01);
    }

    @Test
    void testValidSetLimit() {
        budget.setLimit(200.0);
        assertEquals(200.0, budget.getLimit(), 0.01);
    }

    @Test
    void testSetLimit_negativeLimit_exceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> budget.setLimit(-50.0));
    }

    @Test
    void testValidIndexDeleteExpense() throws InvalidInputException {
        budget.addExpense(expense1);
        budget.addExpense(expense2);
        budget.deleteExpense(1); // Deletes the most recent expense (expense2)
        assertEquals(1, budget.getExpenses().size());
        assertEquals(expense1, budget.getExpenses().get(0));
    }

    @Test
    void testDeleteExpense_invalidIndex_exceptionThrown() {
        budget.addExpense(expense1);
        assertThrows(InvalidInputException.class, () -> budget.deleteExpense(2));
    }

    @Test
    void testGetRemainingBudget_limitSet_noOverflow() throws IllegalStateException, ArithmeticException {
        budget.addExpense(expense1);
        budget.addExpense(expense2);
        assertEquals(50.0, budget.getRemainingBudget(), 0.01);
    }

    @Test
    void testGetRemainingBudget_noLimit_returnsZero() throws IllegalStateException, ArithmeticException {
        Budget noLimitBudget = new Budget("Entertainment", 0.0);
        assertEquals(0.0, noLimitBudget.getRemainingBudget(), 0.01);
    }


}