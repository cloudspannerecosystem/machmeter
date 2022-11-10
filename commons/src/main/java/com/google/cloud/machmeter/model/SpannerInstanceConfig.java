package com.google.cloud.machmeter.model;

import com.google.gson.annotations.SerializedName;

public class SpannerInstanceConfig {

  @SerializedName(value = "instance_name", alternate = "instanceId")
  private String instanceId;

  @SerializedName(value = "database_name", alternate = "dbName")
  private String dbName;

  private transient String projectId;

  @SerializedName(value = "configuration")
  private String configuration;

  @SerializedName(value = "display_name", alternate = "displayName")
  private String displayName;

  @SerializedName(value = "processing_units", alternate = "processingUnits")
  private int processingUnits;

  @SerializedName(value = "environment")
  private String environment;

  public String getInstanceId() {
    return instanceId;
  }

  public void setInstanceId(String instanceId) {
    this.instanceId = instanceId;
  }

  public String getDbName() {
    return dbName;
  }

  public void setDbName(String dbName) {
    this.dbName = dbName;
  }

  public String getProjectId() {
    return projectId;
  }

  public void setProjectId(String projectId) {
    this.projectId = projectId;
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

  public int getProcessingUnits() {
    return processingUnits;
  }

  public void setProcessingUnits(int processingUnits) {
    this.processingUnits = processingUnits;
  }

  public String getEnvironment() {
    return environment;
  }

  public void setEnvironment(String environment) {
    this.environment = environment;
  }
}