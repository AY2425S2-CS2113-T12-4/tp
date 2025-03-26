package budgetbuddy;


public class BudgetBuddy {
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        BudgetManager budgetManager = new BudgetManager();

        // Load data from file into the budget manager
        StorageManager.load(budgetManager);

        InputManager inputManager = new InputManager(budgetManager);

        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);

        inputManager.processInputLoop();
        // Save data to file after user exits
        StorageManager.save(budgetManager);
    }
}
