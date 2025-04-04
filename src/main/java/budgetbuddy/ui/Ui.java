package budgetbuddy.ui;

import budgetbuddy.model.Budget;
import budgetbuddy.model.Expense;
import budgetbuddy.parser.DateTimeParser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

/**
 * The Ui class provides methods for displaying messages to the user.
 *
 * <p>This class is responsible for printing messages for various actions such as welcoming the user,
 * displaying commands, showing expenses, and providing budget summaries.</p>
 */
public class Ui {
    private static final String SEPARATOR = "___________________________________________";  // Separator for formatting
    private Scanner scanner;  // Scanner to capture user input

    /**
     * Prints a separator line for better output formatting.
     */
    public static void printSeparator() {
        System.out.println(SEPARATOR);
    }

    /**
     * Prints a welcome message to the user when the program starts.
     *
     * <p>This method displays an ASCII art logo and a greeting message to introduce the program
     * and prompt the user for input.</p>
     */
    public void printWelcomeMessage() {
        System.out.println("|                     |");
        System.out.println("|  $$$$       $$$$    |");
        System.out.println("| $    $     $    $   |");
        System.out.println("| $    $     $    $   |");
        System.out.println("|  $$$$       $$$$    |");
        System.out.println("|_____________________|");
        System.out.println("Hello! I'm your Budget Buddy");
        System.out.println("What can I do for you?");
        System.out.println("Input 'help' if you want to know what I can do!!");
        printSeparator();
    }

    /**
     * Prints a goodbye message when the program exits.
     *
     * <p>This method shows a farewell message to the user.</p>
     */
    public static void printGoodbyeMessage() {
        printSeparator();
        System.out.println("Thank you for using Budget Buddy.");
        System.out.println("Goodbye!");
    }

    /**
     * Prints a help message that lists available commands and their formats.
     *
     * <p>This method provides a summary of all commands that the user can enter, along with
     * their respective syntax and example usages.</p>
     */
    public static void printHelpMessage() {
        printSeparator();
        System.out.println("Available Commands:");

        System.out.println("\nAdd Expense: add");
        System.out.println("Format: add AMOUNT c/ CATEGORY d/ DESCRIPTION t/ TIME <MMM dd yyyy 'at' hh:mm");
        System.out.println("Please Note: id dateTime format is incorrect, current system time would be used instead");
        System.out.println("Examples: add 15.50 c/Food d/Lunch t/Oct 05 2025 at 12:30, " +
                "\n"+"       add 40 c/Transport d/Taxi Ride t/Oct 10 2025 at 14:35");

        System.out.println("\nDelete Expense: delete");
        System.out.println("Format: delete INDEX");
        System.out.println("Examples: delete 2, delete 5");

        System.out.println("\nView Expenses: list");
        System.out.println("Format: list start/ START_TIME end/ END_TIME");
        System.out.println("Please Note: START_TIME and END_TIME are both optional");
        System.out.println("Example: list");

        System.out.println("\nSet Budget: set-budget");
        System.out.println("Format: set-budget AMOUNT | set-budget c/CATEGORY AMOUNT");
        System.out.println("Examples: set-budget 1000, set-budget c/Food 300");

        System.out.println("\nCheck Budget: check-budget");
        System.out.println("Format: check-budget [c/CATEGORY]");
        System.out.println("Examples: check-budget, check-budget c/Groceries");

        System.out.println("\nSet Budget Alert: alert");
        System.out.println("Format: alert AMOUNT");
        System.out.println("Examples: alert 500, alert 0");

        System.out.println("\nView Spending Summary: summary");
        System.out.println("Format: summary [c/CATEGORY]");
        System.out.println("Examples: summary, summary c/Food");

        printSeparator();
    }

    /**
     * Prints an error message when the budget alert amount is invalid.
     *
     * <p>This method alerts the user if the alert amount entered is invalid (e.g., negative number).</p>
     */
    public static void printInvalidBudgetAlertWarning() {
        printSeparator();
        System.out.println("Error: Alert amount must be a non-negative number.");
        printSeparator();
    }

    /**
     * Prints a message confirming the setting of a budget alert.
     *
     * @param alertAmount The alert threshold amount.
     * @param isEdit A boolean indicating if this is an edit to an existing alert.
     */
    public static void printSetBudgetAlert(double alertAmount, boolean isEdit) {
        printSeparator();
        if (isEdit) {
            System.out.println("Alert amount updated to $" + alertAmount);
        } else {
            System.out.println("Budget alert set at $" + alertAmount +
                    ". You will be notified if expenses exceed this amount.");
        }
        printSeparator();
    }

    /**
     * Prints a message confirming the removal of a budget alert.
     *
     * <p>This method notifies the user that the budget alert has been successfully removed.</p>
     */
    public static void printRemoveBudgetAlert() {
        printSeparator();
        System.out.println("Budget alert has been removed.");
        printSeparator();
    }

    /**
     * Prints a warning if the total expenses exceed the budget alert threshold.
     *
     * @param totalExpenses The total expenses incurred by the user.
     * @param alertAmount The threshold set for the budget alert.
     */
    public static void printCheckAlert(double totalExpenses, double alertAmount) {
        printSeparator();
        System.out.println("Warning: Your total expenses ($" + totalExpenses +
                ") have exceeded the alert limit of $" + alertAmount);
        printSeparator();
    }

    /**
     * Prints a message when no expenses have been recorded yet.
     *
     * <p>This method informs the user if there are no expenses in the system.</p>
     */
    public static void printNoExpense() {
        printSeparator();
        System.out.println("No expenses recorded.");
        printSeparator();
    }

    /**
     * Prints a list of all recorded expenses.
     *
     * @param expenses A list of expenses to be displayed.
     */
    public static void printExpensesList(ArrayList expenses) {
        printSeparator();
        System.out.println("Expense List:");
        for (int i = expenses.size() - 1; i >= 0; i--) {
            System.out.println((expenses.size() - i) + ". " + expenses.get(i));
        }
        printSeparator();
    }

    /**
     * Prints a list of all recorded expenses in a date and time range.
     *
     * @param expenses A list of expenses to be displayed.
     */

    public static void printExpensesList(ArrayList expenses, String start, String end) {
        printSeparator();

        boolean bypassStart = start.isEmpty();
        //if no start date provided
        boolean bypassEnd = end.isEmpty();
        //if no end date provided

        //boolean happensAfterStart = false;
        //if recorded expense happens after user provided start date
        //boolean happensBeforeEnd = false;
        //if recorded expense happens before user provided end date


        LocalDateTime startDate = null;
        LocalDateTime endDate = null;


        try {
            if (!bypassStart) {
                startDate = DateTimeParser.parseOrDefault(start, false);
            }
            if (!bypassEnd) {
                endDate = DateTimeParser.parseOrDefault(end, false);
            }
        } catch (Exception e) {
            System.out.println("Error parsing expenses list: " + e.getMessage());
            //throw new RuntimeException(e);
        }
        //if the user provides incorrect date and time formats


        System.out.println("Expense List:");
        for (int i = expenses.size() - 1; i >= 0; i--) {
            Expense expense = (Expense) expenses.get(i);
            LocalDateTime expenseDateTime = DateTimeParser.parseOrDefault(expense.getDateTimeString(), true);

            boolean happensAfterStart = bypassStart || (startDate != null &&
                    (expenseDateTime.isEqual(startDate) || expenseDateTime.isAfter(startDate)));
            boolean happensBeforeEnd = bypassEnd || (endDate != null &&
                    (expenseDateTime.isEqual(endDate) || expenseDateTime.isBefore(endDate)));

            if (happensAfterStart && happensBeforeEnd) {
                System.out.println((expenses.size() - i) + ". " + expenses.get(i));
            }
        }

        printSeparator();
    }

    /**
     * Prints a message confirming the deletion of an expense.
     *
     * @param expenses A list of all expenses.
     * @param index The index of the expense to be deleted.
     */
    public static void printDeleteExpense(ArrayList<Expense> expenses, int index) {
        printSeparator();
        System.out.println("The following expense has been deleted successfully from Overall Budget.");
        System.out.println("-> " + expenses.get(expenses.size() - index));
        printSeparator();
    }

    /**
     * Prints a summary of all budgets and their expenses.
     *
     * @param budgets A map containing budget categories and their associated budgets.
     */
    public static void printBudgetSummary(Map<String, Budget> budgets) {
        printSeparator();
        System.out.println("Budget Summary:");
        for (String category : budgets.keySet()) {
            Budget budget = budgets.get(category);
            System.out.println("\nCategory: " + category);
            System.out.println("Total Expenses: $" + budget.getTotalExpenses());
            System.out.println("Remaining Budget: $" + budget.getRemainingBudget());
            System.out.println("Spending Limit: $" + budget.getLimit());
        }
        printSeparator();
    }

    /**
     * Prints a message indicating that an expense has been successfully updated.
     *
     * @param expenses The list of all expenses, which contains the expense that was updated.
     * @param index The index of the expense in the list that was updated.
     */
    public static void printExpenseEditedMessage(ArrayList<Expense> expenses, int index) {
        printSeparator();
        System.out.println("Got it, the expense at index " + index + " has been updated!");
        System.out.println("Updated expense -> " + expenses.get(expenses.size() - index));
        printSeparator();
    }

    /**
     * Prints a message indicating that a new expense has been successfully added.
     *
     * @param expense The expense that was added.
     */
    public static void printAddExpense(Expense expense) {
        printSeparator();
        System.out.println("Expense Added: " + expense);
        printSeparator();
    }

}
