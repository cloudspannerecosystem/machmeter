package com.google.cloud.machmeter.plugins;

import com.google.cloud.machmeter.model.MachmeterConfig;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

public class InfraSetup implements PluginInterface {
  private static final Logger logger = Logger.getLogger(InfraSetup.class.getName());

  @Override
  public String getName() {
    return "infraSetup";
  }

  @Override
  public void execute(MachmeterConfig config) {
    // Spanner instance and database config
    Map<String, Object> spannerConfig = new LinkedHashMap<>();
    spannerConfig.put("instance_name", config.getInfraConfig().getInstanceConfig().getInstanceId());
    spannerConfig.put("database_name", config.getInfraConfig().getInstanceConfig().getDbName());
    spannerConfig.put(
        "configuration", config.getInfraConfig().getInstanceConfig().getConfiguration());
    spannerConfig.put(
        "display_name",
        config.getInfraConfig().getInstanceConfig().getDisplayName().replaceAll(" ", "_"));
    spannerConfig.put(
        "processing_units", config.getInfraConfig().getInstanceConfig().getProcessingUnits());
    spannerConfig.put("environment", config.getInfraConfig().getInstanceConfig().getEnvironment());
    // GKE config
    Map<String, Object> gkeConfig = new LinkedHashMap<>();
    gkeConfig.put("cluster_name", config.getInfraConfig().getGkeConfig().getClusterName());
    gkeConfig.put("region", config.getInfraConfig().getGkeConfig().getRegion());
    gkeConfig.put("network", config.getInfraConfig().getGkeConfig().getNetwork());
    gkeConfig.put("subnetwork", config.getInfraConfig().getGkeConfig().getSubnetwork());
    gkeConfig.put(
        "ip_range_pods_name", config.getInfraConfig().getGkeConfig().getIpRangePodsName());
    gkeConfig.put(
        "ip_range_services_name", config.getInfraConfig().getGkeConfig().getIpRangeServicesName());

    String terraformPlanCMD =
        String.format(
            "terraform plan -var=gcp_project=%s -var=spanner_config=%s -var=gke_config=%s",
            config.getInfraConfig().getInstanceConfig().getProjectId(),
            new JSONObject(spannerConfig),
            new JSONObject(gkeConfig));
    String terraformApplyCMD =
        String.format(
            "terraform apply -var=gcp_project=%s -var=spanner_config=%s -var=gke_config=%s --auto-approve",
            config.getInfraConfig().getInstanceConfig().getProjectId(),
            new JSONObject(spannerConfig),
            new JSONObject(gkeConfig));

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
