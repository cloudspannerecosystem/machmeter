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

  @SerializedName(value = "machine_type", alternate = "machineType")
  @Expose
  private String machineType;

  @SerializedName(value = "node_locations", alternate = "nodeLocations")
  @Expose
  private String nodeLocations;

  @SerializedName(value = "min_count", alternate = "minCount")
  @Expose
  private int minCount;

  @SerializedName(value = "max_count", alternate = "maxCount")
  @Expose
  private int maxCount;

  @SerializedName(value = "initial_node_count", alternate = "initialNodeCount")
  @Expose
  private int initialNodeCount;

  @SerializedName(value = "google_service_account", alternate = "googleServiceAccount")
  @Expose
  private String googleServiceAccount;

  public void setGoogleServiceAccount(String googleServiceAccount) {
    this.googleServiceAccount = googleServiceAccount;
  }
  public String getGoogleServiceAccount() {
    return googleServiceAccount;
  }

  @SerializedName(value = "kube_service_account", alternate = "kubeServiceAccount")
  @Expose
  private String kubeServiceAccount;

  public void setKubeServiceAccount(String kubeServiceAccount) {
    this.kubeServiceAccount = kubeServiceAccount;
  }
  public String getKubeServiceAccount() {
    return kubeServiceAccount;
  }
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

  public String getMachineType() {
    return machineType;
  }

  public void setMachineType(String machineType) {
    this.machineType = machineType;
  }

  public String getNodeLocations() {
    return nodeLocations;
  }

  public void setNodeLocations(String nodeLocations) {
    this.nodeLocations = nodeLocations;
  }

  public int getMinCount() {
    return minCount;
  }

  public void setMinCount(int minCount) {
    this.minCount = minCount;
  }

  public int getMaxCount() {
    return maxCount;
  }

  public void setMaxCount(int maxCount) {
    this.maxCount = maxCount;
  }

  public int getInitialNodeCount() {
    return initialNodeCount;
  }

  public void setInitialNodeCount(int initialNodeCount) {
    this.initialNodeCount = initialNodeCount;
  }
}
