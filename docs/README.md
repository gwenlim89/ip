# Larper User Guide

Larper is a desktop task manager for todos, deadlines, and events. It is optimized for typing commands quickly
while still showing your current agenda in the GUI.

Larper's personality is simple: it is a productivity expert, allegedly.

## Contents

- [Quick start](#quick-start)
- [Command format notes](#command-format-notes)
- [Features](#features)
- [FAQ](#faq)
- [Known issues](#known-issues)
- [Command summary](#command-summary)
- [Acknowledgements](#acknowledgements)

## Quick Start

1. Ensure that Java `25` or later is installed on your computer.
2. Download the latest `larper.jar` file from the project's GitHub release page.
3. Copy the JAR file to the folder you want to use as Larper's home folder.
4. Open a terminal in that folder and run:

   ```sh
   java -jar larper.jar
   ```

5. Type a command into the command box and press `Enter`.
6. Type `help` to see the supported commands inside the app.

Example commands to try:

```text
todo read lecture notes
deadline submit iP /by 2026-09-18 2359
event project meeting /from 2026-09-17 2pm /to 2026-09-17 4pm
list
```

## Command Format Notes

- Words in `UPPER_CASE` are parameters you should replace.
- `NUMBER` means the task number shown in the agenda or task list.
- `[OPTIONAL]` means that the part can be omitted.
- `TAG...` means that one or more tags can be provided.
- Tags are one-word labels. You may type them as `school` or `#school`.
- Commands are case-sensitive. For example, use `todo`, not `Todo`.
- Descriptions cannot contain `|`, because Larper uses that character to save task fields safely.
- Dates can be typed as `2026-09-18`, `18/9/2026`, `18 Sep 2026`, `Sep 18 2026`, or a weekday
  such as `friday`.
- Times can be typed as `2359`, `23:59`, `2pm`, or `2:30pm`.
- Use `no time` when a deadline or event date has no specific time.

## Features

### Viewing Help: `help`

Shows a compact list of commands Larper understands.

Format:

```text
help
```

### Adding A Todo: `todo`

Adds a todo task without a date.

Format:

```text
todo DESCRIPTION [TAG...]
```

Examples:

```text
todo read lecture notes
todo clean desk #home
```

### Adding A Deadline: `deadline`

Adds a task that must be done by a date, with an optional time.

Format:

```text
deadline DESCRIPTION [TAG...] /by DATE [TIME]
```

Examples:

```text
deadline submit iP /by 2026-09-18 2359
deadline return library book #school /by 18 Sep 2026 no time
```

If you give a date but leave out the time, Larper will ask for the missing time. Reply with a supported time
such as `2pm`, or type `no time`.

### Adding An Event: `event`

Adds a task that happens from one date/time to another date/time.

Format:

```text
event DESCRIPTION [TAG...] /from START_DATE [START_TIME] /to END_DATE [END_TIME]
```

Examples:

```text
event project meeting /from 2026-09-17 2pm /to 2026-09-17 4pm
event workshop #school /from friday 9am /to friday 11am
```

If a start or end time is missing, Larper will ask for it. Reply with a supported time or `no time`.

### Listing Tasks: `list`

Shows every task in the current agenda.

Format:

```text
list
```

### Marking A Task As Done: `mark`

Marks a task as completed.

Format:

```text
mark NUMBER
```

Example:

```text
mark 2
```

### Marking A Task As Not Done: `unmark`

Marks a completed task as not completed.

Format:

```text
unmark NUMBER
```

Example:

```text
unmark 2
```

### Deleting A Task: `delete`

Deletes a task from the agenda.

Format:

```text
delete NUMBER
```

Example:

```text
delete 3
```

### Finding Tasks By Text: `find`

Searches task descriptions and exact tag text.

Format:

```text
find
SEARCH_PHRASE
```

Example:

```text
find
school
```

Larper first prompts for the search phrase, then shows matching tasks.

### Finding Tasks By Tag: `find tag`

Searches for tasks with an exact tag.

Format:

```text
find tag TAG
```

Examples:

```text
find tag school
find tag #school
```

### Adding Tags: `tag`

Adds one or more tags to an existing task.

Format:

```text
tag NUMBER TAG...
```

Examples:

```text
tag 1 school
tag 1 #school urgent
```

### Removing Tags: `untag`

Removes one or more tags from an existing task.

Format:

```text
untag NUMBER TAG...
```

Example:

```text
untag 1 urgent
```

### Exiting The App: `exit`

Exits Larper.

Format:

```text
exit
```

### Saving Data

Larper saves the task list automatically after commands that change the agenda. The data file is stored at:

```text
data/larperdata.txt
```

If the data file is missing, Larper starts with an empty agenda and creates the file again when tasks are saved.

Advanced users may edit the data file directly, but invalid lines may be skipped when Larper loads the file
again. Back up the file before editing it manually.

### Error Handling

Larper shows command errors as highlighted messages in the GUI. Examples of handled errors include:

- Unknown commands, such as `delet 2`
- Missing task descriptions
- Descriptions containing `|`
- Invalid task numbers
- Missing or invalid tags
- Missing dates or times
- Missing or malformed data files

## FAQ

**Q: Do I need to save manually?**

A: No. Larper saves automatically after commands such as `todo`, `deadline`, `event`, `mark`, `unmark`,
`delete`, `tag`, and `untag`.

**Q: Can I use Larper from the terminal?**

A: Yes. The same command engine supports the console version and the JavaFX GUI.

**Q: Why does Larper ask for a time after I enter a deadline or event?**

A: The date was understood, but the time was missing. Type a time such as `1400` or `2pm`, or type `no time`.

## Known Issues

1. Commands are case-sensitive.
2. Tags can contain only one alphanumeric word.
3. The GUI command box is designed for single-line commands.

## Command Summary

Action | Format | Example
--- | --- | ---
Help | `help` | `help`
Add todo | `todo DESCRIPTION [TAG...]` | `todo read notes #school`
Add deadline | `deadline DESCRIPTION [TAG...] /by DATE [TIME]` | `deadline submit iP /by 2026-09-18 2359`
Add event | `event DESCRIPTION [TAG...] /from START_DATE [START_TIME] /to END_DATE [END_TIME]` | `event meeting /from friday 2pm /to friday 4pm`
List tasks | `list` | `list`
Mark done | `mark NUMBER` | `mark 2`
Mark not done | `unmark NUMBER` | `unmark 2`
Delete task | `delete NUMBER` | `delete 3`
Find text | `find`, then `SEARCH_PHRASE` | `find`, then `school`
Find tag | `find tag TAG` | `find tag school`
Add tags | `tag NUMBER TAG...` | `tag 1 school urgent`
Remove tags | `untag NUMBER TAG...` | `untag 1 urgent`
Exit | `exit` | `exit`

## Acknowledgements

- This project was built from the [SE-EDU Duke project template](https://github.com/se-edu/duke).
- The JavaFX GUI structure was adapted from the [SE-EDU JavaFX tutorial](https://se-education.org/guides/tutorials/javaFxPart1.html).
- This user guide follows the structure recommended in the CS2103/T Week 6 iP instructions and uses the
  [AddressBook Level 3 User Guide](https://se-education.org/addressbook-level3/UserGuide.html) as a formatting
  benchmark.
- Profile images are project assets stored in `src/main/resources/images`. If any of them were downloaded from an
  external source, add the exact source here before final submission.
