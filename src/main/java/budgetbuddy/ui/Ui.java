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
    private static final String SEPARATOR =
            "__________________________________________";  // Separator for formatting
    private static String lastOutput;
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
        printSeparator();
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
        System.out.println("Format: add [AMOUNT] c/[CATEGORY] d/[DESCRIPTION] t/[MMM dd yyyy 'at' hh:mm]");
        System.out.println("Examples: add 15.50 c/Food d/Lunch t/Oct 05 2025 at 12:30, " +
                "\n          add 40 c/Transport d/Taxi Ride t/Oct 10 2025 at 14:35");

        System.out.println("\nAdd Recurring Expense: add-recurring");
        System.out.println("Format: add-recurring [AMOUNT] c/[CATEGORY] d/[DESCRIPTION] t/[TIME]" +
                " f/[FREQUENCY] i/[ITERATIONS]");
        System.out.println("Examples: add-recurring 20 c/Food d/Lunch t/Apr 24 2025 at 12:00 f/30 i/5");

        System.out.println("\nDelete Expense: delete");
        System.out.println("Format: delete [INDEX]");
        System.out.println("Examples: delete 2" +
                "\n          delete 5");

        System.out.println("\nView Expenses: list");
        System.out.println("Format: list start/[START_TIME] end/[END_TIME]");
        System.out.println("Example: list"+"\n         list start/Apr 24 2025 at 12:00 ," +
                " \n         list start/Apr 24 2025 at 12:00 end/May 01 2025 at 12:00");

        System.out.println("\nEdit Expense: edit-expense");
        System.out.println("Format: edit-expense INDEX a/[AMOUNT] d/[DESCRIPTION] t/[MMM dd yyyy 'at' hh:mm]");
        System.out.println("Examples: edit-expense 1 a/25.00, " +
                "\n          edit-expense 2 d/Dinner t/Nov 15 2025 at 19:00");

        System.out.println("\nSet Budget: set-budget");
        System.out.println("Format: set-budget [AMOUNT] " +
                "\n          set-budget c/[CATEGORY] [AMOUNT]");
        System.out.println("Examples: set-budget 1000" +
                "\n          set-budget c/Food 300");

        System.out.println("\nCheck Budget: check-budget");
        System.out.println("Format: check-budget c/[CATEGORY]");
        System.out.println("Examples: check-budget" +
                "\n          check-budget c/Groceries");

        System.out.println("\nEdit Budget: edit-budget");
        System.out.println("Format: edit-budget old/[CURRENT_NAME] a/[NEW_AMOUNT] c/[NEW_NAME]");
        System.out.println("Examples: edit-budget old/Food a/500, " +
                "\n          edit-budget old/Food c/Groceries");

        System.out.println("\nBudget Summary: summary");
        System.out.println("Format: summary c/[CATEGORY1] c/[CATEGORY2] ...");
        System.out.println("Examples: summary " +
                "\n          summary c/Food" +
                "\n          summary c/Food c/Transport");

        System.out.println("\nSet Alert: alert");
        System.out.println("Format: alert [AMOUNT]");
        System.out.println("Examples: alert 500" +
                "\n          alert 0");

        System.out.println("\nDelete Alert: delete-alert");
        System.out.println("Format: delete-alert");
        System.out.println("Example: delete-alert");

        System.out.println("\nFind Expenses: find");
        System.out.println("Format: find [KEYWORD]");
        System.out.println("Example: find coffee");

        System.out.println("\nExit Program: bye");
        System.out.println("Format: bye");
        System.out.println("Example: bye");

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
     * @param isEdit      A boolean indicating if this is an edit to an existing alert.
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
     * @param alertAmount   The threshold set for the budget alert.
     */
    public static void printCheckAlert(double totalExpenses, double alertAmount) {
        System.out.println("Warning: Your total expenses ($" + totalExpenses +
                ") have exceeded the alert limit of $" + alertAmount);
        printSeparator();
    }

    /**
     * Prints a message when total expenses hit the alert threshold exactly.
     *
     * @param totalExpenses The current total expenses, equal to alert amount.
     */
    public static void printHitAlert(double totalExpenses) {
        printSeparator();
        System.out.println("Notice: You have hit your budget alert limit of $" + totalExpenses);
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
    public static void printExpensesList(ArrayList<Expense> expenses) {
        printSeparator();
        System.out.println("Expense List:");
        expenses.sort((e1, e2) -> e1.getDateTime().compareTo(e2.getDateTime()));
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

        boolean bypassStart = start.isBlank();
        //if no start date provided
        boolean bypassEnd = end.isBlank();
        //if no end date provided

        //boolean happensAfterStart = false;
        //if recorded expense happens after user provided start date
        //boolean happensBeforeEnd = false;
        //if recorded expense happens before user provided end date


        LocalDateTime startDate = null;
        LocalDateTime endDate = null;


        try {
            if (!bypassStart) {
                startDate = DateTimeParser.parseOrDefault(start, true);
                if(!DateTimeParser.parseOrDefaultBooleanReturn(start, true)) {
                    System.err.println("Invalid date format for \"start/\". Use: \"MMM dd yyyy at HH:mm\"");
                    System.err.flush(); //make sure that error appears before list
                    return; //Exit function
                }
            }
            if (!bypassEnd) {
                endDate = DateTimeParser.parseOrDefault(end, true);
                if(!DateTimeParser.parseOrDefaultBooleanReturn(end, true)) {
                    System.err.println("Invalid date format for \"end/\". Using current date and time instead.");
                    System.err.flush();//make sure that error appears before list
                }
            }
        } catch (Exception e) {
            System.out.println("Error parsing expenses list: " + e.getMessage());
            //throw new RuntimeException(e);
        }
        //if the user provides incorrect date and time formats

        if(!bypassEnd && !bypassStart){
            assert startDate != null;
            if(startDate.isAfter(endDate)){
                System.err.println("Start time cannot occur after end time.");
                System.err.flush();//flush error before any other print
                return;
            }
        }

        printSeparator();
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
     * @param index    The index of the expense to be deleted.
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

        if (budgets.isEmpty()) {
            System.out.println("No budgets found.");
        } else {
            for (String category : budgets.keySet()) {
                Budget budget = budgets.get(category);
                System.out.println("\nCategory: " + category);
                System.out.println("Total Expenses: $" + budget.getTotalExpenses());
                System.out.println("Remaining Budget: $" + budget.getRemainingBudget());
                System.out.println("Spending Limit: $" + budget.getLimit());
            }
        }

        printSeparator();
    }


    /**
     * Prints a message indicating that an expense has been successfully updated.
     *
     * @param expenses The list of all expenses, which contains the expense that was updated.
     * @param index    The index of the expense in the list that was updated.
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
    public static void printAddExpense(Expense expense, String category, boolean addedToCategory, String message) {
        printSeparator();
        System.out.println("Expense Added: " + expense);
        if (category != null && !category.isEmpty()) {
            if (!message.isEmpty()) {
                System.out.println(message);
            } else if (addedToCategory) {
                System.out.println("Successfully added to category budget");
            }
        }
        printSeparator();
    }

    public static void printSetOverallBudget(double budget) {
        printSeparator();
        System.out.println("Overall Budget set to: $" + budget);
        printSeparator();
    }

    public static void printSetCategoryBudget(String category, double budget) {
        printSeparator();
        System.out.println("Budget for " + category + " set to: $" + budget);
        printSeparator();
    }

    public static void printDeleteExpenseCategory(String category) {
        printSeparator();
        System.out.println("Expense also deleted from category '" + category + "'.");
        printSeparator();
    }

    /**
     * Prints the budget summary for a specific category or overall budget.
     *
     * @param category    The budget category to check (empty string for overall budget)
     * @param totalBudget The Budget object containing the budget information
     */
    public static void printCheckBudget(String category, double totalBudget, double spent, double remaining) {
        printSeparator();
        if (category == null || category.trim().isEmpty()) {
            System.out.println("Overall Budget:");
        } else {
            System.out.println("Budget for " + category);
        }

        System.out.println("\nTotal Budget: $" + String.format("%.2f", totalBudget));
        System.out.println("Spent: $" + String.format("%.2f", spent));
        System.out.println("Remaining: $" + String.format("%.2f", remaining));
        printSeparator();
    }

    public static void printBudgetNotFound(String category) {
        printSeparator();
        System.out.println("Budget category '" + category + "' not found.");
        printSeparator();
    }

    /**
     * Prints the header for expense search results
     *
     * @param keyword The search keyword used
     */
    public static void printSearchHeader(String keyword) {
        printSeparator();
        System.out.println("Expenses Matching: '" + keyword + "'");
    }

    /**
     * Prints a single matching expense with index
     *
     * @param index   The 1-based index of the expense
     * @param expense The expense to print
     */
    public static void printMatchingExpense(int index, Expense expense) {
        System.out.println(index + ". " + expense);
    }

    /**
     * Prints message when no expenses are found
     *
     * @param keyword The search keyword used
     */
    public static void printNoMatchesFound(String keyword) {
        System.out.println("No matching expenses found for keyword: " + keyword);
        printSeparator();
    }

    /**
     * Prints the budget limit update confirmation with separator lines.
     * Displays the current budget name and its new spending limit.
     *
     * @param currentName The name of the budget being updated (cannot be null or empty)
     * @param newLimit    The new spending limit amount (must be positive)
     */
    public static void printUpdateBudgetLimit(String currentName, double newLimit) {
        printSeparator();
        System.out.println("Budget limit for " + currentName + " updated to: $" + newLimit);
        printSeparator();
    }

    /**
     * Prints the budget rename confirmation with separator lines.
     * Shows both the original and new budget category names.
     *
     * @param oldName The original budget category name (cannot be null or empty)
     * @param newName The new budget category name (cannot be null or empty)
     */
    public static void printRenamedBudget(String oldName, String newName) {
        printSeparator();
        System.out.println("Budget category '" + oldName + "' renamed to '" + newName + "'.");
        printSeparator();
    }

    /**
     * Prints an error message with a separator line.
     *
     * @param errorMessage The error message to be displayed.
     */
    public static void printError(String errorMessage) {
        printSeparator();
        System.out.println("Error: " + errorMessage);
        printSeparator();
    }

    /**
     * Prints a message indicating that the provided time format is incorrect.
     * <p>
     * The method also displays the correct format guide and informs the user that the system's current
     * date and time will be used instead.
     * <p>
     * Expected format: {@code "MMM dd yyyy 'at' HH:mm"} (e.g., Jan 31 2025 at 14:30)
     */
    public static void printWrongTimeFormat() {
        System.out.println("Wrong time format used. " + "Will use system current dateTime instead.");
        System.out.println("Format guide: \"MMM dd yyyy 'at' HH:mm\"");
        printSeparator();
    }

    /**
     * Prints a warning message when the total expenses have exceeded the budget limit for a given category.
     *
     * @param totalExpense the total amount of expenses recorded
     * @param limit        the budget limit set for the category
     * @param category     the name of the budget category
     */
    public static void printBudgetExceeded(double totalExpense, double limit, String category) {
        String formattedTotalExpense = String.format("$%,.2f", totalExpense);
        String formattedLimit = String.format("$%,.2f", limit);
        System.out.println("Warning: Your total expenses (" + formattedTotalExpense + ") have exceeded the budget " +
                "limit for the '" + category + "' category (limit: " + formattedLimit + ")");
        printSeparator();
    }

    /**
     * Prints a warning message when the total expenses have exactly reached the budget limit for a given category.
     *
     * @param totalExpense the total amount of expenses recorded
     * @param limit        the budget limit set for the category
     * @param category     the name of the budget category
     */
    public static void printBudgetReached(double totalExpense, double limit, String category) {
        printSeparator();
        String formattedTotalExpense = String.format("$%,.2f", totalExpense);
        String formattedLimit = String.format("$%,.2f", limit);
        System.out.println("Warning: Your total expenses (" + formattedTotalExpense + ") have reached the budget " +
                "limit for the '" + category + "' category (limit: " + formattedLimit + ")");
        printSeparator();
    }

    public static void printMessage(String message) {
        lastOutput = message;
        System.out.println(message);
    }


    public static String getLastOutput() {
        return lastOutput;
    }
}
