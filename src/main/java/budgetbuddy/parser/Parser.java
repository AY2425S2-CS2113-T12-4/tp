package budgetbuddy.parser;

import budgetbuddy.exception.InvalidInputException;

/**
 * Abstract base class for all command parsers.
 * Each parser class should implement the parse() method to extract command-specific data.
 *
 * @param <T> the return type after parsing
 */
public abstract class Parser<T> {
    protected final String input;

    public Parser(String input) {
        this.input = input;
    }

    /**
     * Parses the input and returns the parsed result.
     *
     * @return parsed output of type T
     * @throws InvalidInputException if the input format is invalid
     */
    public abstract T parse() throws InvalidInputException;
}
