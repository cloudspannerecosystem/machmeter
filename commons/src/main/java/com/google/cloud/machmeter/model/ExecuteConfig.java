package com.google.cloud.machmeter.model;

import java.util.Map;

public class ExecuteConfig implements ConfigInterface {
  private String namespace;

  private String jMeterTemplatePath;

  private Map<String, String> jMeterParams;

  public String getNamespace() {
    return namespace;
  }

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public void setjMeterTemplatePath(String jMeterTemplatePath) {
    this.jMeterTemplatePath = jMeterTemplatePath;
  }

  public void setjMeterParams(Map<String, String> jMeterParams) {
    this.jMeterParams = jMeterParams;
  }

  public String getjMeterTemplatePath() {
    return jMeterTemplatePath;
  }

  public Map<String, String> getjMeterParams() {
    return jMeterParams;
  }
}
