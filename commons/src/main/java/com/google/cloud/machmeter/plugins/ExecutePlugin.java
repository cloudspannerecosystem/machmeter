package com.google.cloud.machmeter.plugins;

import com.google.cloud.machmeter.model.ConfigInterface;
import com.google.cloud.machmeter.model.ExecuteConfig;


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

  }

}
