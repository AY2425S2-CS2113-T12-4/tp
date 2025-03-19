package seedu.duke;

import java.util.Scanner;

import seedu.duke.exception.InvalidInputException;


/**
 * The InputManager class handles user input, processes commands, and interacts with the BudgetManager.
 * It continuously listens for input until the "bye" command is entered, and processes commands to
 * add expenses to budgets or handle invalid input.
 */
public class InputManager {
    private final BudgetManager budgetManager;


    /**
     * Constructs an InputManager that interacts with the specified BudgetManager.
     *
     * @param budgetManager The BudgetManager instance to be used for managing budgets and expenses.
     */
    public InputManager(BudgetManager budgetManager) {
        assert budgetManager != null : "BudgetManager cannot be null.";
        this.budgetManager = budgetManager;
    }

    /**
     * Starts an input processing loop where the user can enter commands.
     * The loop continues until the user types "bye".
     * Valid commands include "add" to add expenses to budgets.
     */
    public void processInputLoop() {
        String line;
        Scanner in = new Scanner(System.in);

        while (true) {
            line = in.nextLine().trim();

            try {
                if (line.equalsIgnoreCase("bye")) {
                    break;
                } else if (line.toLowerCase().startsWith("add")) {
                    if (line.length() < 4) {
                        throw new InvalidInputException("Please use the format: add <AMOUNT> /d <DESCRIPTION>");
                    }
                    line = line.substring(4);
                    String[] splitLine = line.split("/d", 2); // Split into two parts: description and amount
                    if (splitLine.length < 2) {
                        throw new InvalidInputException("Please use the format: add <AMOUNT> /d <DESCRIPTION>");
                    }
                    double amount = Double.parseDouble(splitLine[0]);
                    String description = splitLine[1];
                    budgetManager.addExpenseToBudget("", amount, description);

                } else if (line.toLowerCase().startsWith("alert")) {
                    // Handle the alert command
                    String[] parts = line.split(" ");
                    if (parts.length != 2) {
                        throw new InvalidInputException("Please use the format: alert <AMOUNT>");
                    }
                    double amount = Double.parseDouble(parts[1]);
                    budgetManager.setBudgetAlert(amount);

                } else if (line.equalsIgnoreCase("summary")) {
                    BudgetSummary budgetSummary = new BudgetSummary(budgetManager);
                    budgetSummary.summariseBudget();

                } else if (line.equalsIgnoreCase("list")) {
                    budgetManager.listAllExpenses();

                } else if (line.toLowerCase().startsWith("delete")) {
                    String[] parts = line.split(" ");
                    if (parts.length != 2) {
                        throw new InvalidInputException("Please use the format: delete <INDEX>");
                    }
                    int index = Integer.parseInt(parts[1]);
                    budgetManager.deleteExpense(index);

                } else {
                    throw new InvalidInputException("Please try again with one of the valid commands:" +
                            "\nadd, alert, summary, list, delete, bye");
                }
            } catch (InvalidInputException e) {
                e.print();
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: Please enter a valid number.");
            }
        }
        in.close();
    }
}
