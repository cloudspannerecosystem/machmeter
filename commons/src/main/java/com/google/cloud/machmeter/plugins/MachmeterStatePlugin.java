package com.google.cloud.machmeter.plugins;

import com.google.cloud.machmeter.helpers.ResourceHandler;
import com.google.cloud.machmeter.model.ConfigInterface;
import java.io.File;
import java.nio.file.Paths;

/*
This plugin creates a local directory on the user system to preserve machmeter state.
Currently only the `machmeter_output/terraform` directory which forms the execution environment for terraform CLI to run.
In future, this directory can be used to store credentials, session information, metadata etc.
 */
public class MachmeterStatePlugin implements PluginInterface {

  private static final String MACHMETER_OUTPUT_DIR = "machmeter_output";
  private static final String TERRAFORM_DIR = "terraform";

  @Override
  public String getName() {
    return "machmeter-state-setup";
  }

  @Override
  public void execute(ConfigInterface config) {
    File machmeterDir = new File(MACHMETER_OUTPUT_DIR);
    if (!machmeterDir.exists()) {
      machmeterDir.mkdirs();
    }
    ResourceHandler resourceCopy = new ResourceHandler();
    resourceCopy.copyResourceDirectory(TERRAFORM_DIR, Paths.get(machmeterDir.getAbsolutePath()));
  }
}
