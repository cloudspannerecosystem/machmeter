---
layout: default
title: Home
nav_order: 1
description: "Machmeter is a tool to bootstrap POCs on Cloud Spanner quickly and easily."
permalink: /
---

# Machmeter
{: .fs-9 }

Machmeter is a tool to bootstrap POCs on Cloud Spanner quickly and easily.
{: .fs-6 .fw-300 }

[Learn More](#what-does-machmeter-do){: .btn .btn-primary .fs-5 .mb-4 .mb-md-0 .mr-2 }
[View it on GitHub][Machmeter repo]{: .btn .fs-5 .mb-4 .mb-md-0 }

---

Machmeter is an open source tool for performance benchmarking of Cloud Spanner. This repository contains Machmeter code, usage instructions and a few example use-cases. Users can clone any of these use-case and edit them to imitate their specific use-case.

### What does Machmeter do?
 - Simplifying setup for performance test: This tool provides an easy-to-use CLI which can set up an integrated environment to test on Spanner quickly with pre-built sample test scenarios. It simplifies operations such as setting up a Spanner instance, setting up client metrics collection, setting up a Kubernetes cluster and running an at-scale JMeter template using leader-follower configuration.
 - Randomized data generation for complex schema: Machmeter provides samples for common use-cases to configure JMeter to generate randomized data for a given schema. These templates use JMeter APIs and are GUI driven.
 - Extensible templates for new use cases: Templates can easily be extended/modified to adapt to a customer’s specific use case.  Moreover, you can also quickly implement custom scenarios within this tool with little java code/editing existing scenarios.

### Project Structure

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

## About the project

### Contributing
[CONTRIBUTING docs](./contributing.md)

## License

This is [Apache 2.0 License](../LICENSE)

{: .note }
This is not an officially supported Google product.

[Machmeter repo]: https://github.com/cloudspannerecosystem/machmeter


