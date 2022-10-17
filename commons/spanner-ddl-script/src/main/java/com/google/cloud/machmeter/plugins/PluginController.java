package com.google.cloud.machmeter.plugins;

import com.google.cloud.machmeter.model.ExecutionCommand;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PluginController {

  private final Map<ExecutionCommand, List<PluginInterface>> pluginCommandMap;

  public PluginController() {
    pluginCommandMap = new HashMap<>();
    pluginCommandMap.put(ExecutionCommand.SETUP, getSetupCommand());
    pluginCommandMap.put(ExecutionCommand.EXECUTE, getExecuteCommand());
  }


  public List<PluginInterface> getSequentialOfPlugins(ExecutionCommand executionCommand) {
    return pluginCommandMap.get(executionCommand);
  }

  private List<PluginInterface> getSetupCommand() {
    List<PluginInterface> setupCommandList = new ArrayList<>();
    setupCommandList.add(new DdlPlugin());
    return setupCommandList;
  }

  private List<PluginInterface> getExecuteCommand() {
    List<PluginInterface> executeCommandList = new ArrayList<>();
    return executeCommandList;
  }

}
