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

  private static final String DEFAULT_JVM_ARGS = "-Xms1g -Xmx1g";
  private static final int DEFAULT_SLAVE_REPLICA_COUNT = 2;
  private static final String DEFAULT_CPU_REQUEST = "1000m";
  private static final String DEFAULT_MEMORY_REQUEST = "1Gi";

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

  @SerializedName(value = "machine_type", alternate = "machineType")
  @Expose
  private String machineType;

  @SerializedName(value = "node_locations", alternate = "nodeLocations")
  @Expose
  private String nodeLocations;

  @SerializedName(value = "master_jvm_args", alternate = "masterJvmArgs")
  @Expose
  private String masterJvmArgs;

  @SerializedName(value = "slave_jvm_args", alternate = "slaveJvmArgs")
  @Expose
  private String slaveJvmArgs;

  @SerializedName(value = "master_cpu_request", alternate = "masterCPURequest")
  @Expose
  private String masterCPURequest;

  @SerializedName(value = "master_memory_request", alternate = "masterMemoryRequest")
  @Expose
  private String masterMemoryRequest;

  @SerializedName(value = "slave_cpu_request", alternate = "slaveCPURequest")
  @Expose
  private String slaveCPURequest;

  @SerializedName(value = "slave_memory_request", alternate = "slaveMemoryRequest")
  @Expose
  private String slaveMemoryRequest;

  @SerializedName(value = "slave_replica_count", alternate = "slaveReplicaCount")
  @Expose
  private int slaveReplicaCount;

  @SerializedName(value = "min_count", alternate = "minCount")
  @Expose
  private int minCount;

  @SerializedName(value = "max_count", alternate = "maxCount")
  @Expose
  private int maxCount;

  @SerializedName(value = "initial_node_count", alternate = "initialNodeCount")
  @Expose
  private int initialNodeCount;

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

  public String getMasterJvmArgs() {
    if (masterJvmArgs == null) masterJvmArgs = DEFAULT_JVM_ARGS;
    ;
    return masterJvmArgs;
  }

  public void setMasterJvmArgs(String masterJvmArgs) {
    if (masterJvmArgs == null) {
      this.masterJvmArgs = DEFAULT_JVM_ARGS;
      return;
    }
    this.masterJvmArgs = masterJvmArgs;
  }

  public String getSlaveJvmArgs() {
    if (slaveJvmArgs == null) slaveJvmArgs = DEFAULT_JVM_ARGS;
    ;
    return slaveJvmArgs;
  }

  public void setSlaveJvmArgs(String slaveJvmArgs) {
    if (slaveJvmArgs == null) {
      this.slaveJvmArgs = DEFAULT_JVM_ARGS;
      return;
    }
    this.slaveJvmArgs = slaveJvmArgs;
  }

  public String getMasterCPURequest() {
    if (masterCPURequest == null) masterCPURequest = DEFAULT_CPU_REQUEST;
    ;
    return masterCPURequest;
  }

  public void setMasterCPURequest(String masterCPURequest) {
    if (masterCPURequest == null) {
      this.masterCPURequest = DEFAULT_CPU_REQUEST;
      return;
    }
    ;
    this.masterCPURequest = masterCPURequest;
  }

  public String getMasterMemoryRequest() {
    if (masterMemoryRequest == null) masterMemoryRequest = DEFAULT_MEMORY_REQUEST;
    ;
    return masterMemoryRequest;
  }

  public void setMasterMemoryRequest(String masterMemoryRequest) {
    if (masterMemoryRequest == null) {
      this.masterMemoryRequest = DEFAULT_MEMORY_REQUEST;
      return;
    }
    ;
    this.masterMemoryRequest = masterMemoryRequest;
  }

  public String getSlaveCPURequest() {
    if (slaveCPURequest == null) slaveCPURequest = DEFAULT_MEMORY_REQUEST;
    ;
    return slaveCPURequest;
  }

  public void setSlaveCPURequest(String slaveCPURequest) {
    if (slaveCPURequest == null) {
      this.slaveCPURequest = DEFAULT_CPU_REQUEST;
      return;
    }
    ;
    this.slaveCPURequest = slaveCPURequest;
  }

  public String getSlaveMemoryRequest() {
    if (slaveMemoryRequest == null) slaveMemoryRequest = DEFAULT_MEMORY_REQUEST;
    ;
    return slaveMemoryRequest;
  }

  public void setSlaveMemoryRequest(String slaveMemoryRequest) {
    if (slaveMemoryRequest == null) {
      this.slaveMemoryRequest = DEFAULT_MEMORY_REQUEST;
      return;
    }
    ;
    this.slaveMemoryRequest = slaveMemoryRequest;
  }

  public int getSlaveReplicaCount() {
    if (slaveReplicaCount <= 0) slaveReplicaCount = DEFAULT_SLAVE_REPLICA_COUNT;
    ;
    return slaveReplicaCount;
  }

  public void setSlaveReplicaCount(int slaveReplicaCount) {
    if (slaveReplicaCount <= 0) {
      this.slaveReplicaCount = DEFAULT_SLAVE_REPLICA_COUNT;
      return;
    }
    ;
    this.slaveReplicaCount = slaveReplicaCount;
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
