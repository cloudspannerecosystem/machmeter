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

import com.google.cloud.machmeter.helpers.ResourceHandler;
import com.google.cloud.machmeter.model.SetupConfig;
import com.google.inject.Inject;
import java.io.File;
import java.nio.file.Paths;

/*
This plugin creates a local directory on the user system to preserve machmeter state.
Currently, only the `machmeter_output/terraform` directory which forms the execution environment for terraform CLI to run.
In the future, this directory can be used to store credentials, session information, metadata etc.
 */
public class MachmeterStatePlugin implements Plugin<SetupConfig> {

  private final ResourceHandler resourceCopy;

  @Inject
  public MachmeterStatePlugin(ResourceHandler resourceHandler) {
    this.resourceCopy = resourceHandler;
  }

  private static final String MACHMETER_OUTPUT_DIR = "machmeter_output";
  private static final String TERRAFORM_DIR = "terraform";

  @Override
  public String getPluginName() {
    return "machmeter-state-setup";
  }

  @Override
  public void execute(SetupConfig config) {
    File machmeterDir = new File(MACHMETER_OUTPUT_DIR);
    if (!machmeterDir.exists()) {
      machmeterDir.mkdirs();
    }
    resourceCopy.copyResourceDirectory(TERRAFORM_DIR, Paths.get(machmeterDir.getAbsolutePath()));
  }

  @Override
  public Class<SetupConfig> getPluginConfigClass() {
    return SetupConfig.class;
  }
}
