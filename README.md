# Machmeter

## Overview

Machmeter is an open source tool for performance benchmarking of Cloud Spanner.
This repository contains Machmeter code, usage instructions and a few example
use-cases. Users can clone any of these use-case and edit them to imitate their 
specific use-case.

## Use-cases

- [Ledger](./usecases/finance/ledger):
- [IMDB](./usecases/entertainment/imdb):
- [Cart](./usecases/shopping/cart):

## Requirements

### Cloud Environment

__We expected to set organization policy default__

- [Google Cloud Project](https://cloud.google.com/resource-manager/docs/creating-managing-projects)
- [Google Account](https://cloud.google.com/iam/docs/overview?hl=ja#google_account)
    - It needs roles/owner permission on your project

__Currently, we only support Service Account for authentication.__

- [Service Account](https://cloud.google.com/iam/docs/creating-managing-service-accounts#creating)
    - It needs roles/owner permission on your project
- [Service Account Key file](https://cloud.google.com/iam/docs/creating-managing-service-account-keys#creating)

```bash
# Example to create account and keyfile
$ export SA_NAME=terraformer
$ export PROJECT_ID=test
# Create Service Account
$ gcloud iam service-accounts create $SA_NAME \
    --description="Operation service account for spanner stress demo" \
    --display-name=$SA_NAME
$ gcloud projects add-iam-policy-binding $PROJECT_ID \
    --member="serviceAccount:${SA_NAME}@${PROJECT_ID}.iam.gserviceaccount.com" \
    --role="roles/owner"
# Download a key file
$ export KEY_FILE=terraformer.json
$ gcloud iam service-accounts keys create $KEY_FILE \
    --iam-account=${SA_NAME}@${PROJECT_ID}.iam.gserviceaccount.com
# We recommend key file type is JSON
```

### Deploy Tools

__Following tools will need to be installed:__
- [JDV & JVM](https://openjdk.org/) (auther version >= 8)
- [Jmeter](https://jmeter.apache.org/) (auther version >= 5.5)
- [Terraform Cli](https://developer.hashicorp.com/terraform/downloads) (auther version >= 1.3.5)
- [kubectl](https://kubernetes.io/docs/tasks/tools/) (auther version >= v1.25.4)
- [Maven](https://maven.apache.org/) (auther version >= 3.6.3)

## Project structure
- [Machmeter](./commons): this is a maven project containing the code for the tools.
It has broadly 2 modules, an __Orchestrator layer__ and __Plugins__. 
  - __Orchestrator Layer__: this layer handles the input and reads the provided 
  config. Further, based on the input, it decides which plugins need to be
  executed.
  - __Plugins__: this layer contains plugins for various stages of the benchmarking.
    - [MachmeterStatePlugin](./commons/src/main/java/com/google/cloud/machmeter/plugins/MachmeterStatePlugin.java):
    This plugin sets up the local directories for preserving __Machmeter__ state.
    - [InfraSetupPlugin](./commons/src/main/java/com/google/cloud/machmeter/plugins/InfraSetupPlugin.java):
    This plugin executes terraform scripts to setup the Spanner Instances
    and GKE cluster for Jmeter clients.
    - [DdlPlugin](./commons/src/main/java/com/google/cloud/machmeter/plugins/DdlPlugin.java):
    This plugin is for loading data into the Spanner Instances.
    - [ExecutePlugin](./commons/src/main/java/com/google/cloud/machmeter/plugins/ExecutePlugin.java):
    This plugin executes the benchmarking tests provided in the use-case.
  - [Terraform](./commons/src/main/resources/terraform): This folder contains
  a terraform script for setting up Spanner Instances and GKE clusters. It also 
  contains config files for the Grafana Dashboard.
- [Use-cases](./usecases): all the sample use-cases reside into this folder
categorized into different folders.

## How to execute a benchmark test

### Setup env settings

__You should set own environment by followings:__

```bash
$ git clone https://github.com/kazu0716/spanner-stress-test-demo.git
$ cd cloud-spanner-poc-validation/commons

# Building the maven project
$ mvn clean package -P assembly

# You provide the path to service accounts key.
$ export GOOGLE_APPLICATION_CREDENTIALS=~/service-accounts.json
```

#### Execute Benchmarking

__Execution Command__:
```bash
$ java -jar target/machmeter/machmeter.jar <command> <Path To Json Config>
```

__Executing a benchmark involve 4 independent steps:__
- Instance Setup
- Data Load
- Executing Perf Test
- Cleanup

##### Instances setup

Instances setup involve executing the __setup__ command of machmeter with
spanner instances config, GKE cluster config and path to Schema file
containing SQL command describing the database structure.
One important thing to remember is that the spanner config will need to remain the
same in all these steps.

###### Execution Command
```bash
$ java -jar target/machmeter/machmeter.jar setup path-to-config-file.json
```

###### Config File ([Sample file](./commons/resource/sample-machmeter-setup-config.json))

|                                                   |                                                                         | 
|---------------------------------------------------|-------------------------------------------------------------------------| 
| Key name                                          | Description                                                             | 
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
** Please refer to the sample file for example values


##### Data Load

Data load involves executing the __execute__ command of machmeter with
data load jmeter script as input. This script, when executed, will load a 
randomized set of data onto a given mentioned spanner database.

This will also create CSV files containing the keys for created data. These
CSV will be used as a source for parameters in queries in the Perf test.

###### Execution Command
```bash
$ java -jar target/machmeter/machmeter.jar execute path-to-config-file.json
```

###### Config File ([Sample file](./commons/resource/sample-machmeter-execute-data-load-config.json))

|                          |                                    | 
|--------------------------|------------------------------------| 
| Key name                 | Description                        | 
| namespace                | Name of the test                   | 
| jMeterTemplatePath       | Path to Data load Jmeter Script    | 
| jMeterParams.project     | Google Cloud Project Id            | 
| jMeterParams.instance    | Name of the instance to be created | 
| jMeterParams.database    | Name of the database to be created | 
| jMeterParams.connections | Spanner Connection Counts          |
** Please refer to the sample file for example values

##### Executing Perf Test

Executing perf test involves executing the __execute__ command of machmeter with
perf jmeter script as input. This script, when executed, will execute the input
jmeter script.

This will execute the Performance test distributed across the GKE cluster
created earlier.

For monitoring, server side performance can be seen in the spanner
dashboard. For the client, the Grafana dashboard can be used. Please see below
for details.

###### Execution Command
```bash
$ java -jar target/machmeter/machmeter.jar execute path-to-config-file.json
```

###### Config File ([Sample file](./commons/resource/sample-machmeter-execute-perf-test-config.json))

|                          |                                    | 
|--------------------------|------------------------------------| 
| Key name                 | Description                        | 
| namespace                | Name fo the test                   | 
| jMeterTemplatePath       | Path to Perf Jmeter Script         | 
| jMeterParams.project     | Google Cloud Project Id            | 
| jMeterParams.instance    | Name of the instance to be created | 
| jMeterParams.database    | Name of the database to be created | 
| jMeterParams.connections | Spanner Connection Counts          |
** Please refer to the sample file for example values

##### Cleanup

Cleanup involves executing the __cleanup__ command of machmeter with
spanner instances config, GKE cluster config and path to the data load SQL script
created in the earlier steps. Here, the config file remains the same as in Setup.
This step deletes the instances and clusters created.

###### Execution Command
```bash
$ java -jar target/machmeter/machmeter.jar cleanup path-to-config-file.json
```

###### Config File ([Sample file](./commons/resource/sample-machmeter-setup-config.json))

Refer to the Setup section for details on the config file.

### Watch the result

__You can check the result in real time by followings:__

- [Google Cloud's operations suite](https://cloud.google.com/products/operations)
    - [Cloud Monitoring](https://cloud.google.com/monitoring/charts/metrics-selector)
    - [Cloud Logging](https://cloud.google.com/logging/docs/view/logs-explorer-interface#getting_started)
    - [Cloud Trace](https://cloud.google.com/trace/docs/viewing-details)

- [Locust plugin dashboard](https://github.com/SvenskaSpel/locust-plugins/blob/master/locust_plugins/dashboards/README.md)

![](https://github.com/SvenskaSpel/locust-plugins/raw/master/locust_plugins/dashboards/screenshots/main_dashboard.png)

#### How to connect to grafana dashboard

```bash
# SSH port-forwording to grafana
$ make open.grafana
Grafana dashboard: https://3443-xxxxyyyzzzz

# Cloud Shell envronment(You can access via proxy)
$ open https://3443-xxxxyyyzzzz

# Other environment
$ open http://localhost:3443
```

## Customizing Use-case

Users can customize the sample use-cases to imitate the requirements. This 
requires cloning the use-case that is closest to their requirements and modifying
its components.

### Components of a Use-case

To demonstrate this, we will be using [Ledger](./usecases/finance/ledger) as a
sample use-case.

- [Schema](./usecases/finance/ledger/spanner-interactions/schema/schema.sql):
This describes the database structure and should contain all your DDL commands.
This needs to be modified containing the DDL command of the respective application
to be tested.
- [Data load Jmeter Script](./usecases/finance/ledger/spanner-interactions/data-load/scenario-1-initial-load.jmx):
This Jmeter script performs 2 tasks. One it inserts randomized data into the spanner database
and second it creates CSV files containing keys required in queries in perf execution.
  - For these, there are 2 thread groups. First thread group, `Insert Data` is responsible
  for inserting data into the database. This contains `JDBC Requests` which have
  `insert` statements for all the tables needed to be populated.
  - For any computed data within the tables, `update` statements post all the insert statements
  to be added.
  - Number of Rows to be added can be controlled by `users` and `iterations`. 
  `users` control the number of threads. `iterations` control the number of iterations
  within a single thread. So, the total rows added will be `users * iterations`.
  - Second thread group, `Create Sampled CSV Data` is responsible for querying all
  the keys required in the perf test and dumping them into CSV files.
- [Perf Test Jmeter Script](./usecases/finance/ledger/spanner-interactions/perf-test/finance-perf.jmx):
This has the following components:
  - `create spanner client(setup Thread Group)`: Spanner connections are created in this group.
  - `Master Thread Group`: this group contains all the read/write queries for the test.
  - `Throughput Controller`: these controllers control the percentage of queries in total
  queries sent to the database.
  - `CSV Data Set Config`: these configs import the CSV files created in earlier scripts.

## Reference

## Contribution

- [CONTRIBUTING docs](./docs/contributing.md)

## License

This is [Apache 2.0 License](./LICENSE)

## Note

This is not an officially supported Google product