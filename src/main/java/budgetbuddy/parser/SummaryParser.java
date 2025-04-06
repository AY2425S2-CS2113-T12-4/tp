package budgetbuddy.parser;

import budgetbuddy.exception.InvalidInputException;
import budgetbuddy.model.BudgetManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses the "summary" command to extract one or more category names.
 * If no category is specified, returns an empty array to indicate summary across all categories.
 */
public class SummaryParser extends Parser<String[]> {

    private final BudgetManager budgetManager;

    public SummaryParser(String input, BudgetManager budgetManager) {
        super(input);
        this.budgetManager = budgetManager;
    }

    @Override
    public String[] parse() throws InvalidInputException {
        String trimmedInput = input.trim();

        // Handle case where only "summary" is passed with no categories
        if (trimmedInput.equals("summary")) {
            return new String[0]; // summary across all categories
        }

        // Expecting format like: summary c/Food c/Transport
        String[] tokens = trimmedInput.substring("summary".length()).trim().split("c/");
        List<String> categoryList = new ArrayList<>();

        for (String token : tokens) {
            String category = token.trim();
            if (category.isEmpty()) {
                continue;
            }

            if (!budgetManager.categoryExists(category)) {
                throw new InvalidInputException("Category '" + category + "' does not exist.");
            }

            categoryList.add(category);
        }

        return categoryList.toArray(new String[0]);
    }
}
