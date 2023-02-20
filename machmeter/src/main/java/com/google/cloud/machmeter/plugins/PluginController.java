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

import com.google.cloud.machmeter.model.Command;
import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PluginController {

  private final Map<Command, List<Plugin<?>>> pluginCommandMap;

  private final MachmeterStatePlugin machmeterStatePlugin;
  private final InfraSetupPlugin infraSetupPlugin;
  private final DdlPlugin ddlPlugin;
  private final ExecutePlugin executePlugin;
  private final CleanupPlugin cleanupPlugin;

  @Inject
  public PluginController(
      MachmeterStatePlugin machmeterStatePlugin,
      InfraSetupPlugin infraSetupPlugin,
      DdlPlugin ddlPlugin,
      ExecutePlugin executePlugin,
      CleanupPlugin cleanupPlugin) {
    this.machmeterStatePlugin = machmeterStatePlugin;
    this.infraSetupPlugin = infraSetupPlugin;
    this.ddlPlugin = ddlPlugin;
    this.executePlugin = executePlugin;
    this.cleanupPlugin = cleanupPlugin;
    pluginCommandMap = new HashMap<>();
    pluginCommandMap.put(Command.SETUP, getOrderedSetupCommand());
    pluginCommandMap.put(Command.EXECUTE, getOrderedExecuteCommand());
    pluginCommandMap.put(Command.CLEANUP, getOrderedCleanupCommand());
  }

  public List<Plugin<?>> getSequentialListOfPlugins(Command command) {
    return pluginCommandMap.get(command);
  }

  private List<Plugin<?>> getOrderedSetupCommand() {
    List<Plugin<?>> setupCommandList = new ArrayList<>();
    setupCommandList.add(machmeterStatePlugin);
    setupCommandList.add(infraSetupPlugin);
    setupCommandList.add(ddlPlugin);
    return setupCommandList;
  }

  private List<Plugin<?>> getOrderedExecuteCommand() {
    List<Plugin<?>> executeCommandList = new ArrayList<>();
    executeCommandList.add(executePlugin);
    return executeCommandList;
  }

  private List<Plugin<?>> getOrderedCleanupCommand() {
    List<Plugin<?>> cleanupCommandList = new ArrayList<>();
    cleanupCommandList.add(cleanupPlugin);
    return cleanupCommandList;
  }
}
