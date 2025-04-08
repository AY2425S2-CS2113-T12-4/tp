package budgetbuddy.parser;

import budgetbuddy.exception.InvalidInputException;


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
        if (!input.startsWith("add ")) {
            throw new InvalidInputException("Invalid amount format. Use: add <AMOUNT> c/<CATEGORY> d/<DESCRIPTION>");
        }

        String line = input.substring(4).trim();

        // Check positions of c/, d/, and optional t/
        int cIndex = line.indexOf("c/");
        int dIndex = line.indexOf("d/");
        int tIndex = line.indexOf("t/");

        if (cIndex == -1 || dIndex == -1) {
            throw new InvalidInputException("Missing required markers. Use: add <AMOUNT> c/<CATEGORY> d/<DESCRIPTION>");
        }

        if (cIndex < 1 || dIndex < 1 || dIndex < cIndex) {
            throw new InvalidInputException("Use: add <AMOUNT> c/<CATEGORY> d/<DESCRIPTION>");
        }

        String amountStr = line.substring(0, cIndex).trim();
        if (amountStr.isEmpty()) {
            throw new InvalidInputException("Amount is missing.");
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
            if (amount < 0 || amount > MAX_AMOUNT) {
                throw new InvalidInputException("Amount must be between 0 and " + MAX_AMOUNT);
            }
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid amount format. Use: add <AMOUNT> c/<CATEGORY> d/<DESCRIPTION>");
        }

        String category = line.substring(cIndex + 2, dIndex).trim();

        String description;
        String dateTime = "";

        if (tIndex != -1 && tIndex > dIndex) {
            description = line.substring(dIndex + 2, tIndex).trim();
            dateTime = line.substring(tIndex + 2).trim();
        } else {
            description = line.substring(dIndex + 2).trim();
        }

        if (description.isEmpty()) {
            throw new InvalidInputException("Description cannot be empty.");
        }

        return new String[]{String.valueOf(amount), category, description, dateTime

        };
    }

}
