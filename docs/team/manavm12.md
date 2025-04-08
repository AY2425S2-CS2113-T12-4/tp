# Manav Mehta - Project Portfolio Page

## Overview
Budget Buddy empowers students to take control of their finances by providing a simple and intuitive way to track
expenses, set budgets, and gain financial insights. By offering real-time updates, spending alerts, and budget
summaries, Budget Buddy helps students develop better financial habits, avoid overspending, and make informed financial
decisions without hassle.

## Summary of Contributions

### Delete Expense Function
* <b>Summary</b>:
  * Enables users to delete individual expense records by specifying the index.
  * Supports efficient management and cleanup of outdated or incorrect entries.

* <b>Justification</b>:
  * Keeps financial records clean and accurate by allowing removal of incorrect or outdated entries.

### List Expense Function
* <b>Summary</b>:
  * Displays all recorded expenses in reverse chronological order.

* <b>Justification</b>:
  * Allows users to easily view all recorded expenses and aids in indexing them for use in functions like `delete`.

### Edit Budget Function
* <b>Summary</b>:
  * Allows users to edit the name and/or amount of an existing budget using old/, a/, and c/ markers.
  * Supports partial edits: users can update just the amount, just the name, or both.

* <b>Justification</b>:
  * Enables quick updates to budget limits or category names without needing to recreate them.

### Check Budget Function
* <b>Summary</b>:
  * Allows users to check the remaining amount in their overall or category-specific budgets.
  * Displays remaining limit, total spending, and over-budget warnings if applicable.

* <b>Justification</b>:
  *  Displays remaining budget and total spending to help users track their financial status in real-time.

### Parser Class
* <b>Summary</b>:
  * Designed and implemented a generic Parser<T> abstract class to unify and simplify command parsing logic.
  * Implemented individual parser subclasses for commands such as AddParser, DeleteParser, EditBudgetParser, and ListParser.
  * Ensured each parser validates input format strictly and provides clear error messages.

* <b>Justification</b>:
  * Promotes extensibility and standardization across command input parsing.
  * Simplifies input validation and error handling for future feature additions.

### Other Significant Code Contributions
* Significantly contributed to the core logic of the `set-budget` and `add` command, including support for both overall and
  category-specific budgets with appropriate creation and update handling.
* Implemented strict argument order validation for commands like `add`, improving clarity and preventing ambiguous behavior.
* Refactored the time-based expense filtering logic for the `list` command and integrated detailed feedback for incorrect input.


### Developer Guide
* Structured the overall Developer Guide.
* Authored the High-Level Architecture section with a clear class diagram and explanation.
* Created the Logic Component Sequence Diagram to show how commands interact with the system.
* Documented core features and implementations including (sequence diagrams included):
  * Parser<T> class
  * Delete Expense
  * Edit Budget
  * Check Budget
  * List Command

### User Guide
* Wrote clear formatting notes in the beginning to explain format of UG
* Features added:
  * delete-expense
  * list-expense
  * edit-budget
  * check-budget
* Added warning notes about argument ordering and saving progress using bye.

### Code Contributed
* View my code contribution [here](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-02-21&tabOpen=true&tabType=authorship&tabAuthor=manavm12&tabRepo=AY2425S2-CS2113-T12-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false).
