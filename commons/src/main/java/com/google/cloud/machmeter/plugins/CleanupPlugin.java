package com.google.cloud.machmeter.plugins;

import com.google.cloud.machmeter.model.ConfigInterface;
import com.google.cloud.machmeter.model.GKEConfig;
import com.google.cloud.machmeter.model.SetupConfig;
import com.google.cloud.machmeter.model.SpannerInstanceConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CleanupPlugin implements PluginInterface {
  private static final Logger logger = Logger.getLogger(InfraSetupPlugin.class.getName());

  @Override
  public String getName() {
    return "cleanup";
  }

  @Override
  public void execute(ConfigInterface config) {
    SetupConfig setupConfig;
    if (config instanceof SetupConfig) {
      setupConfig = (SetupConfig) config;
    }
    else {
      throw new RuntimeException("Cast error!");
    }
    // Spanner instance and database config
    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    SpannerInstanceConfig spannerInstanceConfig =
        setupConfig.getInfraConfig().getSpannerInstanceConfig();
    GKEConfig gkeConfig = setupConfig.getInfraConfig().getGkeConfig();
    if (gkeConfig.getServiceAccountJson().isEmpty()) {
      gkeConfig.setServiceAccountJson(System.getenv("GOOGLE_APPLICATION_CREDENTIALS"));
    }
    String terraformDestroyCMD =
        String.format(
            "terraform destroy -var=gcp_project=%s -var=spanner_config=%s -var=gke_config=%s",
            spannerInstanceConfig.getProjectId(),
            gson.toJson(spannerInstanceConfig),
            gson.toJson(gkeConfig));
    try {
      logger.log(Level.INFO, "Executing {0}", terraformDestroyCMD);
      run(terraformDestroyCMD);
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
