package com.google.cloud.machmeter.model;

public enum ExecutionCommand {
  SETUP,
  EXECUTE;

  public static ExecutionCommand parseCommand(String command) {
    for (ExecutionCommand executionCommand : ExecutionCommand.values()) {
      if (executionCommand.name().equalsIgnoreCase(command)) {
        return executionCommand;
      }
    }
    throw new IllegalArgumentException("Invalid Command Received");
  }
}
