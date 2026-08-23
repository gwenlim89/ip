# UI Test Plan

This file records console UI test cases for the Larper chatbot.

## Test Case: add list mark unmark

Aim: Verify that todo, deadline, and event tasks can be added, listed, marked, unmarked, and exited.

Inputs:
```text
todo read book
deadline return book /by Sunday no time
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
 [D][ ] return book (by: 23 aug no time)
 Now you have 2 tasks in the list.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Got it. I've added this task:
 [E][ ] meeting (from: 24 aug 2pm to: 25 aug 4pm)
 Now you have 3 tasks in the list.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Here are the tasks in your list:
 1. [T][ ] read book
 2. [D][ ] return book (by: 23 aug no time)
 3. [E][ ] meeting (from: 24 aug 2pm to: 25 aug 4pm)
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Nice! I've marked this task as done:
 [D][X] return book (by: 23 aug no time)
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 OK, I've marked this task as not done yet:
 [D][ ] return book (by: 23 aug no time)
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Bye. Hope to see you again soon!
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
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
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 oh watchu yapping on
 Please use one of these formats:
 todo DESCRIPTION
 deadline DESCRIPTION /by DATE TIME
 event DESCRIPTION /from START_DATE START_TIME /to END_DATE END_TIME
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Bye. Hope to see you again soon!
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
```

## Test Case: date time and status exceptions

Aim: Verify invalid date/time inputs and repeated mark/unmark commands show exception messages without stopping the program.

Inputs:
```text
deadline return book /by 9 mar
deadline return book /by 2pm
deadline return book /by no time
event meeting /from 9 mar /to 10 mar 4pm
deadline return book /by 9 mar no time
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
 Time is optional, so please confirm: add a time, or type no time.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Where the date is? Larper needs the deadline date.
 Try: deadline DESCRIPTION /by DATE TIME
 Or: event DESCRIPTION /from START_DATE START_TIME /to END_DATE END_TIME
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Where the date is? Larper needs the deadline date.
 Try: deadline DESCRIPTION /by DATE TIME
 Or: event DESCRIPTION /from START_DATE START_TIME /to END_DATE END_TIME
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Larper found the event start date, but no time was given.
 Time is optional, so please confirm: add a time, or type no time.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Got it. I've added this task:
 [D][ ] return book (by: 9 mar no time)
 Now you have 1 task in the list.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Nice! I've marked this task as done:
 [D][X] return book (by: 9 mar no time)
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 This task is already marked. Lock in and pick one that is not done yet.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 OK, I've marked this task as not done yet:
 [D][ ] return book (by: 9 mar no time)
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
deadline vnervn/by 9 mar
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
 Time is optional, so please confirm: add a time, or type no time.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Got it. I've added this task:
 [D][ ] vnervn (by: 9 mar no time)
 Now you have 2 tasks in the list.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Here are the tasks in your list:
 1. [T][ ] larp
 2. [D][ ] vnervn (by: 9 mar no time)
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Bye. Hope to see you again soon!
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
```

## Test Case: delete task from middle

Aim: Verify that a delete command embedded in a sentence removes the numbered task and keeps the remaining task order correct.

Inputs:
```text
todo alpha
deadline beta /by 9 mar no time
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
 [D][ ] beta (by: 9 mar no time)
 Now you have 2 tasks in the list.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Got it. I've added this task:
 [E][ ] gamma (from: 10 mar 2pm to: 11 mar 4pm)
 Now you have 3 tasks in the list.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Poof it gone now:
 [D][ ] beta (by: 9 mar no time)
 Now you have 2 tasks in the list.
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Here are the tasks in your list:
 1. [T][ ] alpha
 2. [E][ ] gamma (from: 10 mar 2pm to: 11 mar 4pm)
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
 Bye. Hope to see you again soon!
_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_
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
