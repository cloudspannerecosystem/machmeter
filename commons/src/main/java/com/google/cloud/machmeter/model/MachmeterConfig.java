package com.google.cloud.machmeter.model;

public class MachmeterConfig {
  private InfraConfig infraConfig;
  private DdlConfig ddlConfig;
  private GKEConfig gkeConfig;

  public GKEConfig getGkeConfig() {
    return gkeConfig;
  }

  public void setGkeConfig(GKEConfig gkeConfig) {
    this.gkeConfig = gkeConfig;
  }

  public DdlConfig getDdlConfig() {
    return ddlConfig;
  }

  public void setDdlConfig(DdlConfig ddlConfig) {
    this.ddlConfig = ddlConfig;
  }

  public InfraConfig getInfraConfig() {
    return infraConfig;
  }

  public void setInfraConfig(InfraConfig infraConfig) {
    this.infraConfig = infraConfig;
  }
}
