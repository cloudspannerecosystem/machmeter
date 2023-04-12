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

package com.google.cloud.machmeter.plugins;

import com.google.cloud.machmeter.helpers.ShellExecutor;
import com.google.cloud.machmeter.model.ExecuteConfig;
import com.google.inject.Inject;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Logic to create the commands for the following: 1. Forms the command to read the supplied JMeter
 * file and upload it to GKE master -> forms a new path for JMX file that is on the master. 2.
 * Create the jmeter command to be executed - takes input from the jMeterParams and the location of
 * the JMX file inside the cluster.
 */
public class ExecutePlugin implements Plugin<ExecuteConfig> {

  private static final Logger logger = Logger.getLogger(InfraSetupPlugin.class.getName());

  private final ShellExecutor shellExecutor;

  @Inject
  public ExecutePlugin(ShellExecutor shellExecutor) {
    this.shellExecutor = shellExecutor;
  }

  @Override
  public String getPluginName() {
    return "execute-plugin";
  }

  @Override
  public void execute(ExecuteConfig config) {
    String jMeterArgs = convertMapToJMeterArgs(config.getjMeterParams());
    Path cwd = Paths.get(System.getProperty("user.dir"));
    Path fullPath = Paths.get(cwd.toString(), config.getjMeterTemplatePath());
    Path fileName = fullPath.getFileName();
    String kubectlCopy =
        String.format(
            "kubectl cp %s -n %s \"$(kubectl get po -n %s | grep jmeter-master | awk '{print $1}'):/%s\"",
            fullPath, config.getNamespace(), config.getNamespace(), fileName);
    String kubectlExec =
        String.format(
            "kubectl exec -ti -n %s $(kubectl get po -n %s | grep jmeter-master | awk '{print $1}') -- /bin/bash /load_test %s %s",
            config.getNamespace(), config.getNamespace(), fileName, jMeterArgs);
    Runtime.getRuntime()
        .addShutdownHook(
            new Thread(
                () -> {
                  logger.log(Level.INFO, "Shutting down the execute...");
                  String kubectlStop =
                      String.format(
                          "kubectl delete po -n %s $( kubectl get po -n %s | grep jmeter-slave | awk '{print $1}') --grace-period=0",
                          config.getNamespace(), config.getNamespace());
                  try {
                    shellExecutor.run(kubectlStop, "machmeter_output/terraform");
                  } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                  }
                }));
    try {
      logger.log(Level.INFO, "Executing {0}", kubectlCopy);
      shellExecutor.run(kubectlCopy, "machmeter_output/terraform");
      logger.log(Level.INFO, "Executing {0}", kubectlExec);
      shellExecutor.run(kubectlExec, "machmeter_output/terraform");
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  @Override
  public Class<ExecuteConfig> getPluginConfigClass() {
    return ExecuteConfig.class;
  }

  private String convertMapToJMeterArgs(final Map<String, String> jMeterParamMap) {
    List<String> argList =
        jMeterParamMap.entrySet().stream()
            .map(e -> String.format("-G%s=%s", e.getKey(), e.getValue()))
            .collect(Collectors.toList());
    return String.join(" ", argList);
  }
}
