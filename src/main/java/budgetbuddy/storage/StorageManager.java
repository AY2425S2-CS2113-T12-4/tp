package budgetbuddy.storage;

import budgetbuddy.model.Budget;
import budgetbuddy.model.BudgetManager;
import budgetbuddy.model.Expense;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Handles saving and loading of budget and alert data to/from a local txt file.
 */
public class StorageManager {
    private static final String FILE_PATH = "budget_data.txt";

    /**
     * Saves all budgets and alert amount to a file.
     */
    public static void save(BudgetManager manager) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<String, Budget> entry : manager.getBudgets().entrySet()) {
                String category = entry.getKey();
                Budget budget = entry.getValue();
                writer.write("CATEGORY:" + category + "|LIMIT:" + budget.getLimit());
                writer.newLine();
                for (Expense e : budget.getExpenses()) {
                    writer.write("EXPENSE:" + e.getAmount() + "|"
                            + e.getDescription().replace("|", " ") + "|"
                            + e.dateTime);
                    writer.newLine();
                }
            }
            writer.write("ALERT:" + manager.getBudgetAlert());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving budget data: " + e.getMessage());
        }
    }

    /**
     * Loads budgets and alert amount from a file into the BudgetManager.
     */
    public static void load(BudgetManager manager) {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            Budget currentBudget = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("CATEGORY:")) {
                    String[] parts = line.split("\\|LIMIT:");
                    String category = parts[0].substring(9);
                    double limit = Double.parseDouble(parts[1]);
                    currentBudget = new Budget(category, limit);
                    manager.getBudgets().put(category, currentBudget);
                } else if (line.startsWith("EXPENSE:") && currentBudget != null) {
                    String[] parts = line.substring(8).split("\\|");
                    double amount = Double.parseDouble(parts[0]);
                    String description = parts[1];
                    String timeStamp = parts[2];
                    Expense e = new Expense(amount, description, timeStamp, true);
                    currentBudget.addExpense(e);
                } else if (line.startsWith("ALERT:")) {
                    double alertAmount = Double.parseDouble(line.substring(6));
                    manager.setBudgetAlert(alertAmount);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading budget data: " + e.getMessage());
        }
    }
}



