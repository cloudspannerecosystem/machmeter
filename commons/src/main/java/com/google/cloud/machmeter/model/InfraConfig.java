package com.google.cloud.machmeter.model;

public class InfraConfig {

  private InstanceConfig instanceConfig;

  private GKEConfig gkeConfig;

  public InstanceConfig getInstanceConfig() {
    return instanceConfig;
  }

  public void setInstanceConfig(InstanceConfig instanceConfig) {
    this.instanceConfig = instanceConfig;
  }

  public GKEConfig getGkeConfig() {
    return gkeConfig;
  }

  public void setGkeConfig(GKEConfig gkeConfig) {
    this.gkeConfig = gkeConfig;
  }
}
