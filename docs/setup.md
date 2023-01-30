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
| gkeConfig.service_account_json                    | Path to service account's json file                                     | 
| jMeterParams.spannerInstanceConfig.projectId      | Google Cloud Project Id (Should be same as in previoud keys)            | 
| jMeterParams.spannerInstanceConfig.instanceId     | Name of the instance to be created (Should be same as in previoud keys) | 
| jMeterParams.spannerInstanceConfig.dbName         | Name of the database to be created (Should be same as in previoud keys) | 
| jMeterParams.spannerInstanceConfig.configuration  | Region in which the spanner instance is to be created                   | 
| jMeterParams.schemaFilePath                       | Path to the Schema file.                                                |

</div>

{: .note }
Please refer to the sample file for example values.


This is a [sample file](../commons/resource/sample-machmeter-setup-config.json) with the above defined configuration.

### Execution Command
```bash
$ java -jar target/machmeter/machmeter.jar setup path-to-config-file.json
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
| namespace                | Name of the test                   | 
| jMeterTemplatePath       | Path to Data load Jmeter Script    | 
| jMeterParams.project     | Google Cloud Project Id            | 
| jMeterParams.instance    | Name of the instance to be created | 
| jMeterParams.database    | Name of the database to be created | 
| jMeterParams.connections | Spanner Connection Counts          |

</div>

{: .note }
Please refer to the sample file for example values.

This is a [sample file](../commons/resource/sample-machmeter-execute-data-load-config.json) with the above defined configuration.

### Execution Command
```bash
$ java -jar target/machmeter/machmeter.jar execute path-to-config-file.json
```