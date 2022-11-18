package com.google.cloud.machmeter.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GKEConfig {

  @SerializedName(value = "cluster_name", alternate = "clusterName")
  @Expose
  private String clusterName;

  @SerializedName(value = "region")
  @Expose
  private String region;

  @SerializedName(value = "network")
  @Expose
  private String network;

  @SerializedName(value = "subnetwork")
  @Expose
  private String subnetwork;

  @SerializedName(value = "ip_range_pods_name", alternate = "ipRangePodsName")
  @Expose
  private String ipRangePodsName;

  @SerializedName(value = "ip_range_services_name", alternate = "ipRangeServicesName")
  @Expose
  private String ipRangeServicesName;

  @SerializedName(value = "namespace")
  @Expose
  private String namespace;

  @SerializedName(value = "service_account_json", alternate = "serviceAccountJson")
  @Expose
  private String serviceAccountJson;

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

  public String getNamespace() {
    return namespace;
  }

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public String getServiceAccountJson() {
    return serviceAccountJson;
  }

  public void setServiceAccountJson(String serviceAccountJson) {
    this.serviceAccountJson = serviceAccountJson;
  }
}
