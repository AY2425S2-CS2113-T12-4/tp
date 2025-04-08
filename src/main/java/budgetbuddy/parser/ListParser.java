package budgetbuddy.parser;

import budgetbuddy.exception.InvalidInputException;

/**
 * Parses input to extract optional start/ and end/ date filters.
 */
public class ListParser extends Parser<String[]> {

    public ListParser(String input) {
        super(input);
    }

    @Override
    public String[] parse() throws InvalidInputException {
        String start = "";
        String end = "";

        boolean hasStart = input.contains("start/");
        boolean hasEnd = input.contains("end/");

        if (hasStart) {
            String[] splitStart = input.split("start/", 2);
            if (splitStart.length < 2 || splitStart[1].isBlank() || splitStart[1].trim().startsWith("end/")) {
                throw new InvalidInputException("start/ marker is missing or empty.");
            }
            start = splitStart[1].split("end/")[0].trim();
        }

        if (hasEnd) {
            String[] splitEnd = input.split("end/", 2);
            end = splitEnd[1].trim();
        }

        return new String[]{start, end};
    }
}
