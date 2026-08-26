import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class TaskDateTimeParser {
    private static final DateTimeFormatter DISPLAY_DATE_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy",
            Locale.ENGLISH);
    private static final DateTimeFormatter SLASH_DATE_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy");
    private static final String[][] MONTH_ALIASES = {
        {"jan", "jan"},
        {"january", "jan"},
        {"janurary", "jan"},
        {"feb", "feb"},
        {"february", "feb"},
        {"feburary", "feb"},
        {"mar", "mar"},
        {"march", "mar"},
        {"apr", "apr"},
        {"april", "apr"},
        {"may", "may"},
        {"jun", "jun"},
        {"june", "jun"},
        {"jul", "jul"},
        {"july", "jul"},
        {"aug", "aug"},
        {"august", "aug"},
        {"sep", "sep"},
        {"sept", "sep"},
        {"september", "sep"},
        {"oct", "oct"},
        {"october", "oct"},
        {"nov", "nov"},
        {"november", "nov"},
        {"dec", "dec"},
        {"december", "dec"}
    };
    private static final DateTimeFormatter SHORT_MONTH_DATE_FORMATTER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("d MMM yyyy")
            .toFormatter(Locale.ENGLISH);
    private static final DateTimeFormatter LONG_MONTH_DATE_FORMATTER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("d MMMM yyyy")
            .toFormatter(Locale.ENGLISH);
    private static final DateTimeFormatter SHORT_MONTH_FIRST_DATE_FORMATTER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("MMM d yyyy")
            .toFormatter(Locale.ENGLISH);
    private static final DateTimeFormatter LONG_MONTH_FIRST_DATE_FORMATTER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("MMMM d yyyy")
            .toFormatter(Locale.ENGLISH);

    public static TaskDateTime parse(String text, String defaultTime) {
        String trimmedText = removeLeadingDateMarker(text.trim());
        if (trimmedText.isEmpty() || isNoTimeOnly(trimmedText)) {
            throw new IllegalArgumentException("Missing task date.");
        }

        String dateText = trimmedText;
        String time = defaultTime;
        if (endsWithNoTime(trimmedText)) {
            dateText = trimmedText.substring(0, trimmedText.length() - " no time".length()).trim();
            time = "no time";
        } else {
            int lastSpaceIndex = trimmedText.lastIndexOf(' ');
            if (lastSpaceIndex != -1) {
                String possibleDate = trimmedText.substring(0, lastSpaceIndex).trim();
                String possibleTime = trimmedText.substring(lastSpaceIndex + 1).trim();
                if (looksLikeTime(possibleTime)) {
                    dateText = possibleDate;
                    time = normalizeTime(possibleTime);
                }
            }
        }

        return new TaskDateTime(parseDate(dateText), time);
    }

    public static LocalDate parseDate(String dateText) {
        String cleanDateText = cleanDateText(dateText);
        try {
            return LocalDate.parse(cleanDateText);
        } catch (DateTimeParseException e) {
            return parseNonIsoDate(cleanDateText);
        }
    }

    public static String normalizeTime(String timeText) {
        String cleanTimeText = timeText.trim().toLowerCase();
        if (isNoTimeOnly(cleanTimeText)) {
            return "no time";
        }
        if (cleanTimeText.matches("\\d{3,4}")) {
            return String.format("%04d", Integer.parseInt(cleanTimeText));
        }
        if (cleanTimeText.matches("\\d{1,2}:\\d{2}")) {
            return cleanTimeText.replace(":", "");
        }
        if (cleanTimeText.matches("\\d{1,2}(:\\d{2})?(am|pm)")) {
            return parseAmPmTime(cleanTimeText);
        }
        throw new IllegalArgumentException("Invalid deadline time.");
    }

    public static boolean looksLikeTime(String text) {
        String cleanTimeText = text.trim().toLowerCase();
        return cleanTimeText.matches("\\d{3,4}") || cleanTimeText.matches("\\d{1,2}:\\d{2}")
                || cleanTimeText.matches("\\d{1,2}(:\\d{2})?(am|pm)");
    }

    public static boolean isValidTimeAnswer(String text) {
        return isNoTimeOnly(text) || looksLikeTime(text);
    }

    public static boolean containsMonthName(String text) {
        String cleanText = cleanDateText(text).toLowerCase();
        int index = 0;
        while (index < MONTH_ALIASES.length) {
            if (containsWord(cleanText, MONTH_ALIASES[index][0])) {
                return true;
            }
            index++;
        }
        return false;
    }

    public static String formatDate(LocalDate date) {
        return date.format(DISPLAY_DATE_FORMATTER);
    }

    private static LocalDate parseNonIsoDate(String dateText) {
        String normalizedDateText = normalizeMonthNames(dateText);
        try {
            return LocalDate.parse(normalizedDateText, SLASH_DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            DayOfWeek dayOfWeek = parseDayOfWeek(normalizedDateText);
            if (dayOfWeek != null) {
                return getNextDateForDayOfWeek(dayOfWeek);
            }
            return parseMonthDate(normalizedDateText);
        }
    }

    private static LocalDate getNextDateForDayOfWeek(DayOfWeek dayOfWeek) {
        LocalDate today = getToday();
        int daysAhead = dayOfWeek.getValue() - today.getDayOfWeek().getValue();
        if (daysAhead < 0) {
            daysAhead += 7;
        }
        return today.plusDays(daysAhead);
    }

    private static LocalDate parseMonthDate(String dateText) {
        try {
            return parseMonthDateWithYear(dateText);
        } catch (DateTimeParseException e) {
            return parseMonthDateWithYear(dateText + " " + getCurrentYear());
        }
    }

    private static LocalDate parseMonthDateWithYear(String dateText) {
        try {
            return LocalDate.parse(dateText, SHORT_MONTH_DATE_FORMATTER);
        } catch (DateTimeParseException firstException) {
            try {
                return LocalDate.parse(dateText, LONG_MONTH_DATE_FORMATTER);
            } catch (DateTimeParseException secondException) {
                try {
                    return LocalDate.parse(dateText, SHORT_MONTH_FIRST_DATE_FORMATTER);
                } catch (DateTimeParseException thirdException) {
                    return LocalDate.parse(dateText, LONG_MONTH_FIRST_DATE_FORMATTER);
                }
            }
        }
    }

    private static String parseAmPmTime(String timeText) {
        boolean isPm = timeText.endsWith("pm");
        String timeWithoutPeriod = timeText.substring(0, timeText.length() - 2);
        int hour;
        int minute = 0;

        if (timeWithoutPeriod.contains(":")) {
            String[] timeParts = timeWithoutPeriod.split(":");
            hour = Integer.parseInt(timeParts[0]);
            minute = Integer.parseInt(timeParts[1]);
        } else {
            hour = Integer.parseInt(timeWithoutPeriod);
        }

        if (isPm && hour != 12) {
            hour += 12;
        } else if (!isPm && hour == 12) {
            hour = 0;
        }

        return String.format("%02d%02d", hour, minute);
    }

    private static String cleanDateText(String dateText) {
        return dateText.trim()
                .replace(",", "")
                .replaceAll("(?i)\\b(\\d{1,2})(st|nd|rd|th)\\b", "$1")
                .replaceAll("\\s+", " ");
    }

    private static String normalizeMonthNames(String dateText) {
        String cleanDateText = cleanDateText(dateText).toLowerCase();
        int index = 0;
        while (index < MONTH_ALIASES.length) {
            cleanDateText = cleanDateText.replaceAll("\\b" + MONTH_ALIASES[index][0] + "\\b",
                    MONTH_ALIASES[index][1]);
            index++;
        }
        return cleanDateText;
    }

    private static boolean containsWord(String text, String word) {
        return (" " + text + " ").matches(".*\\s" + word + "\\s.*");
    }

    private static String removeLeadingDateMarker(String text) {
        String lowerText = text.toLowerCase();
        if (lowerText.startsWith("by ")) {
            return text.substring(3).trim();
        }
        if (lowerText.startsWith("from ")) {
            return text.substring(5).trim();
        }
        if (lowerText.startsWith("to ")) {
            return text.substring(3).trim();
        }
        return text;
    }

    private static DayOfWeek parseDayOfWeek(String text) {
        String trimmedText = text.trim().toLowerCase();
        switch (trimmedText) {
        case "mon":
        case "monday":
            return DayOfWeek.MONDAY;
        case "tue":
        case "tues":
        case "tuesday":
            return DayOfWeek.TUESDAY;
        case "wed":
        case "wednesday":
            return DayOfWeek.WEDNESDAY;
        case "thu":
        case "thur":
        case "thurs":
        case "thursday":
            return DayOfWeek.THURSDAY;
        case "fri":
        case "friday":
            return DayOfWeek.FRIDAY;
        case "sat":
        case "saturday":
            return DayOfWeek.SATURDAY;
        case "sun":
        case "sunday":
            return DayOfWeek.SUNDAY;
        default:
            return null;
        }
    }

    private static boolean isNoTimeOnly(String text) {
        return text.trim().equalsIgnoreCase("no time");
    }

    private static boolean endsWithNoTime(String text) {
        return text.trim().toLowerCase().endsWith(" no time");
    }

    private static int getCurrentYear() {
        return getToday().getYear();
    }

    private static LocalDate getToday() {
        String todayProperty = System.getProperty("larper.today");
        if (todayProperty != null && !todayProperty.isBlank()) {
            return LocalDate.parse(todayProperty);
        }
        return LocalDate.now();
    }
}
