package com.google.cloud.machmeter.model;

public class SetupConfig implements ConfigInterface {
  private InfraConfig infraConfig;
  private DdlConfig ddlConfig;

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
