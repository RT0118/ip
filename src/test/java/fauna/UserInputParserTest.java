package fauna;

import fauna.exceptions.InvalidUserInputException;
import fauna.parser.FaunaCommand;
import fauna.parser.ParsedUserInput;
import fauna.parser.UserInputParser;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class UserInputParserTest {
    private static final DateTimeFormatter dateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    @Test
    public void dummyTest() {
        int result = 2;
        int expected = 2;
        assertEquals(expected, result);
    }

    @Test
    public void methodToTest_testCaseDesc_result() {
        // assertEquals(...)
    }

    @Test
    public void parse_validUserInput_success() {
        // test parser with event command
        String userInput = "event my event /from 2024-12-12 0800 /to 2025-01-30 2359";
        ParsedUserInput result = UserInputParser.parse(userInput);

        assertEquals(FaunaCommand.EVENT, result.getCommand());
        assertEquals("my event", result.getTaskName());
        assertEquals(LocalDateTime.parse("2024-12-12 0800", dateTimeFormatter)
                , result.getTaskFromDatetime());
        assertEquals(LocalDateTime.parse("2025-01-30 2359", dateTimeFormatter)
                , result.getTaskToDatetime());
    }

    @Test
    public void parse_invalidUserInput_exceptionThrown() {
        // missing event /to argument
        try {
            String userInput = "event failure event /from 2024-12-12 0800";
            ParsedUserInput result = UserInputParser.parse(userInput);
            assertEquals("failure event", result.getTaskName());
            fail();
        } catch (InvalidUserInputException invalidUserInputException) {
            assertEquals("Uuuu, the task's /to is missing!",
                    invalidUserInputException.getMessage());
        }

        // mark task of index -999
        try {
            String userInput = "mark -999";
            ParsedUserInput result = UserInputParser.parse(userInput);
            assertEquals(-999, result.getTaskNumber());
            fail();
        } catch (InvalidUserInputException invalidUserInputException) {
            assertEquals("Uuuu, the task number provided cannot be less than 1!",
                    invalidUserInputException.getMessage());
        }
    }
}
