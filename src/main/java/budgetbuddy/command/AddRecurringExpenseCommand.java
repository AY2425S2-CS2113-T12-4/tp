package budgetbuddy.command;

import budgetbuddy.exception.InvalidInputException;
import budgetbuddy.model.BudgetManager;
import budgetbuddy.parser.AddRecurringParser;
import budgetbuddy.parser.DateTimeParser;

import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;

/**
 * Handles the "add-recurring" command for Budget Buddy.
 * <p>
 * This command allows the user to add a recurring expense to the budget. The user can specify:
 * <ul>
 *     <li>The expense amount</li>
 *     <li>Category (e.g., Food, Transport, Overall)</li>
 *     <li>Description</li>
 *     <li>Start date and time</li>
 *     <li>Recurring frequency (in days)</li>
 *     <li>Number of iterations (how many times to add the expense)</li>
 * </ul>
 * <p>
 * The expected format for the date and time is:
 * <pre>
 * MMM dd yyyy at HH:mm
 * </pre>
 * For example:
 * <pre>
 * Apr 24 2025 at 12:00
 * </pre>
 * If the provided date format is invalid, the system will use the current date and time as a fallback.
 * <p>
 * Constraints:
 * <ul>
 *     <li>Maximum frequency: 1000 days</li>
 *     <li>Maximum iterations: 10</li>
 * </ul>
 * <p>
 * Output during repeated expense addition is temporarily suppressed to avoid console clutter.
 * Once complete, a confirmation message and the full expense list will be displayed.
 *
 * Example usage:
 * <pre>
 * add-recurring 20 c/Food d/Lunch t/Apr 24 2025 at 12:00 f/30 i/5
 * </pre>
 *
 * @author juneja999
 */

public class AddRecurringExpenseCommand extends Command {

    //these are the constraints we are adding to this command
    public static final int MAX_FREQUENCY_ADD_RECURRING  = 1000;
    public static final int MAX_ITERATIONS_ADD_RECURRING = 10;

    public AddRecurringExpenseCommand(String description){
        super(description);
    }


    @Override
    public void execute(BudgetManager budgetManager) throws InvalidInputException{

        AddRecurringParser recurringParser = new AddRecurringParser(description);
        String[] parsedData = recurringParser.parse();

        double amount = Double.parseDouble(parsedData[0]);
        String category = parsedData[1];
        String expenseDescription = parsedData[2];
        String startTime = parsedData[3];
        int recurringFrequency = Integer.parseInt(parsedData[4]);
        int recurringIterations = Integer.parseInt(parsedData[5]);

        //throws error when frequency greater than 100 and returns
        if(recurringFrequency > MAX_FREQUENCY_ADD_RECURRING){
            System.err.println("Frequency should be less than or equal to 1000 days for add-recurring command.");
            return;
        }
        //throws error when iterations more than 10 and returns
        if(recurringIterations > MAX_ITERATIONS_ADD_RECURRING){
            System.err.println("Iterations should be less than or equal to 10 for add-recurring command.");
            return;
        }

        //get formatted date time from DateTimeParser,
        //even if user inputs wrong format, this will utilise the correct format
        //and successive iterations would be based upon this
        LocalDateTime startTimeParsed = DateTimeParser.parseOrDefault(startTime, false);
        startTime = startTimeParsed.format(DateTimeParser.DATETIME_FORMAT);


        //From hereon we start adding expenses to budget
        //While we are adding expenses, we redirect all the print notifications to nullspace
        //this is done so that our console is not cluttered with all the addExpenseToBudget print lines
        System.out.println("Adding recurring expense to budget...");
        PrintStream orignalOut = System.out;
        System.setOut(new PrintStream(OutputStream.nullOutputStream())); //redirecting print lines

        // SHOULD SUPPRESS ERROR MESSAGES ALSO?
        for(int i=0; i<recurringIterations; i++){

            //after the first iteration, we successively add time for the expenses
            if(i>0){
                startTimeParsed = startTimeParsed.plusDays(recurringFrequency);
                startTime = startTimeParsed.format(DateTimeParser.DATETIME_FORMAT);
            }
            budgetManager.addExpenseToBudget(category , amount , expenseDescription , startTime );
        }
        System.setOut(orignalOut); //re-instantiating to console
        System.out.println("Hooray! Added recurring expense(s) to budget.");
        System.out.println("Here is the list:");
        budgetManager.listAllExpenses(); // show the list after adding

    }


    @Override
    public boolean isExit() {
        return false;
    }
}
