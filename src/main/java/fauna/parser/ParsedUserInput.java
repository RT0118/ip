package fauna.parser;

import java.time.LocalDateTime;
import java.util.Optional;

import fauna.exceptions.InvalidUserInputException;

/**
 * ParsedUserInput stores the values obtained from the user after parsing
 */
public class ParsedUserInput {
    private final FaunaCommand command;
    private final Optional<String> taskName;
    private final Optional<LocalDateTime> taskByDatetime;
    private final Optional<LocalDateTime> taskFromDatetime;
    private final Optional<LocalDateTime> taskToDatetime;
    private final Optional<Integer> taskNumber;

    public ParsedUserInput(FaunaCommand command) {
        this.command = command;
        this.taskName = Optional.empty();
        this.taskByDatetime = Optional.empty();
        this.taskFromDatetime = Optional.empty();
        this.taskToDatetime = Optional.empty();
        this.taskNumber = Optional.empty();
    }

    public ParsedUserInput(FaunaCommand command, String taskName) {
        this.command = command;
        this.taskName = Optional.of(taskName);
        this.taskByDatetime = Optional.empty();
        this.taskFromDatetime = Optional.empty();
        this.taskToDatetime = Optional.empty();
        this.taskNumber = Optional.empty();
    }

    public ParsedUserInput(FaunaCommand command, String taskName, LocalDateTime byDatetime) {
        this.command = command;
        this.taskName = Optional.of(taskName);
        this.taskByDatetime = Optional.of(byDatetime);
        this.taskFromDatetime = Optional.empty();
        this.taskToDatetime = Optional.empty();
        this.taskNumber = Optional.empty();
    }

    public ParsedUserInput(FaunaCommand command, String taskName,
                           LocalDateTime fromDatetime, LocalDateTime toDatetime) {
        this.command = command;
        this.taskName = Optional.of(taskName);
        this.taskByDatetime = Optional.empty();
        this.taskFromDatetime = Optional.of(fromDatetime);
        this.taskToDatetime = Optional.of(toDatetime);
        this.taskNumber = Optional.empty();
    }

    public ParsedUserInput(FaunaCommand command, Integer taskNumber) {
        this.command = command;
        this.taskName = Optional.empty();
        this.taskByDatetime = Optional.empty();
        this.taskFromDatetime = Optional.empty();
        this.taskToDatetime = Optional.empty();
        this.taskNumber = Optional.of(taskNumber);
    }

    public FaunaCommand getCommand() {
        return this.command;
    }

    public String getTaskName() {
        return this.taskName.orElse("Invalid name");
    }

    public LocalDateTime getTaskByDatetime() {
        return this.taskByDatetime
                .orElseThrow(() -> new InvalidUserInputException("By date missing?"));
    }

    public LocalDateTime getTaskFromDatetime() {
        return this.taskFromDatetime
                .orElseThrow(() -> new InvalidUserInputException("From date missing?"));
    }

    public LocalDateTime getTaskToDatetime() {
        return this.taskToDatetime
                .orElseThrow(() -> new InvalidUserInputException("To date missing?"));
    }

    public Integer getTaskNumber() {
        return this.taskNumber.orElse(-1);
    }

}
