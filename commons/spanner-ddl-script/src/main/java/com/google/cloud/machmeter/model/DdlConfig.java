package com.google.cloud.machmeter.model;

public class DdlConfig {

  private InstanceConfig instanceConfig;
  private String schemaFilePath;

  public InstanceConfig getInstanceConfig() {
    return instanceConfig;
  }

  public void setInstanceConfig(InstanceConfig instanceConfig) {
    this.instanceConfig = instanceConfig;
  }

  public String getSchemaFilePath() {
    return schemaFilePath;
  }

  public void setSchemaFilePath(String schemaFilePath) {
    this.schemaFilePath = schemaFilePath;
  }
}
