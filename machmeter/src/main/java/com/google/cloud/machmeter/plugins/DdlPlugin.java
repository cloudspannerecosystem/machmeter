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

import com.google.cloud.machmeter.ddl.SpannerJdbcDdl;
import com.google.cloud.machmeter.model.DdlConfig;
import com.google.cloud.machmeter.model.SetupConfig;
import com.google.inject.Inject;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class DdlPlugin implements Plugin<SetupConfig> {

  private final SpannerJdbcDdl spannerJdbcDdl;

  @Inject
  public DdlPlugin(SpannerJdbcDdl spannerJdbcDdl) {
    this.spannerJdbcDdl = spannerJdbcDdl;
  }

  @Override
  public String getPluginName() {
    return "ddlPlugin";
  }

  @Override
  public void execute(SetupConfig config) {
    DdlConfig ddlConfig = config.getDdlConfig();
    try {
      spannerJdbcDdl.executeSqlFile(
          ddlConfig.getInstanceConfig().getProjectId(),
          ddlConfig.getInstanceConfig().getInstanceId(),
          ddlConfig.getInstanceConfig().getDbName(),
          ddlConfig.getSchemaFilePath());
    } catch (SQLException e) {
      throw new IllegalArgumentException("Invalid sql statement.", e);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Invalid schema file path.", e);
    }
  }

  @Override
  public Class<SetupConfig> getPluginConfigClass() {
    return SetupConfig.class;
  }
}
