# Juneja Akshat - Project Portfolio Page

## Overview
**Budget Buddy** is a lightweight, student-focused expense tracker designed to help users manage 
their daily spending, set budgets, and stay financially aware. It offers features like real-time tracking, 
spending alerts, and budget summaries to build better financial habits. With a simple interface and 
cross-platform compatibility, Budget Buddy makes money management easy and accessible.

## Summary of Contributions

### Summary Functionality 
- **What it does:**
  - Helps users view a concise summary of their spending without going through individual expenses, 
  giving a quick overview of how much they’ve spent.
  - Provides a breakdown across all existing budget categories if the user does not provide particular categories.
  If no other custom categories exist,it defaults to summarizing the "Overall" budget alone.
  - If particular categories are selected by the user, then only summary of those categories is provided.
  - For each budget, the following are displayed:
      - Budget category
      - Total expenses made in the category (in Dollars)
      - Remaining Budget of the category (in Dollars)
      - Spending limit of the category (in Dollars)

- **Justification:**
  - Saves time and effort by removing the need to manually check or calculate usage across different budgets.
  - Promotes financial awareness by showing spending and remaining budgets across both overall and category levels.


### Add Recurring Expenses 
- **What it does:**
  - Enables users to add periodic expenses by specifying all necessary parameters: amount, category, description, 
  start date/time, frequency (in days), and number of iterations.
  - The system automatically generates and inserts each occurrence based on the frequency and iteration count, 
  simulating real-world recurring payments like subscriptions or bills.
  - Input is constrained by a maximum frequency of 1000 days and a limit of 10 iterations to ensure system stability,
  avoid excessive duplication, and maintain performance over time.

- **Justification:**
  - Simplifies the process of recording recurring payments such as subscriptions (e.g., Netflix, Spotify) or
  regular bills by letting users add them all at once.
  - Reduces manual effort and lowers the chance of missed entries by automatically generating expenses based on the 
  provided frequency and iteration count.
  
### Time-Based Expense Listing
- **What it does:**
  - Allows users to filter expenses using start/ and end/ markers — either one, both, 
  or none(defaults to normal list operations) — to view expenses within a specific date-time range or list all by default.
  - If a marker is used without a valid date-time, a warning is shown and the system defaults to the current time, 
  ensuring smooth functionality even with incomplete input.

- **Justification:**
  - Gives users greater control over how they view their expenses by allowing them to focus on specific time periods,
  such as daily, weekly, or monthly summaries.
  - Improves usability and resilience by handling incomplete or incorrect input gracefully, ensuring the command still 
  functions even when the user input is partial or malformed.

### Date-Time Tracking for expenses
- **What it does:**
  - Allows users to attach a specific date and time to each expense, enabling more accurate tracking and 
  future filtering based on time.
  - If no time is provided, the system automatically assigns the current date and time, ensuring all expenses 
  have a valid timestamp by default.

- **Justification:**
  - Makes it easier for users to filter and view expenses during listing by associating each entry with a 
  specific date and time.
  - Improves user convenience by automatically handling missing time inputs with the current system time, 
  reducing effort and preventing errors.

### Other Significant Code Contributions
- Implemented Date and Time for the system including supporters such as `DateTimeParser` and `DATE_TIME_FORMAT`.
- Implemented JUnit test cases for `SummaryCommand`, `BudgetSummary`, `AddRecurringExpense`, `AddExpenseCommand`,
`ListCommand`.
- Implemented correct guidelines for `summary`, `add-recurring`, `add`, `list` under the `help` functionality.
- Implemented methods in `Ui` class related to correct printing of lists.

### Developer Guide
- Documented the design of the abstract `Command` class with a clear overview, class diagram, 
and a detailed example (`AddExpenseCommand`) in the Design section. This showcases the standardized 
structure and extensibility of command implementations across the app.
- Added a sequence diagram for the `list` command in the Implementation section to illustrate 
time-based filtering logic and flow.
- Added a sequence diagram for the `add-recurring` command in the Implementation section 
to show how recurring expenses are parsed, validated, and recorded.
- Expanded the value proposition in the Product Scope section to better reflect the system’s
practical benefits to users.

### User Guide 
- Added description, formatting and examples for `add-recurring` function.
- Added description, formatting and examples for `summary` function.
- Added formatting and examples for `list` function.
- Added examples for `add` function.

### Code Contributed
- Thank you for reading my Project Portfolio Page.
- Please view my code contributions [here](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=functional-code&since=2025-02-21&tabOpen=true&tabType=authorship&tabAuthor=juneja999&tabRepo=AY2425S2-CS2113-T12-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=functional-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)



