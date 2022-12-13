/*
 * Copyright 2022 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
    pluginCommandMap.put(ExecutionCommand.CLEANUP, getOrderedCleanupCommand());
  }

  public List<PluginInterface> getSequentialOfPlugins(ExecutionCommand executionCommand) {
    return pluginCommandMap.get(executionCommand);
  }

  private List<PluginInterface> getOrderedSetupCommand() {
    List<PluginInterface> setupCommandList = new ArrayList<>();
    setupCommandList.add(new MachmeterStatePlugin());
    setupCommandList.add(new InfraSetupPlugin());
    setupCommandList.add(new DdlPlugin());
    return setupCommandList;
  }

  private List<PluginInterface> getOrderedExecuteCommand() {
    List<PluginInterface> executeCommandList = new ArrayList<>();
    executeCommandList.add(new ExecutePlugin());
    return executeCommandList;
  }

  private List<PluginInterface> getOrderedCleanupCommand() {
    List<PluginInterface> cleanupCommandList = new ArrayList<>();
    cleanupCommandList.add(new CleanupPlugin());
    return cleanupCommandList;
  }
}
