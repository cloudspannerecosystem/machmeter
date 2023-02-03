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

import com.google.cloud.machmeter.model.ConfigInterface;
import com.google.cloud.machmeter.model.ExecuteConfig;
import com.google.cloud.machmeter.model.ExecutionCommand;
import com.google.cloud.machmeter.model.SetupConfig;
import com.google.cloud.machmeter.plugins.PluginController;
import com.google.cloud.machmeter.plugins.PluginInterface;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Orchestrator {
  private static final Logger logger = Logger.getLogger(Orchestrator.class.getName());
  private final PluginController pluginController = new PluginController();

  /**
   * @param parameters Contains list of command line arguments in the following structure [0]: setup
   *     / execute [1]: path to json machMeterConfig
   */
  public static void main(String[] parameters) {
    if (parameters.length != 2) {
      throw new IllegalArgumentException("Command and config path required.");
    }
    new Orchestrator().executeOrchestrator(parameters[0], parameters[1]);
  }

  public void executeOrchestrator(String command, String machmeterConfigPath) {
    ExecutionCommand executionCommand = validateExecutionCommand(command);

    ConfigInterface config = validateJsonFile(executionCommand, machmeterConfigPath);
    executeOrchestrator(executionCommand, config);
  }

  public void executeOrchestrator(ExecutionCommand command, ConfigInterface config) {
    List<PluginInterface> pluginInterfaces = pluginController.getSequentialOfPlugins(command);
    for (PluginInterface pluginInterface : pluginInterfaces) {
      logger.log(Level.INFO, "Executing {0} plugin.", pluginInterface.getName());
      pluginInterface.execute(config);
      logger.log(Level.INFO, "Plugin {0} completed.", pluginInterface.getName());
    }
  }

  private ExecutionCommand validateExecutionCommand(String command) {
    return ExecutionCommand.parseCommand(command);
  }

  private ConfigInterface validateJsonFile(
      ExecutionCommand executionCommand, String pathToJsonFile) {

    Gson gson = new Gson();
    try (JsonReader reader = new JsonReader(new FileReader(pathToJsonFile))) {
      if (executionCommand.equals(ExecutionCommand.SETUP)
          || executionCommand.equals(ExecutionCommand.CLEANUP)) {
        return gson.fromJson(reader, SetupConfig.class);
      }
      return gson.fromJson(reader, ExecuteConfig.class);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Invalid Json File.");
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid Json File.");
    } catch (JsonSyntaxException e) {
      throw new IllegalArgumentException("Json File is not of the expected format.", e);
    }
  }
}