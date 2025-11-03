---
layout: page
title: User Guide
---

SWEatless is a desktop application for **CS2103/T teaching staff who favour CLI usage** and want to **access and manage student and team information efficiently**. Since many CS2103/T projects use CLI, teaching staff should be familiar with it, and may see value in integrating it with their student and team management.

SWEatless allows for intuitive student-team management using easy-to-remember commands. Furthermore, it supports direct access to the Github accounts of both students and teams, allowing you to keep track of progress with a single click!

The quick-start guide is the step-by-step instruction to get SWEatless running on your device. It is important to understand the constraints on each field before referring to the feature list. FAQs and known issues are documented at the bottom of the user guide.

* Table of Contents
{:toc}

---

## Command summary

Command word is case-insensitive. (i.e. `list` is equivalent to `LiSt`)

| Action               | Format, Examples                                                                                                                                    |
| -------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Create Student**   | `create-s n/NAME p/PHONE_NUMBER e/EMAIL g/GITHUB_USERNAME​` <br> e.g., `create-s n/James Ho p/9716319 e/jamesho@example.com g/jamesho` |
| **Delete Student**   | `delete-s INDEX` or `delete-s e/EMAIL`<br> e.g., `delete-s 3` or `delete-s e/johndoe@example.com`                         |
| **Edit Student**     | `edit-s INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [g/GITHUB_USERNAME]​`<br> e.g.,`edit-s 2 n/James Lee e/jameslee@example.com`          |
| **Find**             | `find n/[ONE_OR_MORE_NAMES] t/[ONE_OR_MORE_TEAM_NAMES]`<br> e.g., `find n/James Jake t/F12-3`                                                                     |
| **List**             | `list`                                                                                                                                              |
| **Create Team**      | `create-t t/TEAM_NAME` <br> e.g., `create-t F12-3`                                                                                            |
| **Delete Team**      | `delete-t t/TEAM_NAME`<br> e.g., `delete-t F12-3`                                                                                             |
| **Add To Team**      | `team-add INDEX t/TEAM_NAME` <br> e.g., `team-add 1 t/F12-3`                                                                                  |
| **Remove from Team** | `team-remove INDEX`<br> e.g., `team-remove 3`                             
|                                            |
| **Import**           | `import f/FILE_NAME`                                                                                                                                |
| **Export**           | `export f/FILE_NAME`                                                                                                                              |
| **Clear**            | `clear`                                                                                                                                             |
| **Exit**             | `exit`                                                                                                                                              |
| **Help**             | `help`                                                                                                                                              |

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2526S1-CS2103T-F12-3/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for SWEatless.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar SWEatless.jar` command to run the application.<br>
   A GUI similar to the one below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

    - `list` : Lists all contacts and all created teams.

    - `create-s n/John Doe p/98765432 e/johnd@example.com g/johnd` : Adds a contact named `John Doe` to SWEatless.

    - `delete-s 3` : Deletes the 3rd contact shown in the current list.

    - `delete-s e/johnd@example.com` : Deletes the contact with the specified email.

    - `clear` : Deletes all contacts.

    - `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

---

## Constraints

### Name

- Format: Names should only contain alphabetic characters, spaces and '/', must not be blank and must be at most 100 characters long
- Rules:
    - Must start with a letter
    - Can contain letters, ‘/’, and spaces
    - Cannot start with whitespace
    - Cannot contain special characters like !@#$%^&\*() and numbers

Valid Examples: John Doe, Alice, Mary Jane Smith, John S/O Doe
Invalid Examples: John (starts with space), John@Doe (special character), empty string, Alice123 (numbers)

### Phone Number

- Format: Phone numbers should only contain numbers, and should be between 3 to 25 digits long
- Rules:
    - Must contain only digits (0-9)
    - Must be at least 3 digits long
    - Must be at most 25 digits long
    - No spaces, dashes, parentheses, or other characters allowed

Valid Examples: 123, 1234567890, 999  
Invalid Examples: 12 (too short), 123-456-7890 (contains dashes), abc123 (contains letters)

### Email

- Format: Emails should be of the format local-part@domain and adhere to specific constraints
- Rules:
    - The entire email address must not exceed 255 characters
    - Local part (before @):
        - Only alphanumeric characters and special characters: +\_.-
        - Cannot start or end with special characters
        - Consecutive special characters are not allowed
    - Domain part (after @):
        - Must have at least 2 characters in the final domain label
        - Each domain label must start and end with alphanumeric characters
        - Domain labels can contain hyphens but not at the start/end
        - Must have at least one period separating domain labels

Valid Examples: user@example.com, test.email+tag@domain.co.uk, user123@sub.domain.org  
Invalid Examples: user@domain (no TLD), .user@domain.com (starts with dot), user@.domain.com (starts with dot)

### Github

- Format: Github usernames must be 1-39 characters, alphanumeric or hyphens, cannot start or end with a hyphen, and cannot have consecutive hyphens, following the [validity rules of Github](https://docs.github.com/en/enterprise-cloud@latest/admin/managing-iam/iam-configuration-reference/username-considerations-for-external-authentication#about-usernames-with-external-authentication)
- Rules:
    - Must be 1-39 characters long
    - Can contain letters (a-z, A-Z), numbers (0-9), and hyphens (-)
    - Cannot start with a hyphen
    - Cannot end with a hyphen
    - Cannot have consecutive hyphens (--)

Valid Examples: john-doe, alice123, user-name, a (single character)  
Invalid Examples: -username (starts with hyphen), username- (ends with hyphen), user--name (consecutive hyphens), empty string

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:** Make sure the Github username is entered properly!
<br>
<br>
SWEatless is not responsible for ensuring that the Github username leads to an actual profile. Please make sure that the Github username entered leads to a profile that exists.
</div>

### Team Name

- Format: Team names should adhere to specific constraints (according to CS2103T past team names)
- Rules:
    - First character (day): W, T, F
    - Second and third character (time): 08, 09, 10, 11, 12, 13, 14, 15, 16, 17
    - (Optional) Fourth character (a / b): a, b
    - Fourth / Fifth character (hyphen): -
    - Fifth / Sixth character (team number): 1, 2, 3, 4


Valid Examples: F12-3, T11-1, F08a1, W17-2
Invalid Example: S12-3 (invalid day), F11-6 (invalid team number), T01-2 (invalid time), T03f-2 (invalid subgroup)

When clicking on the team Github hyperlink, it will direct you to the Github organization associated with the team.
- `AY2526S1-CS2103T-` is prepended to the team name to match the convention of the Github organization name.
- The academic year, semester, and module is currently specified to be `AY2526`, `S1`, and `CS2103T` respectively. Since the scope of the SWEatless is restricted to the specific year, semester, and module, these fields are not adjustable in the current version.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:** Valid team format may not reflect actual teams!
<br>
<br>
Note that some team names may be valid, but may not directly correspond to an actual team. 
<br>
<br>
SWEatless only does a high-level RegEx check on the validity of the team name. The user must ensure that the team name corresponds to an actual team.
</div>

### Index

- Format: Index should only contain numbers
- Rules:
    - One-based indexing is used
    - Must be a non-zero positive integer

Valid Examples: 1, 2, 5, 90
Invalid Examples: 0 (is not non-zero), -1 (is not positive), abc (contains non-numeric characters)

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

- Command word is case-insensitive. (i.e. `list` is equivalent to `LiSt`)

- Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

- Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

- Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

- If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.

- Any parameter constraints for the commands can be found [here](#constraints)
</div>

### Creating a student: `create-s`

Creates a student to add to SWEatless.

Format: `create-s n/NAME p/PHONE_NUMBER e/EMAIL g/GITHUB_USERNAME​`

Examples:

- `create-s n/John Doe p/98765432 e/johnd@example.com g/johnd`
- `create-s n/Betsy Crowe e/betsycrowe@example.com p/97121323 g/betsycrowe`

Multiple students can have the same Name. However, Phone number, Email and Github username must be unique.
No students can share the same Phone number, Email or Github username.

If a typo was made when creating a student, refer to the [Editing a student](#editing-a-student-edit-s) command to rectify the mistake.

### Deleting a student: `delete-s`

Deletes the specified student from SWEatless.

Format: `delete-s INDEX` OR `delete-s e/EMAIL`

- Deletes the student at the specified `INDEX` OR the student with the matching `EMAIL`.
- The index refers to the index number shown in the displayed student list.
- The index **must be a positive integer** 1, 2, 3, …​
- The email matching is **case-insensitive** (e.g., `JohnDoe@Example.com` will match `johndoe@example.com`).
- Only one method (index or email) can be used at a time.

Examples:

- `delete-s 1` Deletes the 1st student in the list.
- `delete-s e/johndoe@example.com` Deletes the student with email `johndoe@example.com`.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
The current version does not support undoing operations.
<br>
<br>
Hence, if a delete was incorrectly performed, the affected team (and students in that team) will need to be added back manually. To avoid accidental data loss, you can save a copy of the address book by exporting it. Please refer to [Exporting data to a JSON file](#exporting-data-to-a-json-file-export).
<br>
<br>
Therefore, only delete students if you are confident that you are deleting it correctly.
</div>

### Editing a student: `edit-s`

Edits an existing student in SWEatless.

Format: `edit-s INDEX [n/NAME] [p/PHONE] [e/EMAIL] [g/GITHUB_USERNAME]`

- Edits the student at the specified `INDEX`.
- The index refers to the index number shown in the displayed student list. This might be a filtered list which differs from the main list.
- At least one of the optional fields must be provided.
- Existing values will be updated to the input values.

Examples:

- `edit-s 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st student to be `91234567` and `johndoe@example.com` respectively.
- `edit-s 2 n/Betsy Crower` Edits the name of the 2nd student to be `Betsy Crower`

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
The current version does not support undoing operations.
<br>
<br>
Hence, if an edit was incorrectly performed, the affected fields will need to be manually edited to the original values. Hence, if a delete was incorrectly performed, the affected team (and students in that team) will need to be added back manually. To avoid accidental data loss, you can save a copy of the address book by exporting it. Please refer to [Exporting data to a JSON file](#exporting-data-to-a-json-file-export).
<br>
<br>
Therefore, edit the student details only if you are confident that you are updating it correctly.
</div>

### Locating persons by name or team: `find`

Finds students whose names contain any of the specified keywords or who belong in any of the specified teams (case-insensitive) and displays them as a list with index numbers.

Format: `find n/[ONE_OR_MORE_NAMES] t/[ONE_OR_MORE_TEAM_NAMES]`

- At least one of the optional fields must be provided.
- The search is case-insensitive. e.g `hans` will match `Hans`
- The order of the keywords does not matter.
- Only the name and/or team name is searched.
- Only full words will be matched e.g. `Han` will not match `Hans`

Examples:

- `find n/John` returns `john` and `John Doe`
- `find n/alex david` returns `Alex Yeoh`, `David Li`<br>
  *Students matching at least one name keyword will be returned (i.e. `OR` search)*
  ![result for 'find n/alex david'](images/findAlexDavidResult.png)
- `find t/F12-3` returns all students from team `F12-3`
- `find t/F12-3 T11-2` returns all students from team `F12-3` and `T11-2`<br>
  *Students matching at least one team keyword will be returned (i.e. `OR` search)*
- `find n/alex david t/F12-3 T11-2` returns all students from team `F12-3` and `T11-2` with names `Alex` or `David`<br>
  *If both `n/` and `/t` tags are used, students with the queried names in the specified teams will be returned (i.e. `AND` search)*

To remove filters on the display, please refer to [Listing all students](#listing-all-students-and-teams-list).

### Listing all students and teams: `list`

Shows a list of all students and teams created so far in SWEatless.

Format: `list`

Examples:

- `list` followed by `delete-s 2` deletes the 2nd student in SWEatless.
- `find n/Betsy` followed by `delete-s 1` deletes the 1st student in the results of the `find` command.
- `delete-s e/betsy@example.com` deletes the student with the email `betsy@example.com`.

### Creating a team: `create-t`

Creates a team to add to SWEatless.

Format: `create-t t/TEAM_NAME`

Examples:

- `create-t t/F12-3`

### Deleting a team: `delete-t`

Deletes the specified team from SWEatless.

Format: `delete-t t/TEAM_NAME`

Examples:

- `delete-t t/F12-3`

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
The current version does not support undoing operations.
<br>
<br>
Hence, if a delete was incorrectly performed, the affected team (and students in that team) will need to be added back manually. To avoid accidental data loss, you can perform `export` to save the current state.
<br>
<br>
Therefore, only delete students if you are confident that you are deleting it correctly.
</div>

### Adding a student to a team: `team-add`

Adds the specified student to the specified team in SWEatless.

Each team can only have 5 students.

Each student can only be added to 1 team at a time.

Remember to create a team before trying team-add!

Format: `team-add INDEX t/TEAM_NAME`

Examples:

- `team-add 2 t/F12-3`

### Removing a student from a team: `team-remove`

Removes the specified student from the specified team in SWEatless.

Format: `team-remove INDEX`

Examples:

- `team-remove 1`

### Importing data from a JSON file: `import`

Imports data from a JSON file. Allows users to directly get information from a file without manually editing `sweatless_storage.json`. Import file data will be copied directly into `sweatless_storage.json`.
If the file is not a valid SWEatless json file, import will be rejected.
<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
<br>
<br>
Imported data will overwrite old sweatless_storage.json data, make sure important data is exported before importing new data!
<br>
<br>
Exported files will always be valid. Invalid files must have been wrongfully, manually edited and will be rejected with no validation or suggestions for fix. Suggestions for fixing invalid files may be implemented in proposed future features.
</div>


Format: `import f/FILE_PATH`

- The specified file must exist and be a valid SWEatless address book JSON file.
- File paths are resolved inside the data/ folder.
- The imported file replaces the current address book data.
- If the file cannot be read or is invalid, SWEatless will display an error message.
- File name should be alphanumeric, and may contain `._-`, except at the start and the end.
- The file must have a .json extension (it will be added automatically if omitted).

Examples:

- `import f/my_import.json` Imports address book data from data/my_import.json.
- `import f/my_import` Imports address book data from data/my_import.json.
- `import f/_myimport.json` Will not import. `_` not allowed at the start

### Exporting data to a JSON file: `export`

Exports the current SWEatless address book data into a JSON file. Allows users to capture data at a point of time prior to making further edits.

Format: `export f/FILE_PATH`

- File paths are resolved inside the data/ folder.
- If the specified file already exists, it will be overwritten.
- File name should be alphanumeric, and may contain `._-`, except at the start and the end.
- The file must have a .json extension (it will be added automatically if omitted).

Examples:

- `export f/my_export.json` Exports the current address book to data/my_export.json.
- `export f/my_export` Exports address book data from data/my_export.json.
- `export f/_myexport.json` Will not export. `_` not allowed at the start

### Clearing all entries: `clear`

Clears all entries from SWEatless.

Format: `clear`

### Exiting the program: `exit`

Exits the program.

Format: `exit`

### Viewing help: `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Saving the data

SWEatless data are saved in `..\data\sweatless_storage.json` automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

SWEatless data are saved automatically as a JSON file `[JAR file location]/data/sweatless_storage.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
<br>
<br>
If your changes to the data file makes its format invalid, SWEatless will **1. Save the invalid JSON as `sweatless_storage_corrupted_TIMESTAMP`.** and **2. Delete the original data file.** **3. Load the sample json file on the next start. Original data must be restored manually from the corrupted json file.**
<br>
<br>
Certain edits can cause the SWEatless to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

---

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous SWEatless home folder.

---

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

---

