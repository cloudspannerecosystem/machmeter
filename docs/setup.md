---
layout: default
title: Infrastructure and Data Setup
nav_order: 3
description: "Setting up a POC."
---

# Setup
{: .no_toc }

Using the Machmeter `setup` command to start creating resources and the `execute` command to load randomised data into Cloud Spanner with the help of a JMeter template.

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

## Resource Creation using IaaC

Required resources involve executing the `setup` command of machmeter with
spanner instance config, GKE cluster config and path to Schema file
containing SQL command describing the database structure.
One important thing to remember is that the spanner config will need to remain the
same in all these steps.

{: .note}
Machmeter uses Terraform to create a GKE cluster which is used to generate the load. 
Refer to [Kubernetes concepts cheat sheet](https://medium.com/hashmapinc/30-second-kubernetes-concepts-cheat-sheet-98ba813194cb) and [kubectl CLI cheat sheet](https://kubernetes.io/docs/reference/kubectl/cheatsheet/) 
for a quick reference to common concepts and commands.

### Configuration File Definition 

<div class="resource-config" markdown="1">
 
| Key name                                          | Description                                                             | 
|:--------------------------------------------------|:------------------------------------------------------------------------|
| infraConfig.spannerInstanceConfig.projectId       | Google Cloud Project Id                                                 | 
| infraConfig.spannerInstanceConfig.instanceId      | Name of the instance to be created                                      | 
| infraConfig.spannerInstanceConfig.dbName          | Name of the database to be created                                      | 
| infraConfig.spannerInstanceConfig.configuration   | Region in which the spanner instance is to be created                   | 
| infraConfig.spannerInstanceConfig.displayName     | Instance display name                                                   | 
| infraConfig.spannerInstanceConfig.processingUnits | Number of processing units                                              | 
| infraConfig.spannerInstanceConfig.environment     | Creation environment                                                    | 
| gkeConfig.clusterName                             | GKE cluster name                                                        | 
| gkeConfig.namespace                               | GKE cluster name space                                                  | 
| gkeConfig.region                                  | GKE cluster region                                                      | 
| gkeConfig.network                                 | GKE cluster network                                                     | 
| gkeConfig.subnetwork                              | GKE cluster sub network                                                 | 
| gkeConfig.ipRangePodsName                         | GKE cluster IP range Pod's Name                                         | 
| gkeConfig.ipRangeServicesName                     | GKE cluster IP range Services Name                                      | 
| gkeConfig.google_service_account                  | Name of Google Cloud Service Account to be created                      | 
| gkeConfig.kube_service_account                    | Name of GKE Service Account to be created                               | 
| jMeterParams.spannerInstanceConfig.projectId      | Google Cloud Project Id (Should be same as in previoud keys)            | 
| jMeterParams.spannerInstanceConfig.instanceId     | Name of the instance to be created (Should be same as in previoud keys) | 
| jMeterParams.spannerInstanceConfig.dbName         | Name of the database to be created (Should be same as in previoud keys) | 
| jMeterParams.spannerInstanceConfig.configuration  | Region in which the spanner instance is to be created                   | 
| jMeterParams.schemaFilePath                       | Path to the Schema file.                                                |

</div>

{: .note }
Please refer to the sample file for example values.


This is a [sample file](https://github.com/cloudspannerecosystem/machmeter/blob/master/machmeter/usecases/finance/ledger/sample-configs/setup.json) with the above defined configuration.

### Execution Command
```bash
java -jar target/machmeter/machmeter.jar setup path-to-config-file.json
```

## Data Load

Data load involves executing the `execute` command of machmeter with
data load jmeter script as input. This script, when executed, will load a 
randomized set of data onto a given mentioned spanner database.

This will also create CSV files containing the keys for created data. These
CSV will be used as a source for parameters in queries in the Perf test.

### Configuration File Definition 

<div class="data-load-config" markdown="1">

| Key name                 | Description                        | 
|:-------------------------|:-----------------------------------| 
| namespace                | Name of the GKE namespace          | 
| jMeterTemplatePath       | Path to Data load Jmeter Script    | 
| jMeterParams.project     | Google Cloud Project Id            | 
| jMeterParams.instance    | Name of the instance to be created | 
| jMeterParams.database    | Name of the database to be created | 
| jMeterParams.connections | Spanner Connection Counts          |

</div>

{: .note }
Please refer to the sample file for example values.

This is a [sample file](https://github.com/cloudspannerecosystem/machmeter/blob/master/machmeter/usecases/finance/ledger/sample-configs/data-load.json) with the above defined configuration.

### Execution Command

{: .note }
Ensure you have the GKE credentials configured via: `gcloud container clusters get-credentials CLUSTER_NAME --region=COMPUTE_REGION`

```bash

java -jar target/machmeter/machmeter.jar execute path-to-config-file.json
```