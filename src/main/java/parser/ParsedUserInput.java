package parser;

import java.util.Optional;

public class ParsedUserInput {
    private final FaunaCommand command;
    private final Optional<String> taskName;
    private final Optional<String> taskByDatetime;
    private final Optional<String> taskFromDatetime;
    private final Optional<String> taskToDatetime;
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

    public ParsedUserInput(FaunaCommand command, String taskName, String byDatetime) {
        this.command = command;
        this.taskName = Optional.of(taskName);
        this.taskByDatetime = Optional.of(byDatetime);
        this.taskFromDatetime = Optional.empty();
        this.taskToDatetime = Optional.empty();
        this.taskNumber = Optional.empty();
    }

    public ParsedUserInput(FaunaCommand command, String taskName, String fromDatetime, String toDatetime) {
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

    public String getTaskByDatetime() {
        return this.taskByDatetime.orElse("Invalid datetime");
    }

    public String getTaskFromDatetime() {
        return this.taskFromDatetime.orElse("Invalid datetime");
    }

    public String getTaskToDatetime() {
        return this.taskToDatetime.orElse("Invalid datetime");
    }

    public Integer getTaskNumber() {
        return this.taskNumber.orElse(-1);
    }

}
