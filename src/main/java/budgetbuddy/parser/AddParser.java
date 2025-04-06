package budgetbuddy.parser;

import budgetbuddy.exception.InvalidInputException;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses the "add" command to extract amount, category, description, and date/time.
 */
public class AddParser extends Parser<String[]> {
    private static final double MAX_AMOUNT = 10000; // Define a maximum amount limit

    public AddParser(String input) {
        super(input);
    }

    @Override
    public String[] parse() throws InvalidInputException {
        if (input.length() < 4) {
            throw new InvalidInputException("Use: add <AMOUNT> c/ <CATEGORY> d/ <DESCRIPTION> t/ <DATE TIME>");
        }

        String line = input.substring(4).trim();
        List<String> missingFields = new ArrayList<>();
        List<String> wrongOrderFields = new ArrayList<>();

        // Check marker order
        int cIndex = line.indexOf("c/");
        int dIndex = line.indexOf("d/");
        int tIndex = line.indexOf("t/");

        if (cIndex == -1) {
            missingFields.add("c/");
        }
        if (dIndex == -1) {
            missingFields.add("d/");
        }
        if (tIndex == -1) {
            missingFields.add("t/");
        }

        if (!missingFields.isEmpty()) {
            throw new InvalidInputException("Missing " + String.join(", ", missingFields));
        }

        // Verify correct order: c/ must come before d/, which must come before t/
        if (cIndex > dIndex || cIndex > tIndex || dIndex > tIndex) {
            throw new InvalidInputException("Markers must be in sequence: c/ before d/ before t/");
        }

        // Split components
        String[] splitAmountCategory = line.split("c/", 2);
        String amountStr = splitAmountCategory[0].trim();
        if (amountStr.isEmpty()) {
            missingFields.add("amount");
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
            if (amount > MAX_AMOUNT) {
                throw new InvalidInputException("Amount must be between 0 and " + MAX_AMOUNT);
            }
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid amount format.");
        }

        String[] splitCategoryDescription = splitAmountCategory[1].split("d/", 2);
        String description = splitCategoryDescription[1].split("t/", 2)[0].trim();
        if (description.isEmpty()) {
            missingFields.add("description");
        }

        if (!missingFields.isEmpty()) {
            throw new InvalidInputException("Missing " + String.join(", ", missingFields));
        }

        // Extract all components
        String category = splitCategoryDescription[0].trim();
        String dateTime = splitCategoryDescription[1].split("t/", 2)[1].trim();

        return new String[]{amountStr, category, description, dateTime};
    }
}