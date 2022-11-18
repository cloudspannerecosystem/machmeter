package com.google.cloud.machmeter.model;

public class DdlConfig {

  private SpannerInstanceConfig spannerInstanceConfig;
  private String schemaFilePath;

  public SpannerInstanceConfig getInstanceConfig() {
    return spannerInstanceConfig;
  }

  public void setInstanceConfig(SpannerInstanceConfig spannerInstanceConfig) {
    this.spannerInstanceConfig = spannerInstanceConfig;
  }

  public String getSchemaFilePath() {
    return schemaFilePath;
  }

  public void setSchemaFilePath(String schemaFilePath) {
    this.schemaFilePath = schemaFilePath;
  }
}
