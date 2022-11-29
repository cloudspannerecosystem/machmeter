package com.google.cloud.machmeter.plugins;

import com.google.cloud.machmeter.ddl.SpannerJdbcDdl;
import com.google.cloud.machmeter.model.ConfigInterface;
import com.google.cloud.machmeter.model.DdlConfig;
import com.google.cloud.machmeter.model.SetupConfig;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class DdlPlugin implements PluginInterface {

  @Override
  public String getName() {
    return "ddlPlugin";
  }

  @Override
  public void execute(ConfigInterface config) {
    SetupConfig setupConfig;
    if (config instanceof SetupConfig) {
      setupConfig = (SetupConfig) config;
    } else {
      throw new RuntimeException("Cast error!");
    }
    DdlConfig ddlConfig = setupConfig.getDdlConfig();
    try {
      SpannerJdbcDdl.executeSqlFile(
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
}
