package parser;

import exceptions.InvalidUserInputException;

public class UserInputParser {
    private static String getCommandFromUserInput(String userInput) {
        return userInput.split(" ", 2)[0].toLowerCase();
    }

    private static int getTaskIndexFromUserInput(String userInput) throws InvalidUserInputException {
        String[] splitInput = userInput.split(" ", 2);
        if (splitInput.length < 2 || splitInput[1].isBlank()) {
            throw new InvalidUserInputException("the task number cannot be empty!");
        }

        try {
            int taskIndexInteger = Integer.parseInt(splitInput[1]) - 1;
            if (taskIndexInteger < 0) {
                throw new InvalidUserInputException("the task number provided cannot be less than 1!");
            }
            return taskIndexInteger;
        } catch (NumberFormatException e) {
            throw new InvalidUserInputException("the task number provided is invalid!");
        }
    }

    private static String getTaskNameFromUserInput(String userInput) throws InvalidUserInputException {
        String[] splitInput = userInput.split(" ", 2);
        if (splitInput.length < 2 || splitInput[1].isBlank()) {
            throw new InvalidUserInputException("the task name cannot be empty!");
        }

        return splitInput[1].trim();
    }

    private static String getTaskNameFromUserInput(String userInput, String delimiter) throws InvalidUserInputException {
        String taskNameAndDatetime = getTaskNameFromUserInput(userInput);
        String[] splitInput = taskNameAndDatetime.split(delimiter, 2);
        if (splitInput.length < 2 || splitInput[0].isBlank()) {
            throw new InvalidUserInputException("the task name cannot be empty!");
        }
        return splitInput[0].trim();
    }

    private static String getTaskDatetimeFromUserInput(String userInput, String delimiter) throws InvalidUserInputException {
        String taskNameAndDatetime = getTaskNameFromUserInput(userInput);
        String [] splitInput = taskNameAndDatetime.split(delimiter, 2);
        if (splitInput.length < 2 || splitInput[1].isBlank()) {
            throw new InvalidUserInputException(
                String.format("the task's %s is missing information!", delimiter));
        }
        return splitInput[1].trim();
    }

    public static ParsedUserInput parse(String userInput) throws InvalidUserInputException {
        String command = getCommandFromUserInput(userInput);

        switch (command) {
            case "list","bye":
                return new ParsedUserInput(command);
            case "done","undone": {
                int taskIndex = getTaskIndexFromUserInput(userInput);
                return new ParsedUserInput(command, taskIndex);
            }
            case "todo": {
                String taskName = getTaskNameFromUserInput(userInput);
                return new ParsedUserInput(command, taskName);
            }
            case "deadline": {
                String taskName = getTaskNameFromUserInput(userInput, "/by");
                String by = getTaskDatetimeFromUserInput(userInput, "/by");
                return new ParsedUserInput(command, taskName, by);
            }
            case "event": {
                String taskName = getTaskNameFromUserInput(userInput, "/at");
                String at = getTaskDatetimeFromUserInput(userInput, "/at");
                return new ParsedUserInput(command, taskName, at);
            }
            default:
                return new ParsedUserInput("invalid");
        }
    }
}
