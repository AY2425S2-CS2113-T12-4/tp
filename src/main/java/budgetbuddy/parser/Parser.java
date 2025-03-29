package budgetbuddy.parser;

import budgetbuddy.exception.InvalidInputException;

public class Parser {

    public Parser() {
    }

    /**
     * Parses an "add" command to extract the amount, category, description and time.
     * Expected format:
     * add `AMOUNT` c/ `CATEGORY` d/ `DESCRIPTION` t/ 'DATE TIME'
     *
     * @param line The full command input.
     * @return A string array containing [amount, category, description].
     * @throws InvalidInputException If the format is incorrect.
     */
    public String[] parseAddCommand(String line) throws InvalidInputException {
        if (line.length() < 4) {
            throw new InvalidInputException(
                    "Please use the format: add <AMOUNT> c/ <CATEGORY> d/ <DESCRIPTION> t/ <DATE TIME>");
        }

        //to remove the word "add"
        line = line.substring(4).trim();

        String[] splitAmountCategory = line.split("c/", 2);
        if (splitAmountCategory.length < 2) {
            throw new InvalidInputException(
                    "Please use the format: add <AMOUNT> c/ <CATEGORY> d/ <DESCRIPTION> t/ <DATE TIME>");
        }

        //this gives amount component from the splitAmountCategory
        String amount = splitAmountCategory[0].trim();

        //this gives the rest of line component from the splitAmountCategory
        String remaining = splitAmountCategory[1].trim();

        // Split by "d/" to separate Category and Description
        String[] splitCategoryDescription = remaining.split("d/", 2);
        if (splitCategoryDescription.length < 2) {
            throw new InvalidInputException(
                    "Please use the format: add <AMOUNT> c/ <CATEGORY> d/ <DESCRIPTION> t/ <DATE TIME>");
        }

        //this gives category component from the splitCategoryDescription
        String category = splitCategoryDescription[0].trim();

        //this gives description component from the splitCategoryDescription
        //String description = splitCategoryDescription[1].trim();

        // Split by "t/" to separate Description and Time
        //this is all the command category onwards
        String[] splitDescriptionTime = splitCategoryDescription[1].trim().split("t/", 2);
        if (splitDescriptionTime.length < 2) {
            throw new InvalidInputException(
                    "Please use the format: add <AMOUNT> c/ <CATEGORY> d/ <DESCRIPTION> t/ <DATE TIME>");
        }
        //this gives time component from the splitDescriptionTime
        String dateTime = splitDescriptionTime[1].trim();

        //this gives only the string of description that we require
        //earlier it was giving description + time
        String description = splitDescriptionTime[0].trim();

        return new String[]{amount, category, description, dateTime};
    }

    /**
     * Parses a "set-budget" command to extract the budget category and amount.
     * Expected formats:
     * - "set-budget `AMOUNT`" (for Overall budget)
     * - "set-budget c/CATEGORY `AMOUNT`" (for a specific category)
     *
     * @param command The full command input.
     * @return A string array containing [category, amount].
     * @throws InvalidInputException If the format is incorrect.
     */
    public String[] parseSetBudgetCommand(String command) throws InvalidInputException {
        String[] parts = command.split(" ");

        if (parts.length != 2 && parts.length != 3) {
            throw new InvalidInputException("Invalid command format. Use set-budget c/CATEGORY AMOUNT");
        }

        String category = "";
        String amount;

        try {
            if (parts.length == 2) { // Overall budget setting
                amount = parts[1];
            } else if (parts.length == 3 && parts[1].startsWith("c/")) { // Category budget setting
                category = parts[1].substring(2).trim();
                amount = parts[2];
            } else {
                throw new InvalidInputException("Invalid command format. Use set-budget c/CATEGORY AMOUNT");
            }
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid amount format. Please enter a valid number.");
        }

        return new String[]{category, amount};
    }

    /**
     * Parses an "alert" command to extract the alert amount.
     * Expected format: alert `AMOUNT`
     *
     * @param command The full command input.
     * @return The alert amount as a double.
     * @throws InvalidInputException If the format is incorrect.
     */
    public double parseAlertCommand(String command) throws InvalidInputException {
        String[] parts = command.split(" ");
        if (parts.length != 2) {
            throw new InvalidInputException("Please use the format: alert <AMOUNT>");
        }
        return Double.parseDouble(parts[1].trim());
    }

    public double parseDouble(String input) throws InvalidInputException {
        try {
            return Double.parseDouble(input.trim());
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid number format. Please enter a valid number.");
        }
    }

    /**
     * Parses a "delete" command to extract the expense index to be deleted.
     * Expected format: delete `INDEX`
     *
     * @param command The full command input.
     * @return The index of the expense to delete.
     * @throws InvalidInputException If the format is incorrect.
     */
    public int parseDeleteCommand(String command) throws InvalidInputException {
        String[] parts = command.split(" ");
        if (parts.length != 2) {
            throw new InvalidInputException("Please use the format: delete <INDEX>");
        }
        return Integer.parseInt(parts[1].trim());
    }

    /**
     * Parses the "check-budget" command to extract the category (if provided).
     * Expected formats:
     * - "check-budget" (for overall budget)
     * - "check-budget c/CATEGORY" (for category-specific budget)
     *
     * @param command The full user command.
     * @return The extracted category or an empty string if not provided.
     * @throws InvalidInputException If the format is incorrect.
     */
    public String parseCheckBudgetCommand(String command) throws InvalidInputException {
        String[] parts = command.split(" ");

        if (parts.length == 1) {
            return ""; // No category provided, check overall budget
        }

        if (parts.length == 2 && parts[1].startsWith("c/")) {
            return parts[1].substring(2).trim();
        }

        throw new InvalidInputException("Invalid format. Use: check-budget [c/CATEGORY]");
    }

    public String parseFindExpenseCommand(String command) throws InvalidInputException {
        String[] parts = command.split(" ");
        if (parts.length != 2) {
            throw new InvalidInputException("Please use the format: find <KEYWORD>");
        }
        return parts[1].trim();
    }


    public String[] parseEditExpenseCommand(String command) throws InvalidInputException {
        String[] result = {"", "", "", ""}; // Default values: [index, amount, description, dateTime]

        String[] parts = command.split(" ", 3);
        if (parts.length < 3) { // Must have at least "editExpense <INDEX>" and one more parameter
            throw new InvalidInputException("Please use the format: editExpense <INDEX> a/ <AMOUNT> " +
                    "d/ <DESCRIPTION> t/ <DATE TIME>");
        }

        // Extract index and ensure it is purely numeric
        String index = parts[1];
        if (!index.matches("\\d+")) {
            throw new InvalidInputException("Invalid index. Please provide a valid number.");
        }
        result[0] = index; // Store the valid index

        // Remaining arguments (optional fields)
        String remaining = parts[2].trim();

        // Check if the amount exists
        if (remaining.contains("a/")) {
            // Extract the part after "a/" and remove any extra whitespace
            String amountPart = remaining.split("a/", 2)[1].trim();

            // Now check if 'd/' or 't/' exist and trim accordingly
            if (amountPart.contains("d/")) {
                amountPart = amountPart.split("d/", 2)[0].trim(); // Extract before 'd/'
            }
            if (amountPart.contains("t/")) {
                amountPart = amountPart.split("t/", 2)[0].trim(); // Extract before 't/'
            }

            result[1] = amountPart; // Store the amount
            remaining = remaining.replace("a/" + result[1], "").trim(); // Remove the amount from
            // the remaining string
        }

        // Extract description (if present)
        if (remaining.contains("d/")) {
            String[] split = remaining.split("d/", 2);
            String descriptionPart = split[1].trim();

            // Check if 't/' exists, and if so, split by it. Otherwise, take the whole string as description.
            if (descriptionPart.contains("t/")) {
                descriptionPart = descriptionPart.split("t/", 2)[0].trim();
            }

            result[2] = descriptionPart;  // Store the description
            remaining = remaining.replace("d/" + result[2], "").trim();  // Remove description
            // from remaining string
        }

        // Extract date/time (if present)
        if (remaining.contains("t/")) {
            result[3] = remaining.split("t/", 2)[1].trim();  // Extract date/time after 't/'
        }

        return result; // Returns [index, amount, description, dateTime] with empty strings for missing fields
    }

    /**
     * Parses an "edit-budget" command to extract the current budget name, new amount, and new name.
     * Expected format:
     * edit-budget old/<CurrentName> a/<NewAmount> c/<NewName>
     *
     * At least one of "a/" (new amount) or "c/" (new name) must be provided.
     *
     * @param command The full command input.
     * @return A string array containing [currentName, newAmount, newName].
     *         If no amount is provided, the second element will be an empty string.
     *         If no new name is provided, the third element will be an empty string.
     * @throws InvalidInputException If the format is incorrect or required fields are missing.
     */

    public String[] parseEditBudgetCommand(String command) throws InvalidInputException {
        String[] result = {"", "", ""};

        if (!command.startsWith("edit-budget ")) {
            throw new InvalidInputException("Please use the format: edit-budget old/<CurrentName> a/<NewAmount> c/<NewName>");
        }

        String line = command.substring("edit-budget ".length()).trim();

        if (!line.contains("old/")) {
            throw new InvalidInputException("Missing old/ prefix. Please specify the current budget name.");
        }


        String[] splitOld = line.split("old/", 2);
        String[] currentNameString = splitOld[1].trim().split(" ");

        result[0] = currentNameString[0].trim(); 
        String remaining = splitOld[1].substring(result[0].length()).trim();

        if (remaining.contains("a/")) {
            String amt = remaining.split("a/", 2)[1].trim();
            if (amt.contains(" ")) {
                amt = amt.split(" ")[0].trim();
            }
            try {
                Double.parseDouble(amt); // Validate format
                result[1] = amt;
            } catch (NumberFormatException e) {
                throw new InvalidInputException("Invalid amount provided after a/. It must be a number.");
            }
        }

        if (remaining.contains("c/")) {
            String cat = remaining.split("c/", 2)[1].trim();
            if (cat.contains(" ")) {
                cat = cat.split(" ")[0].trim();
            }
            if (cat.isEmpty()) {
                throw new InvalidInputException("Budget name after c/ cannot be empty.");
            }
            result[2] = cat;
        }

        if (result[1].isEmpty() && result[2].isEmpty()) {
            throw new InvalidInputException("You must specify at least one of a/<amount> or c/<new name>.");
        }

        return result; // [currentName, newAmount, newName]
    }



}

