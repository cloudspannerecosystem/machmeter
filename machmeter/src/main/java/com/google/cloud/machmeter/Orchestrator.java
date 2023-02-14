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

package com.google.cloud.machmeter;

import com.google.cloud.machmeter.model.Command;
import com.google.cloud.machmeter.plugins.Plugin;
import com.google.cloud.machmeter.plugins.PluginController;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Orchestrator {
  private static final Logger logger = Logger.getLogger(Orchestrator.class.getName());
  private final PluginController pluginController = new PluginController();
  private static final Gson gson = new Gson();

  /**
   * @param parameters Contains list of command line arguments in the following structure [0]: setup
   *     / execute [1]: path to json machMeterConfig
   */
  public static void main(String[] parameters) {
    if (parameters.length != 2) {
      throw new IllegalArgumentException("Command and config path required.");
    }
    Orchestrator orchestrator = new Orchestrator();
    orchestrator.executeOrchestrator(parameters[0], parameters[1]);
  }

  public void executeOrchestrator(String command, String commandParameters) {
    Command inputCommand = Command.parseCommand(command);
    try {
      List<Plugin<?>> plugins = pluginController.getSequentialListOfPlugins(inputCommand);
      for (Plugin<?> plugin : plugins) {
        logger.log(Level.INFO, "Executing {0} plugin.", plugin.getPluginName());
        JsonReader reader = new JsonReader(new FileReader(commandParameters));
        plugin.execute(gson.fromJson(reader, plugin.getPluginConfigClass()));
        logger.log(Level.INFO, "Plugin {0} completed.", plugin.getPluginName());
      }
    } catch (FileNotFoundException e) {
      logger.log(Level.SEVERE, "File not found!");
    }
  }
}
