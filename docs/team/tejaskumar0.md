# Tejas Ananth Kumar - Project Portfolio Page

## Overview
Budget Buddy enables students to manage their finances effortlessly by offering a simple and intuitive platform to track
expenses, set budgets, and gain financial insights. With real-time updates, spending alerts, and budget summaries, 
Budget Buddy helps students build better financial habits, avoid overspending, and make informed financial decisions 
with ease.

## Summary of Contributions

### Storage Manager Feature
* **Summary**:  
  Developed the `StorageManager` class to handle persistent saving and loading of user data (budgets, expenses, and alerts) to/from a local `.txt` file.
* **Justification**:  
  Enables data persistence across sessions, ensuring users don’t lose their financial records when they restart the app. Designed a readable format for efficient parsing and minimal errors.
* Enhancements added:
  * Introduced condition to only save active alerts.
  * Added logic to gracefully skip corrupted lines during file loading.
  * Added assertions and error messages to increase traceability during development.
  * Used BufferedReader and BufferedWriter for optimized file I/O performance.

### Budget Alert Feature
* **Summary**:  
  Implemented the `Alert` class and integrated it into the `BudgetManager` to allow users to set a custom expense alert threshold.
* Enhancements:
  Improved the Alert system to also notify users when their total expenses exactly match the alert threshold (e.g., budget is $500 and expenses are $500), with a dedicated warning message. Exceeding the threshold still triggers the standard over-budget alert.
* **Justification**:  
  Helps users manage overspending by notifying them when their total expenses exceed a predefined limit, promoting better financial discipline.
 
### Unit Tests
* Wrote unit tests for AlertCommand, EditAlertCommand, and DeleteAlertCommand.
* Verified alert triggers (both hit and exceed) through edge-case testing.

### Storage Reliability Tests:
* Simulated corrupted data scenarios to ensure the application skips malformed lines without crashing.
  
### Developer Guide
* Documented the storage logic with a UML class diagram.
* Explained how alert checking is triggered after adding new expenses.
* Created sequence diagrams for my implemented features AddAlert, EditAlert, DeleteAlert

### Code Contributed
* View my code contribution [here](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=Tejas%20&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-02-21&tabOpen=true&tabType=authorship&tabAuthor=tejaskumar0&tabRepo=AY2425S2-CS2113-T12-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false).