package fauna.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fauna.exceptions.InvalidUserInputException;

/**
 * UserInputParser handles parsing of raw user input with error checking
 * @author RT0118
 */
public class UserInputParser {
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    private static String getCommandFromUserInput(String userInput) {
        return userInput.split(" ", 2)[0].toLowerCase();
    }

    private static String regexExtract(String input, String pattern, String groupName) {
        Pattern regexPattern = Pattern.compile(pattern);
        Matcher matcher = regexPattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(groupName);
        }
        return null;
    }

    private static String getNameFromUserInput(String userInput, String inputPattern) {
        String extracted = regexExtract(userInput, inputPattern, "name");
        if (extracted == null || extracted.isBlank()) {
            throw new InvalidUserInputException("the task's name cannot be empty!");
        }
        return extracted;
    }

    private static int getIndexFromUserInput(String userInput, String inputPattern) {
        String extracted = regexExtract(userInput, inputPattern, "name");
        if (extracted == null || extracted.isBlank()) {
            throw new InvalidUserInputException("the task's index cannot be empty!");
        }

        try {
            int taskIndexInteger = Integer.parseInt(extracted);
            if (taskIndexInteger < 0) {
                throw new InvalidUserInputException("the task number provided cannot be less than 1!");
            }
            return taskIndexInteger;

        } catch (NumberFormatException e) {
            throw new InvalidUserInputException("the task number provided is invalid!");
        }
    }

    private static LocalDateTime getByDateFromUserInput(String userInput, String inputPattern) {
        String extracted = regexExtract(userInput, inputPattern, "byDate");
        if (extracted == null || extracted.isBlank()) {
            throw new InvalidUserInputException("the task's /by is missing!");
        }
        return dateTimeFromString(extracted);
    }

    private static LocalDateTime getFromDateFromUserInput(String userInput, String inputPattern) {
        String extracted = regexExtract(userInput, inputPattern, "fromDate");
        if (extracted == null || extracted.isBlank()) {
            throw new InvalidUserInputException("the task's /from is missing!");
        }
        return dateTimeFromString(extracted);
    }

    private static LocalDateTime getToDateFromUserInput(String userInput, String inputPattern) {
        String extracted = regexExtract(userInput, inputPattern, "toDate");
        if (extracted == null || extracted.isBlank()) {
            throw new InvalidUserInputException("the task's /to is missing!");
        }
        return dateTimeFromString(extracted);
    }

    private static boolean isDatetimeFromUserInputValid(String dateTimeString) {
        try {
            DATETIME_FORMATTER.parse(dateTimeString);
        } catch (DateTimeParseException dateTimeParseException) {
            return false;
        }
        return true;
    }

    private static LocalDateTime dateTimeFromString(String dateTimeString) {
        if (!isDatetimeFromUserInputValid(dateTimeString)) {
            throw new InvalidUserInputException("your date format is invalid! It should be yyyy-MM-dd HHmm.");
        }
        return LocalDateTime.parse(dateTimeString, DATETIME_FORMATTER);
    }

    private static String getTaskTagFromUserInput(String userInput, String inputPattern) {
        String extracted = regexExtract(userInput, inputPattern, "tag");
        if (extracted == null || extracted.isBlank()) {
            throw new InvalidUserInputException("the task's #tag is missing!");
        }
        return extracted;
    }

    /**
     * <p>parse method will take in user input and
     * extract the command and task information and
     * returns a ParsedUserInput that contains
     * the task information.
     * </p>
     * @param userInput raw user input
     * @return ParsedUserInput object that contains user provided information
     * @throws InvalidUserInputException
     */
    public static ParsedUserInput parse(String userInput) throws InvalidUserInputException {
        FaunaCommand command = FaunaCommand.fromString(getCommandFromUserInput(userInput));
        String inputPattern = command.getCommandRegexPattern();

        switch (command) {
        case MARK, UNMARK, DELETE: {
            int taskIndex = getIndexFromUserInput(userInput, inputPattern);
            assert(taskIndex > 0);
            return new ParsedUserInput(command, taskIndex);
        }
        case TODO, FIND: {
            String taskName = getNameFromUserInput(userInput, inputPattern);
            assert(!taskName.isBlank());
            return new ParsedUserInput(command, taskName);
        }
        case DEADLINE: {
            String taskName = getNameFromUserInput(userInput, inputPattern);
            LocalDateTime by = getByDateFromUserInput(userInput, inputPattern);
            assert(!taskName.isBlank());
            return new ParsedUserInput(command, taskName, by);
        }
        case EVENT: {
            String taskName = getNameFromUserInput(userInput, inputPattern);
            LocalDateTime from = getFromDateFromUserInput(userInput, inputPattern);
            LocalDateTime to = getToDateFromUserInput(userInput, inputPattern);
            assert(!taskName.isBlank());
            return new ParsedUserInput(command, taskName, from, to);
        }
        case TAG: {
            int taskIndex = getIndexFromUserInput(userInput, inputPattern);
            assert(taskIndex > 0);
            String taskTag = getTaskTagFromUserInput(userInput, inputPattern);
            return new ParsedUserInput(command, taskIndex, taskTag);
        }
        default:
            return new ParsedUserInput(command);
        }
    }
}
