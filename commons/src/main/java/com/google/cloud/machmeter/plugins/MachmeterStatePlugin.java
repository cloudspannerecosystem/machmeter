package com.google.cloud.machmeter.plugins;


import com.google.cloud.machmeter.helpers.ResourceHandler;
import com.google.cloud.machmeter.model.MachmeterConfig;

import java.io.File;
import java.nio.file.Paths;


/*
This plugin creates a local directory on the user system to preserve machmeter state.
1. `terraform` directory which forms the execution environment for terraform CLI to run.
 */
public class MachmeterStatePlugin implements PluginInterface {

  public static final String MACHMETER_OUTPUT_DIR = "machmeter_output";

  @Override
  public String getName() {
    return "machmeter-state-setup";
  }

  @Override
  public void execute(MachmeterConfig config) {
      File machmeterDir = new File(MACHMETER_OUTPUT_DIR);
      if (!machmeterDir.exists()) {
        machmeterDir.mkdirs();
      }
    ResourceHandler resourceCopy = new ResourceHandler();
    resourceCopy.copyResourceDirectory(Paths.get(machmeterDir.getAbsolutePath()));
  }
}
