package com.google.cloud.machmeter.model;

public class MachmeterConfig {

  private DdlConfig ddlConfig;
  private SpannerInstanceConfig spannerInstanceConfig;
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

  public SpannerInstanceConfig getSpannerInstanceConfig() {
    return spannerInstanceConfig;
  }

  public void setSpannerInstanceConfig(SpannerInstanceConfig spannerInstanceConfig) {
    this.spannerInstanceConfig = spannerInstanceConfig;
  }
}
