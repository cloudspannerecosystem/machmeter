---
layout: default
title: Execution and Cleanup
nav_order: 4
description: "Executing a POC."
---

# Execution
{: .no_toc }

Using the Machmeter `execute` command to start executing a Performance POC template.

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

## Executing Performance Test

Executing perf test involves executing the `execute` command of machmeter with
perf jmeter script as input. This script, when executed, will execute the input
jmeter script.

This will execute the Performance test distributed across the GKE cluster
created earlier.

For monitoring, server side performance can be seen in the spanner
dashboard. For the client, the Grafana dashboard can be used. Please see below
for details.

### Configuration File Definition

<div class="execute-config" markdown="1">

| Key name                 | Description                        | 
|:-------------------------|:-----------------------------------| 
| namespace                | Name fo the test                   | 
| jMeterTemplatePath       | Path to Perf Jmeter Script         | 
| jMeterParams.project     | Google Cloud Project Id            | 
| jMeterParams.instance    | Name of the instance to be created | 
| jMeterParams.database    | Name of the database to be created | 
| jMeterParams.connections | Spanner Connection Counts          |

</div>

{: .note }
Please refer to the sample file for example values.

This is a [sample file](../machmeter/resource/sample-machmeter-execute-perf-test-config.json) with the above defined configuration

### Execution Command
```bash
$ java -jar target/machmeter/machmeter.jar execute path-to-config-file.json
```

## Cleanup

Cleanup involves executing the `cleanup` command of machmeter with
spanner instances config, GKE cluster config and path to the data load SQL script
created in the earlier steps. Here, the config file remains the same as in Setup.
This step deletes the instances and clusters created.

### Configuration File Definition
This is a [sample file](../machmeter/resource/sample-machmeter-setup-config.json) with the above defined configuration.

Refer to the Setup section for details on the config file.

### Execution Command
```bash
$ java -jar target/machmeter/machmeter.jar cleanup path-to-config-file.json
```


