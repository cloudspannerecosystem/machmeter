package com.google.cloud.machmeter.plugins;

import com.google.cloud.machmeter.model.GKEConfig;
import com.google.cloud.machmeter.model.SpannerInstanceConfig;
import com.google.cloud.machmeter.model.MachmeterConfig;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;

public class InfraSetup implements PluginInterface {
  private static final Logger logger = Logger.getLogger(InfraSetup.class.getName());

  @Override
  public String getName() {
    return "infraSetup";
  }

  @Override
  public void execute(MachmeterConfig machmeterConfig) {
    // Spanner instance and database config
    Gson gson = new Gson();
    SpannerInstanceConfig spannerInstanceConfig = machmeterConfig.getInfraConfig().getSpannerInstanceConfig();
    GKEConfig gkeConfig = machmeterConfig.getInfraConfig().getGkeConfig();
    String terraformPlanCMD =
        String.format(
            "terraform plan -var=gcp_project=%s -var=spanner_config=%s -var=gke_config=%s",
            spannerInstanceConfig.getProjectId(),
            gson.toJson(spannerInstanceConfig),
            gson.toJson(gkeConfig));
    System.out.println(terraformPlanCMD);
    String terraformApplyCMD =
        String.format(
            "terraform apply -var=gcp_project=%s -var=spanner_config=%s -var=gke_config=%s --auto-approve",
            machmeterConfig.getInfraConfig().getSpannerInstanceConfig().getProjectId(),
            gson.toJson(spannerInstanceConfig),
            gson.toJson(gkeConfig));
    try {
      logger.log(Level.INFO, "Executing terraform init");
      run("terraform init");
      logger.log(Level.INFO, "Executing {0}", terraformPlanCMD);
      run(terraformPlanCMD);
      logger.log(Level.INFO, "Executing {0}", terraformApplyCMD);
      run(terraformApplyCMD);
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void run(String executeCommand) throws Exception {
    ProcessBuilder processBuilder = new ProcessBuilder(executeCommand.split(" "));
    processBuilder.inheritIO();
    processBuilder.directory(new File("machmeter_output/terraform"));
    Process process = processBuilder.start();
    StringBuilder output = new StringBuilder();
    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    String line;
    while ((line = reader.readLine()) != null) {
      output.append(line + "\n");
    }

    int exitVal = process.waitFor();
    if (exitVal == 0) {
      System.out.println("Success!");
      System.out.println(output);
    } else {
      System.out.println("Fail!");
      System.out.println(output);
      System.exit(1);
    }
  }
}
