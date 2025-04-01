# User Guide

## Introduction

{Give a product intro}

## Quick Start

{Give steps to get started quickly}

1. Ensure that you have Java 17 or above installed.
1. Down the latest version of `Duke` from [here](http://link.to/duke).

## Features 

{Give detailed description of each feature}

### Adding an expense: `add`
Adds a new item to the list of expenses. Users can choose to leave `CATEGORY` and `DATE TIME` blank, in which case:
 - The expense will default to the **Overall Budget** if `CATEGORY` is not provided or cannot be found.
 - The `DATE TIME` will default to the **current date and time** if left blank or formatted incorrectly.

Format: `add <AMOUNT> c/ <CATEGORY> d/ <DESCRIPTION> t/ <DATE TIME>`

* * The `AMOUNT` can be any positive number, if a number too large in entered, it will be displayed as `Infinity`.
* The `CATEGORY` can be in natural language format. If it does not exist, the expense defaults to the **Overall Budget**.
* The `DESCRIPTION` can be in natural language format.  
* The `CATEGORY` and `DESCRIPTION` cannot contain `d/` and `t/`
* The `DATE TIME` has to follow the format of `MMM dd yyyy at HH:mm`, where **only** the first letter of `MMM` is 
capitalized, and HH:mm follows the 24-hour clock format. 
* The `DATE TIME` must follow the format `MMM dd yyyy at HH:mm`, where:
  - **Only the first letter of `MMM` is capitalized** (e.g., `Jan 15 2025 at 11:30`).
  - **`HH:mm` follows the 24-hour clock format** (e.g., `13:45 for 1:45 PM`). 
  - If an incorrect date format is provided, the **current date and time** will be used instead.
* Users **must still include `c/` and `t/` even if leaving them blank**.

Example of usage: 

`add 200 c/ d/ food t/`

`add 100 c/ Overall d/ bus fares t/`

`add 50 c/ Overall d/ cab fares t/ Jan 15 2025 at 11:30`

### Editing an expense: `edit-expense`
Edit an item on the list of expenses. Expense `AMOUNT`, `DESCRIPTION` or `DATE TIME` can be edited, with all three being 
optional but requiring minimally one.

Format: `edit-expense <INDEX> a/ <AMOUNT> d/ <DESCRIPTION> t/ <DATE TIME>`

* The `INDEX` refers to the index of the expense when the `list` function is used.
* The `AMOUNT` can be any positive number, if a number too large in entered, it will be displayed as `Infinity`.
* The `DESCRIPTION` can be in natural language format.
* The `DESCRIPTION` cannot contain `d/` and `t/`
* The `DATE TIME` has to follow the format of `MMM dd yyyy at HH:mm`, where **only** the first letter of `MMM` is
  capitalized, and HH:mm follows the 24-hour clock format.
* The `DATE TIME` must follow the format `MMM dd yyyy at HH:mm`, where:
  - **Only the first letter of `MMM` is capitalized** (e.g., `Jan 15 2025 at 11:30`).
  - **`HH:mm` follows the 24-hour clock format** (e.g., `13:45 for 1:45 PM`).
  - If an incorrect date format is provided, the **current date and time** will be used instead.
* Users can omit `a/`, `c/` **or** `t/` but cannot omit all three.

Example of usage:

`edit-expense 1 a/ 200`

`edit-expense 1 a/ 100 d/ bus fares`

`edit-expense 1 t/ Jan 15 2025 at 11:30`

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: {your answer here}

## Command Summary

{Give a 'cheat sheet' of commands here}

* Add todo `todo n/TODO_NAME d/DEADLINE`
