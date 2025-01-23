package parser;

import java.util.Optional;

public class ParsedUserInput {
    private final FaunaCommand command;
    private final Optional<String> taskName;
    private final Optional<String> taskDatetime;
    private final Optional<Integer> taskNumber;

    public ParsedUserInput(FaunaCommand command) {
        this.command = command;
        this.taskName = Optional.empty();
        this.taskDatetime = Optional.empty();
        this.taskNumber = Optional.empty();
    }

    public ParsedUserInput(FaunaCommand command, String taskName) {
        this.command = command;
        this.taskName = Optional.of(taskName);
        this.taskDatetime = Optional.empty();
        this.taskNumber = Optional.empty();
    }

    public ParsedUserInput(FaunaCommand command, String taskName, String taskDatetime) {
        this.command = command;
        this.taskName = Optional.of(taskName);
        this.taskDatetime = Optional.of(taskDatetime);
        this.taskNumber = Optional.empty();
    }

    public ParsedUserInput(FaunaCommand command, Integer taskNumber) {
        this.command = command;
        this.taskName = Optional.empty();
        this.taskDatetime = Optional.empty();
        this.taskNumber = Optional.of(taskNumber);
    }

    public FaunaCommand getCommand() {
        return this.command;
    }

    public String getTaskName() {
        return this.taskName.orElse("Invalid name");
    }

    public String getTaskDatetime() {
        return this.taskDatetime.orElse("Invalid datetime");
    }

    public Integer getTaskNumber() {
        return this.taskNumber.orElse(-1);
    }

}
