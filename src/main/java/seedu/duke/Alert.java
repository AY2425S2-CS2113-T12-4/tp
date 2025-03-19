package seedu.duke;

/**
 * Manages budget alerts and notifies the user when expenses exceed a specified amount.
 */
public class Alert {
    private double alertAmount;
    private boolean isActive;

    /**
     * Initializes the Alert with no active limit.
     */
    public Alert() {
        this.alertAmount = 0;
        this.isActive = false;
    }

    /**
     * Sets the alert amount. If the amount is 0, it disables the alert.
     *
     * @param amount The amount to trigger an alert.
     */
    public void setAlert(double amount) {
        if (amount < 0) {
            System.out.println("⚠️ Error: Alert amount must be a positive number.");
            return;
        }

        this.alertAmount = amount;
        this.isActive = amount > 0;

        if (isActive) {
            System.out.println("✅ Budget alert set at $" + alertAmount +
                    ". You will be notified if expenses exceed this amount.");
        } else {
            System.out.println("🛑 Budget alert has been removed.");
        }
    }

    /**
     * Checks if total expenses exceed the alert amount.
     *
     * @param totalExpenses The current total expenses.
     */
    public void checkAlert(double totalExpenses) {
        if (isActive && totalExpenses > alertAmount) {
            System.out.println("⚠️ Warning: Your total expenses ($" + totalExpenses +
                    ") have exceeded the alert limit of $" + alertAmount);
        }
    }

    /**
     * Gets the current alert amount.
     *
     * @return The alert threshold.
     */
    public double getAlertAmount() {
        return alertAmount;
    }
}
