/*
 * Copyright 2022 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
