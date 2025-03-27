package budgetbuddy.model;

import budgetbuddy.ui.Ui;

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
            Ui.printInvalidBudgetAlertWarning();
        }

        this.alertAmount = amount;
        this.isActive = amount > 0;

        if (isActive) {
            Ui.printSetBudgetAlert(alertAmount, false);
        }else {
            Ui.printRemoveBudgetAlert();
        }
    }

    /**
     * Checks if total expenses exceed the alert amount.
     *
     * @param totalExpenses The current total expenses.
     */
    public void checkAlert(double totalExpenses) {
        assert totalExpenses >= 0 : "Total expenses cannot be negative";
        if (isActive && totalExpenses > alertAmount) {
            Ui.printCheckAlert(totalExpenses, alertAmount);
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


    /**
     * Edits the current alert amount.
     *
     * @return The new alert threshold.
     */
    public double editAlertAmount(double amount) {
        if (amount < 0) {
            Ui.printInvalidBudgetAlertWarning();
            return (int) alertAmount;
        }

        this.alertAmount = amount;
        this.isActive = amount > 0;

        if (isActive) {
            Ui.printSetBudgetAlert(alertAmount, true);
        }else {
            Ui.printRemoveBudgetAlert();
        }

        return amount;
    }
}
