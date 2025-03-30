package budgetbuddy.parser;

import budgetbuddy.exception.InvalidInputException;

/**
 * Parses the "add" command to extract amount, category, description, and date/time.
 */
public class AddParser extends Parser<String[]> {
    public AddParser(String input) {
        super(input);
    }

    @Override
    public String[] parse() throws InvalidInputException {
        if (input.length() < 4) {
            throw new InvalidInputException("Use: " +
                    "add <AMOUNT> c/ <CATEGORY> d/ <DESCRIPTION> t/ <DATE TIME>");
        }

        String line = input.substring(4).trim();
        String[] splitAmountCategory = line.split("c/", 2);
        if (splitAmountCategory.length < 2) {
            throw new InvalidInputException("Missing c/");
        }

        String amount = splitAmountCategory[0].trim();
        String[] splitCategoryDescription = splitAmountCategory[1].split("d/", 2);
        if (splitCategoryDescription.length < 2) {
            throw new InvalidInputException("Missing d/");
        }

        String category = splitCategoryDescription[0].trim();
        String[] splitDescriptionTime = splitCategoryDescription[1].split("t/", 2);
        if (splitDescriptionTime.length < 2) {
            throw new InvalidInputException("Missing t/");
        }

        String description = splitDescriptionTime[0].trim();
        String dateTime = splitDescriptionTime[1].trim();

        return new String[]{amount, category, description, dateTime};
    }
}
