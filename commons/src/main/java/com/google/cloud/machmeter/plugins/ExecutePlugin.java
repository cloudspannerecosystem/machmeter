package com.google.cloud.machmeter.plugins;

import com.google.cloud.machmeter.helpers.ShellExecutor;
import com.google.cloud.machmeter.model.ConfigInterface;
import com.google.cloud.machmeter.model.ExecuteConfig;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/*
Logic to create the commands for the following:
1. Forms the command to read the supplied JMeter file and upload it to GKE master -> forms a new path for JMX
file that is on the master.
2. Create the jmeter command to be executed - takes input from the jMeterParams and the location of the JMX
file inside the cluster.
 */
public class ExecutePlugin implements PluginInterface {
  @Override
  public String getName() {
    return "execute-plugin";
  }

  @Override
  public void execute(ConfigInterface config) {
    ExecuteConfig executeConfig;
    if (config instanceof ExecuteConfig) {
      executeConfig = (ExecuteConfig) config;
    }
    else {
      throw new RuntimeException("Cast error!");
    }
    //TODO: Use Dependency Injection throughout Machmeter.
    ShellExecutor shellExecutor = new ShellExecutor();
    String jMeterArgs = convertMapToJMeterArgs(executeConfig.getjMeterParams());
  }

  private String convertMapToJMeterArgs(final Map<String, String> jMeterParamMap) {
    List<String> argList = jMeterParamMap.entrySet()
                                      .stream()
                                      .map(e -> String.format("-J%s=%s", e.getKey(), e.getValue()))
                                      .collect(Collectors.toList());
    return String.join(" ", argList);
  }

}
