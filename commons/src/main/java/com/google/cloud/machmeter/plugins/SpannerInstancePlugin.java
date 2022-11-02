package com.google.cloud.machmeter.plugins;

import com.google.cloud.machmeter.model.MachmeterConfig;
import com.google.cloud.machmeter.model.SpannerInstanceConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpannerInstancePlugin implements PluginInterface {

  private static final Logger logger = Logger.getLogger(SpannerInstancePlugin.class.getName());

  @Override
  public String getName() {
    return "spannerInstancePlugin";
  }

  @Override
  public void execute(MachmeterConfig config) {
    SpannerInstanceConfig spannerInstanceConfig = config.getSpannerInstanceConfig();
    // Step 1
    //Setup environment variables after reading from the Machmeter config using
    // https://developer.hashicorp.com/terraform/cli/config/environment-variables#tf_var_name

    // Step 2
    // Call the cdktf deploy command via a subprocess since it is not possible to do this programmatically right now.
    //https://github.com/hashicorp/terraform-cdk/issues/237
    List<String> cdkList = new ArrayList<>();
    cdkList.add("/opt/homebrew/bin/cdktf");
    cdkList.add("deploy");
    ProcessBuilder processBuilder = new ProcessBuilder(cdkList);
    try {
      Process process = processBuilder.start();
      BufferedReader stdInput
          = new BufferedReader(new InputStreamReader(
          process.getInputStream()));
      String s;
      while ((s = stdInput.readLine()) != null) {
        logger.log(Level.INFO, s);
      }
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Unable to call cdktf to create terraform resources!");
    }
  }
}
