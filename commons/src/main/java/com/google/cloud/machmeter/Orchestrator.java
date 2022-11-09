package com.google.cloud.machmeter;

import com.google.cloud.machmeter.model.ExecutionCommand;
import com.google.cloud.machmeter.model.MachmeterConfig;
import com.google.cloud.machmeter.plugins.PluginController;
import com.google.cloud.machmeter.plugins.PluginInterface;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Orchestrator {
  private static final Logger logger = Logger.getLogger(Orchestrator.class.getName());
  private final PluginController pluginController = new PluginController();

  /**
   *
   * @param parameters Contains list of command line arguments in the following structure
   * [0]: setup / execute
   * [1]: path to json machMeterConfig
   */
  public static void main(String[] parameters) {
    if (parameters.length != 2) {
      throw new IllegalArgumentException("Command and config path required.");
    }
    new Orchestrator().executeOrchestrator(parameters[0], parameters[1]);
  }

  public void executeOrchestrator(String command, String machmeterConfigPath) {
    ExecutionCommand executionCommand = validateExecutionCommand(command);
    MachmeterConfig machmeterConfig = validateJsonFile(machmeterConfigPath);
    executeOrchestrator(executionCommand, machmeterConfig);
  }

  public void executeOrchestrator(ExecutionCommand command, MachmeterConfig machmeterConfig) {
    List<PluginInterface> pluginInterfaces = pluginController.getSequentialOfPlugins(command);
    for (PluginInterface pluginInterface : pluginInterfaces) {
      logger.log(Level.INFO, "Executing {0} plugin.", pluginInterface.getName());
      pluginInterface.execute(machmeterConfig);
      logger.log(Level.INFO, "Plugin {0} completed.", pluginInterface.getName());
    }
  }

  private ExecutionCommand validateExecutionCommand(String command) {
    return ExecutionCommand.parseCommand(command);
  }

  private MachmeterConfig validateJsonFile(String pathToJsonFile) {

    Gson gson = new Gson();
    try (JsonReader reader = new JsonReader(new FileReader(pathToJsonFile))) {
      return gson.fromJson(reader, MachmeterConfig.class);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Invalid Json File.");
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid Json File.");
    } catch (JsonSyntaxException e) {
      throw new IllegalArgumentException("Json File is not of the expected format.", e);
    }
  }

}
