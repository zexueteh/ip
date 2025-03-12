package TARS.logic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;

public class DateTimeParser {
    public static String parseParam(String param) throws DateTimeParseException {
        if (param != null) {
            LocalDateTime dateTime = getDateTime(param);
            return replaceDateTime(param, dateTime);
        }
        return null;
    }

    private static String replaceDateTime(String param, LocalDateTime dateTime) {
        if (dateTime != null) {
            Matcher dateMatcher = RegexConstants.DATE_PATTERN.matcher(param);
            Matcher timeMatcher = RegexConstants.TIME_PATTERN.matcher(param);

            if (dateMatcher.find()) {
                String originalDate = dateMatcher.group();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy");
                String formattedDate = dateTime.toLocalDate().format(formatter);
                param = param.replace(originalDate, formattedDate);
            }
            if (timeMatcher.find()) {
                String originalTime = timeMatcher.group();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h.mm a");
                String formattedTime = dateTime.toLocalTime().format(formatter);
                param = param.replace(originalTime, formattedTime);
            }

        }
        return param;
    }

    private static LocalDateTime getDateTime(String param) throws DateTimeParseException {
        LocalDateTime dateTime = null;
        String dateTimeString = "";
        String formatString = "yyyy-MM-ddHH:mm";

        Matcher dateMatcher = RegexConstants.DATE_PATTERN.matcher(param);
        Matcher timeMatcher = RegexConstants.TIME_PATTERN.matcher(param);

        if (dateMatcher.find()) {
            dateTimeString += dateMatcher.group().trim();
        } else {
            dateTimeString = "9999-12-31";
        }
        if (timeMatcher.find()) {
            dateTimeString += timeMatcher.group().trim();
        } else {
            dateTimeString += "00:00";
        }

        if (!dateTimeString.equals("9999-12-3100:/00")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatString);
            dateTime = LocalDateTime.parse(dateTimeString, formatter);
        }

        return dateTime;
    }
}
