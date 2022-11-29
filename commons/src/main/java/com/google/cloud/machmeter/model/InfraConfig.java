package com.google.cloud.machmeter.model;

public class InfraConfig {

  private SpannerInstanceConfig spannerInstanceConfig;

  private GKEConfig gkeConfig;

  public SpannerInstanceConfig getSpannerInstanceConfig() {
    return spannerInstanceConfig;
  }

  public void setSpannerInstanceConfig(SpannerInstanceConfig spannerInstanceConfig) {
    this.spannerInstanceConfig = spannerInstanceConfig;
  }

  public GKEConfig getGkeConfig() {
    return gkeConfig;
  }

  public void setGkeConfig(GKEConfig gkeConfig) {
    this.gkeConfig = gkeConfig;
  }
}
