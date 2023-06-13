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
import com.google.cloud.machmeter.model.GKEConfig;
import com.google.cloud.machmeter.model.SetupConfig;
import com.google.cloud.machmeter.model.SpannerInstanceConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CleanupPlugin implements Plugin<SetupConfig> {
  private static final Logger logger = Logger.getLogger(InfraSetupPlugin.class.getName());

  private final ShellExecutor shellExecutor;

  @Inject
  public CleanupPlugin(ShellExecutor shellExecutor) {
    this.shellExecutor = shellExecutor;
  }

  @Override
  public String getPluginName() {
    return "cleanup";
  }

  @Override
  public void execute(SetupConfig config) {
    // Spanner instance and database config
    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    SpannerInstanceConfig spannerInstanceConfig =
        config.getInfraConfig().getSpannerInstanceConfig();
    GKEConfig gkeConfig = config.getInfraConfig().getGkeConfig();
    String terraformDestroyCMD =
        String.format(
            "terraform destroy -var=gcp_project=%s -var='spanner_config=%s' -var='gke_config=%s'",
            spannerInstanceConfig.getProjectId(),
            gson.toJson(spannerInstanceConfig),
            gson.toJson(gkeConfig));
    try {
      logger.log(Level.INFO, "Executing {0}", terraformDestroyCMD);
      shellExecutor.run(terraformDestroyCMD, "machmeter_output/terraform");
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Class<SetupConfig> getPluginConfigClass() {
    return SetupConfig.class;
  }
}
