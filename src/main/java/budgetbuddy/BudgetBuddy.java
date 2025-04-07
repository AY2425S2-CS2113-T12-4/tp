package budgetbuddy;

import budgetbuddy.model.BudgetManager;
import budgetbuddy.storage.StorageManager;
import budgetbuddy.ui.InputManager;
import budgetbuddy.ui.Ui;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class BudgetBuddy {
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        for (Handler handler : rootLogger.getHandlers()) {
            handler.setLevel(Level.OFF);
        }
        rootLogger.setLevel(Level.OFF);

        BudgetManager budgetManager = new BudgetManager();
        StorageManager.load(budgetManager);
        InputManager inputManager = new InputManager(budgetManager);
        Ui ui = new Ui();

        ui.printWelcomeMessage();

        inputManager.processInputLoop();
        StorageManager.save(budgetManager);
    }
}
