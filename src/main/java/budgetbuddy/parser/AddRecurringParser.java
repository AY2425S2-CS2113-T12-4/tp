package budgetbuddy.parser;

import budgetbuddy.exception.InvalidInputException;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses the "add-recurring" command to extract amount, category, description,
 * date/time, frequency (in days), and number of iterations.
 *
 * This is then sent to the AddRecurringExpenseCommand
 */
public class AddRecurringParser extends Parser<String[]> {

    public AddRecurringParser(String input) {
        super(input);
    }

    @Override
    public String[] parse() throws InvalidInputException {
        if (input.length() < 6) {
            throw new InvalidInputException("Use: add-recurring <AMOUNT> c/ <CATEGORY>" +
                    " d/ <DESCRIPTION> t/ <DATE TIME> f/ <FREQUENCY_IN_DAYS> i/ <ITERATIONS>");
        }

        String line = input.substring("add-recurring".length()).trim();
        List<String> missingFields = new ArrayList<>();

        int cIndex = line.indexOf("c/");
        int dIndex = line.indexOf("d/");
        int tIndex = line.indexOf("t/");
        int fIndex = line.indexOf("f/");
        int iIndex = line.indexOf("i/");


        if (cIndex == -1) {
            missingFields.add("c/");
        }
        if (dIndex == -1){
            missingFields.add("d/");
        }
        if (tIndex == -1) {
            missingFields.add("t/");
        }
        if (fIndex == -1) {
            missingFields.add("f/");
        }
        if (iIndex == -1){
            missingFields.add("i/");
        }


        if (!missingFields.isEmpty()) {
            throw new InvalidInputException("Missing required markers: " + String.join(", ", missingFields));
        }

        // Check marker order for correct parsing
        if (!(cIndex < dIndex && dIndex < tIndex && tIndex < fIndex && fIndex < iIndex)) {
            throw new InvalidInputException("Markers must be in order: c/ before d/ before t/ before f/ before i/");
        }

        // Extract raw segments
        String amount = line.substring(0, cIndex).trim();
        String category = line.substring(cIndex + 2, dIndex).trim();
        String description = line.substring(dIndex + 2, tIndex).trim();
        String dateTime = line.substring(tIndex + 2, fIndex).trim();
        String frequencyStr = line.substring(fIndex + 2, iIndex).trim();
        String iterationsStr = line.substring(iIndex + 2).trim();

        if (amount.isEmpty()){
            missingFields.add("amount");
        }
        if (category.isEmpty()) {
            missingFields.add("category");
        }
        if (description.isEmpty()){
            missingFields.add("description");
        }
        if (dateTime.isEmpty()) {
            missingFields.add("date/time");
        }
        if (frequencyStr.isEmpty()) {
            missingFields.add("frequency");
        }
        if (iterationsStr.isEmpty()) {
            missingFields.add("iterations");
        }


        //print all the missing fields
        if (!missingFields.isEmpty()) {
            throw new InvalidInputException("Missing required fields: " + String.join(", ", missingFields));
        }

        // Validate frequency and iterations
        int frequency, iterations;

        try {
            frequency = Integer.parseInt(frequencyStr);
            if (frequency < 1) {
                throw new InvalidInputException("Frequency (f/) must be at least 1 day.");
            }
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Frequency (f/) must be a valid number.");
        }

        try {
            iterations = Integer.parseInt(iterationsStr);
            if (iterations < 1) {
                throw new InvalidInputException("Iterations (i/) must be at least 1.");
            }
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Iterations (i/) must be a valid whole number.");
        }

        //return this array to AddRecurringExpenseCommand
        return new String[]{amount, category, description, dateTime,
                String.valueOf(frequency), String.valueOf(iterations)};
    }
}
