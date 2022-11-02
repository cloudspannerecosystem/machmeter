package com.google.cloud.machmeter.model;

public class GKEConfig {

  private String clusterName;
  private String region;
  private String network;
  private String subnetwork;
  private String ipRangePodsName;
  private String ipRangeServicesName;

  public String getClusterName() {
    return clusterName;
  }

  public void setClusterName(String clusterName) {
    this.clusterName = clusterName;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getNetwork() {
    return network;
  }

  public void setNetwork(String network) {
    this.network = network;
  }

  public String getSubnetwork() {
    return subnetwork;
  }

  public void setSubnetwork(String subnetwork) {
    this.subnetwork = subnetwork;
  }

  public String getIpRangePodsName() {
    return ipRangePodsName;
  }

  public void setIpRangePodsName(String ipRangePodsName) {
    this.ipRangePodsName = ipRangePodsName;
  }

  public String getIpRangeServicesName() {
    return ipRangeServicesName;
  }

  public void setIpRangeServicesName(String ipRangeServicesName) {
    this.ipRangeServicesName = ipRangeServicesName;
  }
}
