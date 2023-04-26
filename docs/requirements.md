---
layout: default
title: Getting Started
nav_order: 2
description: "Prerequisites and Setup."
---

# Setup
{: .no_toc }

Setting up Machmeter for execution.

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

## Pre-Requisites
Machmeter uses several tools under the hood. Please ensure the following are installed where Machmeter will run:
- [JDK & JVM](https://openjdk.org/) (auther version >= 8)
- [Terraform Cli](https://developer.hashicorp.com/terraform/downloads) (auther version >= 1.3.5)
- [kubectl](https://kubernetes.io/docs/tasks/tools/) (auther version >= v1.25.4)
- [Maven](https://maven.apache.org/) (auther version >= 3.6.3)

## Getting Started

It's very easy to quickly start experimenting with Machmeter. Use on of the existing usecase templates to get started.

### Installation

Create a clone of the Github repository of Machmeter as follows:

```bash
git clone https://github.com/cloudspannerecosystem/machmeter.git

# Install Dependencies
cd machmeter
./install.sh
cd machmeter

# Building the maven project

# You provide the path to service accounts key.
export GOOGLE_APPLICATION_CREDENTIALS=~/service-accounts.json

# Install the gcloud gke plugin
gcloud components install gke-gcloud-auth-plugin
```

### Execute an existing template

Machmeter exposes three CLI commands: `setup`, `execute` and `cleanup`. All of these commands follow similar syntax and can be executed as follows:

```bash
java -jar target/machmeter/machmeter.jar <command> <Path To Json Config>
```
Each Machmeter run consists of the following three phases:

1. `setup` - Create the necessary resources required to execute a POC.
2. `execute` - Execute the acutal performance POC test.
3. `cleanup` - Delete the created resources.

Click on each step to understand how to perform it.

## Setting up the Cloud Environment

We expect to set organization policy by default.

- [Google Cloud Project](https://cloud.google.com/resource-manager/docs/creating-managing-projects)
- [Google Account](https://cloud.google.com/iam/docs/overview?hl=ja#google_account) - It needs roles/owner permission on your project

We use Workload Identity for authentication. The **required resources will be
automatically created** in set up phase with the required permissions and bindings.

- [Understand Workload Identity](https://cloud.google.com/kubernetes-engine/docs/concepts/workload-identity)
- [Use Workload Identity (NOT to be done manually for machmeter)](https://cloud.google.com/kubernetes-engine/docs/how-to/workload-identity)
