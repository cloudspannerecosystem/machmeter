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

Create a clone of the Github repository of Machmeter and create export your gCloud service account credentials as follows:

```bash
$ git clone https://github.com/cloudspannerecosystem/machmeter.git

# Install Dependencies
$ cd machmeter
$ ./install.sh
$ cd machmeter

# Building the maven project
$ mvn clean package -P assembly

# You provide the path to service accounts key.
$ export GOOGLE_APPLICATION_CREDENTIALS=~/service-accounts.json

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

{: .warning }
Currently, we only support Service Account for authentication.

- [Service Account](https://cloud.google.com/iam/docs/creating-managing-service-accounts#creating) - It needs roles/owner permission on your project
- [Service Account Key file](https://cloud.google.com/iam/docs/creating-managing-service-account-keys#creating)

```bash
# Example to create account and keyfile
export SA_NAME=terraformer
export PROJECT_ID=test
# Create Service Account
gcloud iam service-accounts create $SA_NAME \
    --description="Operation service account for spanner stress demo" \
    --display-name=$SA_NAME
gcloud projects add-iam-policy-binding $PROJECT_ID \
    --member="serviceAccount:${SA_NAME}@${PROJECT_ID}.iam.gserviceaccount.com" \
    --role="roles/owner"
# Download a key file
export KEY_FILE=terraformer.json
gcloud iam service-accounts keys create $KEY_FILE \
    --iam-account=${SA_NAME}@${PROJECT_ID}.iam.gserviceaccount.com
# We recommend key file type is JSON
```

