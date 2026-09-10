# Larper User Guide

Larper is a text-based task manager for todos, deadlines, and events.

## Adding Tasks

Use one of the task types followed by a description:

```text
todo read a book
deadline return the book /by 2026-08-23 no time
event project meeting /from 2026-08-24 2pm /to 2026-08-24 4pm
```

Dates are stored as `LocalDate` values and displayed as `MMM dd yyyy`. Times are
displayed in four-digit military time. A deadline time is optional; event dates
are required and each event time can be omitted with `no time`.

## Tags

Add one-word tags to the description with a leading `#`. Tags can be added to
todos, deadlines, and events before their date markers:

```text
todo read a book #school #fun
deadline return the book #library /by 2026-08-23 no time
event project meeting #work /from 2026-08-24 2pm /to 2026-08-24 4pm
```

Larper normalizes tag names to lowercase, removes duplicate tags, and displays
tags after the task details:

```text
[T][ ] read a book [#fun] [#school]
```

Tags must contain one alphanumeric word. Inputs such as `#school-time` or `#`
are rejected with an `InvalidTagException` so the user can try again.

Tags can also be changed after a task is created:

```text
tag 1 #school fun
untag 1 #fun
```

Both commands accept multiple one-word tags. Removing a tag is allowed even
when the task is already marked as done. A missing tag name produces a prompt
to try again.

## Finding Tasks

Enter `find`, then enter a case-insensitive phrase. Larper searches the task
description and exact tag text. A search for `school` matches `[#school]`, but
does not match `[#schoolhouse]` through the tag.

Use `find tag school` when only the exact `#school` tag should be searched.

In the JavaFX interface, valid `[#tag]` patterns are rendered as compact tag
chips. The chip color is stable for the same tag and the `#` symbol identifies
the chip as a tag.

## Saved Data

Larper stores tasks in the relative file `data/larperdata.txt`. Tags are written
as the final field so existing untagged files continue to load:

```text
T | 0 | read a book | #fun #school
D | 0 | return the book | 2026-08-23 | no time | #library
```
