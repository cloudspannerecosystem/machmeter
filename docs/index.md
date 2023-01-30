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

[Get started now](#getting-started){: .btn .btn-primary .fs-5 .mb-4 .mb-md-0 .mr-2 }
[View it on GitHub][Machmeter repo]{: .btn .fs-5 .mb-4 .mb-md-0 }

---

Machmeter is an open source tool for performance benchmarking of Cloud Spanner. This repository contains Machmeter code, usage instructions and a few example use-cases. Users can clone any of these use-case and edit them to imitate their specific use-case.

## Getting started

It's very easy to quickly start experimenting with Machmeter. Use on of the existing usecase templates to get started.

### Checkout and build Machmeter

Create a clone of the Github repository of Machmeter and create export your gCloud service account credentials as follows:

```bash
$ git clone https://github.com/cloudspannerecosystem/machmeter.git
$ cd machmeter/commons

# Building the maven project
$ mvn clean package -P assembly

# You provide the path to service accounts key.
$ export GOOGLE_APPLICATION_CREDENTIALS=~/service-accounts.json
```

### Execute an existing template

Machmeter exposes three CLI commands: `setup`, `execute` and `cleanup`. All of these commands follow similar syntax and can be executed as follows:

```bash
$ java -jar target/machmeter/machmeter.jar <command> <Path To Json Config>
```

## About the project

### Contributing
[CONTRIBUTING docs](./contributing.md)

## License

This is [Apache 2.0 License](../LICENSE)

{: .note }
This is not an officially supported Google product.

[Machmeter repo]: https://github.com/cloudspannerecosystem/machmeter


