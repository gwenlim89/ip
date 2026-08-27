# UI Test Plan

This file records console UI test cases for the Larper chatbot.

## Test Case: add list mark unmark

Aim: Verify that todo, deadline, and event tasks can be added, listed, marked, unmarked, saved from a missing data file, and exited.

Inputs:
```text
todo read book
deadline return book /by 2026-08-23 no time
event meeting /from Mon 2pm /to Tue 4pm
list
mark 2
unmark 2
exit
```

Expected output:
```text
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 _
| |       __ _   _ __   _ __     ___   _ __
| |      / _` | | '__| | '_ \   / _ \ | '__|
| |___  | (_| | | |    | |_) | |  __/ | |
|_____|  \__,_| |_|    | .__/   \___| |_|
                       |_|
Fine day! I'm Larper.

 What can I do for you?

_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Got it. I've added this task:
 [T][ ] read book
 Now you have 1 task in the list.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Got it. I've added this task:
 [D][ ] return book (by: Aug 23 2026)
 Now you have 2 tasks in the list.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Got it. I've added this task:
 [E][ ] meeting (from: Aug 24 2026 1400 to: Aug 25 2026 1600)
 Now you have 3 tasks in the list.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Here are the tasks in your list:
 1. [T][ ] read book
 2. [D][ ] return book (by: Aug 23 2026)
 3. [E][ ] meeting (from: Aug 24 2026 1400 to: Aug 25 2026 1600)
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Nice! I've marked this task as done:
 [D][X] return book (by: Aug 23 2026)
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 OK, I've marked this task as not done yet:
 [D][ ] return book (by: Aug 23 2026)
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Bye. Hope to see you again soon!
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
```

## Test Case: find matching and missing tasks

Aim: Verify that find prompts for a search phrase, matches full phrases case-insensitively in descriptions only, shows original task numbers beside matches, and reports no matches.

Inputs:
```text
todo read book
deadline return library book /by 2026-06-06 no time
event book club /from aug 8 2pm /to aug 8 4pm
find
book
find
RETURN LIBRARY
find
Aug
exit
```

Expected output:
```text
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 _
| |       __ _   _ __   _ __     ___   _ __
| |      / _` | | '__| | '_ \   / _ \ | '__|
| |___  | (_| | | |    | |_) | |  __/ | |
|_____|  \__,_| |_|    | .__/   \___| |_|
                       |_|
Fine day! I'm Larper.

 What can I do for you?

_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Got it. I've added this task:
 [T][ ] read book
 Now you have 1 task in the list.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Got it. I've added this task:
 [D][ ] return library book (by: Jun 06 2026)
 Now you have 2 tasks in the list.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Got it. I've added this task:
 [E][ ] book club (from: Aug 08 2026 1400 to: Aug 08 2026 1600)
 Now you have 3 tasks in the list.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 What do you want me to find?
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 OK found it!!!
 [T][ ] read book (task no: 1)
 [D][ ] return library book (by: Jun 06 2026) (task no: 2)
 [E][ ] book club (from: Aug 08 2026 1400 to: Aug 08 2026 1600) (task no: 3)
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 What do you want me to find?
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 OK found it!!!
 [D][ ] return library book (by: Jun 06 2026) (task no: 2)
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 What do you want me to find?
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 oh no book found! Please retry again!!
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Bye. Hope to see you again soon!
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
```

Expected data file:
```text
T | 0 | read book
D | 0 | return library book | 2026-06-06 | no time
E | 0 | book club | 2026-08-08 | 1400 | 2026-08-08 | 1600
```

Expected data file:
```text
T | 0 | read book
D | 0 | return book | 2026-08-23 | no time
E | 0 | meeting | 2026-08-24 | 1400 | 2026-08-25 | 1600
```

## Test Case: deadline date formatting

Aim: Verify that Larper accepts a deadline date in yyyy-MM-dd format, stores it, and prints it in MMM dd yyyy format.

Inputs:
```text
deadline submit report /by 2019-10-15 2pm
list
exit
```

Expected output:
```text
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 _
| |       __ _   _ __   _ __     ___   _ __
| |      / _` | | '__| | '_ \   / _ \ | '__|
| |___  | (_| | | |    | |_) | |  __/ | |
|_____|  \__,_| |_|    | .__/   \___| |_|
                       |_|
Fine day! I'm Larper.

 What can I do for you?

_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Got it. I've added this task:
 [D][ ] submit report (by: Oct 15 2019 1400)
 Now you have 1 task in the list.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Here are the tasks in your list:
 1. [D][ ] submit report (by: Oct 15 2019 1400)
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Bye. Hope to see you again soon!
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
```

Expected data file:
```text
D | 0 | submit report | 2019-10-15 | 1400
```

## Test Case: deadline string date and time formatting

Aim: Verify that Larper accepts deadline dates with month names, short forms, mixed case, and saves deadline times in military time.

Inputs:
```text
deadline project draft /by AUGUST 6th 9:30AM
deadline dinner /by feb 7 7PM
deadline quiz /by 8 Sept 2026 1800
deadline typo check /by janurary 9 8am
list
exit
```

Expected output:
```text
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 _
| |       __ _   _ __   _ __     ___   _ __
| |      / _` | | '__| | '_ \   / _ \ | '__|
| |___  | (_| | | |    | |_) | |  __/ | |
|_____|  \__,_| |_|    | .__/   \___| |_|
                       |_|
Fine day! I'm Larper.

 What can I do for you?

_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Got it. I've added this task:
 [D][ ] project draft (by: Aug 06 2026 0930)
 Now you have 1 task in the list.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Got it. I've added this task:
 [D][ ] dinner (by: Feb 07 2026 1900)
 Now you have 2 tasks in the list.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Got it. I've added this task:
 [D][ ] quiz (by: Sep 08 2026 1800)
 Now you have 3 tasks in the list.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Got it. I've added this task:
 [D][ ] typo check (by: Jan 09 2026 0800)
 Now you have 4 tasks in the list.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Here are the tasks in your list:
 1. [D][ ] project draft (by: Aug 06 2026 0930)
 2. [D][ ] dinner (by: Feb 07 2026 1900)
 3. [D][ ] quiz (by: Sep 08 2026 1800)
 4. [D][ ] typo check (by: Jan 09 2026 0800)
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Bye. Hope to see you again soon!
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
```

Expected data file:
```text
D | 0 | project draft | 2026-08-06 | 0930
D | 0 | dinner | 2026-02-07 | 1900
D | 0 | quiz | 2026-09-08 | 1800
D | 0 | typo check | 2026-01-09 | 0800
```

## Test Case: event string date and time formatting

Aim: Verify that Larper accepts event dates with month names, short forms, mixed case, and saves event times in military time.

Inputs:
```text
event project sync /from FEB 7 2:30PM /to 8 Sept 2026 16:00
list
exit
```

Expected output:
```text
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 _
| |       __ _   _ __   _ __     ___   _ __
| |      / _` | | '__| | '_ \   / _ \ | '__|
| |___  | (_| | | |    | |_) | |  __/ | |
|_____|  \__,_| |_|    | .__/   \___| |_|
                       |_|
Fine day! I'm Larper.

 What can I do for you?

_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Got it. I've added this task:
 [E][ ] project sync (from: Feb 07 2026 1430 to: Sep 08 2026 1600)
 Now you have 1 task in the list.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Here are the tasks in your list:
 1. [E][ ] project sync (from: Feb 07 2026 1430 to: Sep 08 2026 1600)
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Bye. Hope to see you again soon!
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
```

Expected data file:
```text
E | 0 | project sync | 2026-02-07 | 1430 | 2026-09-08 | 1600
```

## Test Case: missing description and type

Aim: Verify that missing task descriptions and unknown task types show personalised exception messages.

Inputs:
```text
todo
what is this
exit
```

Expected output:
```text
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 _
| |       __ _   _ __   _ __     ___   _ __
| |      / _` | | '__| | '_ \   / _ \ | '__|
| |___  | (_| | | |    | |_) | |  __/ | |
|_____|  \__,_| |_|    | .__/   \___| |_|
                       |_|
Fine day! I'm Larper.

 What can I do for you?

_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Larper needs a task description before charging into battle.
 Please use one of these formats:
 todo DESCRIPTION
 deadline DESCRIPTION /by DATE TIME
 event DESCRIPTION /from START_DATE START_TIME /to END_DATE END_TIME
 Dates: 2019-10-15, 2/12/2019, Aug 6, August 6th, or Monday.
 Times: 2pm, 2:30pm, 1400, 14:00, or no time.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 oh watchu yapping on
 Please use one of these formats:
 todo DESCRIPTION
 deadline DESCRIPTION /by DATE TIME
 event DESCRIPTION /from START_DATE START_TIME /to END_DATE END_TIME
 Dates: 2019-10-15, 2/12/2019, Aug 6, August 6th, or Monday.
 Times: 2pm, 2:30pm, 1400, 14:00, or no time.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Bye. Hope to see you again soon!
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
```

## Test Case: date time and status exceptions

Aim: Verify invalid date/time inputs and repeated mark/unmark commands show exception messages without stopping the program.

Inputs:
```text
deadline return book /by 2026-03-09
deadline return book /by 2pm
deadline return book /by no time
event meeting /from 9 mar /to 10 mar 4pm
deadline return book /by 2026-03-09 no time
mark 1
mark 1
unmark 1
unmark 1
exit
```

Expected output:
```text
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 _
| |       __ _   _ __   _ __     ___   _ __
| |      / _` | | '__| | '_ \   / _ \ | '__|
| |___  | (_| | | |    | |_) | |  __/ | |
|_____|  \__,_| |_|    | .__/   \___| |_|
                       |_|
Fine day! I'm Larper.

 What can I do for you?

_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Larper found the deadline date, but no time was given.
 Time is optional, so please confirm with 2pm, 2:30pm, 1400, 14:00, or no time.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Where the date is? Larper needs the deadline date.
 Dates can look like 2019-10-15, 2/12/2019, Aug 6, August 6th, or Monday.
 Try: deadline DESCRIPTION /by 2019-10-15 1400
 Or: event DESCRIPTION /from Aug 6 2pm /to Aug 6 4pm
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Where the date is? Larper needs the deadline date.
 Dates can look like 2019-10-15, 2/12/2019, Aug 6, August 6th, or Monday.
 Try: deadline DESCRIPTION /by 2019-10-15 1400
 Or: event DESCRIPTION /from Aug 6 2pm /to Aug 6 4pm
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Larper found the event start date, but no time was given.
 Time is optional, so please confirm with 2pm, 2:30pm, 1400, 14:00, or no time.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Got it. I've added this task:
 [D][ ] return book (by: Mar 09 2026)
 Now you have 1 task in the list.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Nice! I've marked this task as done:
 [D][X] return book (by: Mar 09 2026)
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 This task is already marked. Lock in and pick one that is not done yet.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 OK, I've marked this task as not done yet:
 [D][ ] return book (by: Mar 09 2026)
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 This task is already unmarked. Quit messing around and pick a done task.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Bye. Hope to see you again soon!
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
```

## Test Case: missing deadline time follow-up

Aim: Verify that typing no time after a missing deadline time prompt completes the pending deadline.

Inputs:
```text
todo larp
deadline vnervn/by 2026-03-09
no time
list
exit
```

Expected output:
```text
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 _
| |       __ _   _ __   _ __     ___   _ __
| |      / _` | | '__| | '_ \   / _ \ | '__|
| |___  | (_| | | |    | |_) | |  __/ | |
|_____|  \__,_| |_|    | .__/   \___| |_|
                       |_|
Fine day! I'm Larper.

 What can I do for you?

_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Got it. I've added this task:
 [T][ ] larp
 Now you have 1 task in the list.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Larper found the deadline date, but no time was given.
 Time is optional, so please confirm with 2pm, 2:30pm, 1400, 14:00, or no time.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Got it. I've added this task:
 [D][ ] vnervn (by: Mar 09 2026)
 Now you have 2 tasks in the list.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Here are the tasks in your list:
 1. [T][ ] larp
 2. [D][ ] vnervn (by: Mar 09 2026)
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Bye. Hope to see you again soon!
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
```

## Test Case: delete task from middle

Aim: Verify that a delete command embedded in a sentence removes the numbered task, keeps the remaining task order correct, and saves the final task list.

Inputs:
```text
todo alpha
deadline beta /by 2026-03-09 no time
event gamma /from 10 mar 2pm /to 11 mar 4pm
please delete 2
list
exit
```

Expected output:
```text
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 _
| |       __ _   _ __   _ __     ___   _ __
| |      / _` | | '__| | '_ \   / _ \ | '__|
| |___  | (_| | | |    | |_) | |  __/ | |
|_____|  \__,_| |_|    | .__/   \___| |_|
                       |_|
Fine day! I'm Larper.

 What can I do for you?

_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Got it. I've added this task:
 [T][ ] alpha
 Now you have 1 task in the list.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Got it. I've added this task:
 [D][ ] beta (by: Mar 09 2026)
 Now you have 2 tasks in the list.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Got it. I've added this task:
 [E][ ] gamma (from: Mar 10 2026 1400 to: Mar 11 2026 1600)
 Now you have 3 tasks in the list.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Poof it gone now:
 [D][ ] beta (by: Mar 09 2026)
 Now you have 2 tasks in the list.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Here are the tasks in your list:
 1. [T][ ] alpha
 2. [E][ ] gamma (from: Mar 10 2026 1400 to: Mar 11 2026 1600)
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Bye. Hope to see you again soon!
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
```

Expected data file:
```text
T | 0 | alpha
E | 0 | gamma | 2026-03-10 | 1400 | 2026-03-11 | 1600
```

## Test Case: invalid delete inputs

Aim: Verify that non-number delete input and out-of-range delete numbers show delete-specific exception messages.

Inputs:
```text
todo alpha
delete two
delete 7
delete 0
list
exit
```

Expected output:
```text
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 _
| |       __ _   _ __   _ __     ___   _ __
| |      / _` | | '__| | '_ \   / _ \ | '__|
| |___  | (_| | | |    | |_) | |  __/ | |
|_____|  \__,_| |_|    | .__/   \___| |_|
                       |_|
Fine day! I'm Larper.

 What can I do for you?

_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Got it. I've added this task:
 [T][ ] alpha
 Now you have 1 task in the list.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Deletion needs a number, not characters.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 The number provided is invalid. Try again.
 You have 1 task(s) in the list, so the number must be from 1 to 1.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 The number provided is invalid. Try again.
 You have 1 task(s) in the list, so the number must be from 1 to 1.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Here are the tasks in your list:
 1. [T][ ] alpha
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Bye. Hope to see you again soon!
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
```

## Test Case: delete from empty list

Aim: Verify that deleting from an empty task list shows the empty deletion exception message.

Inputs:
```text
delete 1
exit
```

Expected output:
```text
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 _
| |       __ _   _ __   _ __     ___   _ __
| |      / _` | | '__| | '_ \   / _ \ | '__|
| |___  | (_| | | |    | |_) | |  __/ | |
|_____|  \__,_| |_|    | .__/   \___| |_|
                       |_|
Fine day! I'm Larper.

 What can I do for you?

_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Task list is empty nothing to delete here!!!
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Bye. Hope to see you again soon!
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
```

## Test Case: load tasks from data file

Aim: Verify that Larper loads saved todo, deadline, and event tasks from the data file before handling commands.

Initial data file:
```text
T | 1 | read book
D | 0 | return library book | 2026-06-06 | no time
E | 0 | project meeting | 8 aug 2pm | 8 aug 4pm
```

Inputs:
```text
list
exit
```

Expected output:
```text
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 _
| |       __ _   _ __   _ __     ___   _ __
| |      / _` | | '__| | '_ \   / _ \ | '__|
| |___  | (_| | | |    | |_) | |  __/ | |
|_____|  \__,_| |_|    | .__/   \___| |_|
                       |_|
Fine day! I'm Larper.

 What can I do for you?

_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Here are the tasks in your list:
 1. [T][X] read book
 2. [D][ ] return library book (by: Jun 06 2026)
 3. [E][ ] project meeting (from: Aug 08 2026 1400 to: Aug 08 2026 1600)
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Bye. Hope to see you again soon!
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
```

Expected data file:
```text
T | 1 | read book
D | 0 | return library book | 2026-06-06 | no time
E | 0 | project meeting | 8 aug 2pm | 8 aug 4pm
```

## Test Case: load legacy deadline data

Aim: Verify that older saved deadline lines are loaded as LocalDate deadlines instead of crashing the program.

Initial data file:
```text
T | 1 | read book
D | 0 | return library book | by 2/12/2019 1800
D | 1 | practise work | 7 july no time 
```

Inputs:
```text
list
exit
```

Expected output:
```text
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 _
| |       __ _   _ __   _ __     ___   _ __
| |      / _` | | '__| | '_ \   / _ \ | '__|
| |___  | (_| | | |    | |_) | |  __/ | |
|_____|  \__,_| |_|    | .__/   \___| |_|
                       |_|
Fine day! I'm Larper.

 What can I do for you?

_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Here are the tasks in your list:
 1. [T][X] read book
 2. [D][ ] return library book (by: Dec 02 2019 1800)
 3. [D][X] practise work (by: Jul 07 2026)
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Bye. Hope to see you again soon!
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
```
