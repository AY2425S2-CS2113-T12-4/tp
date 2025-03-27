package budgetbuddy;

import budgetbuddy.model.BudgetManager;
import budgetbuddy.storage.StorageManager;
import budgetbuddy.ui.InputManager;
import budgetbuddy.ui.Ui;

public class BudgetBuddy {
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        BudgetManager budgetManager = new BudgetManager();
        StorageManager.load(budgetManager);
        InputManager inputManager = new InputManager(budgetManager);
        Ui ui = new Ui();

        ui.printWelcomeMessage();

        inputManager.processInputLoop();
        StorageManager.save(budgetManager);
    }
}
