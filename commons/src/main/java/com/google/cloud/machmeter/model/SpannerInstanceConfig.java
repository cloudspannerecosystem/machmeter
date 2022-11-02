package com.google.cloud.machmeter.model;

public class SpannerInstanceConfig {

  private String instanceName;
  private String databaseName;
  private String configuration;
  private String displayName;
  private String processingUnits;
  private String environment;

  public String getInstanceName() {
    return instanceName;
  }

  public void setInstanceName(String instanceName) {
    this.instanceName = instanceName;
  }

  public String getDatabaseName() {
    return databaseName;
  }

  public void setDatabaseName(String databaseName) {
    this.databaseName = databaseName;
  }

  public String getConfiguration() {
    return configuration;
  }

  public void setConfiguration(String configuration) {
    this.configuration = configuration;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getProcessingUnits() {
    return processingUnits;
  }

  public void setProcessingUnits(String processingUnits) {
    this.processingUnits = processingUnits;
  }

  public String getEnvironment() {
    return environment;
  }

  public void setEnvironment(String environment) {
    this.environment = environment;
  }
}
