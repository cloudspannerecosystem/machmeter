package com.google.cloud.machmeter.plugins;

import com.google.cloud.machmeter.model.ExecutionCommand;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PluginController {

  private final Map<ExecutionCommand, List<PluginInterface>> pluginCommandMap;

  public PluginController() {
    pluginCommandMap = new HashMap<>();
    pluginCommandMap.put(ExecutionCommand.SETUP, getOrderedSetupCommand());
    pluginCommandMap.put(ExecutionCommand.EXECUTE, getOrderedExecuteCommand());
  }


  public List<PluginInterface> getSequentialOfPlugins(ExecutionCommand executionCommand) {
    return pluginCommandMap.get(executionCommand);
  }

  private List<PluginInterface> getOrderedSetupCommand() {
    List<PluginInterface> setupCommandList = new ArrayList<>();
    setupCommandList.add(new MachmeterStatePlugin());
    setupCommandList.add(new InfraSetup());
    return setupCommandList;
  }

  private List<PluginInterface> getOrderedExecuteCommand() {
    List<PluginInterface> executeCommandList = new ArrayList<>();
    return executeCommandList;
  }

}
