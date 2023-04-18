---
layout: default
title: Troubleshooting Guide
nav_order: 7
description: "Common FAQs and issues"
---

# Troubleshooting Guide
{: .no_toc }

FAQ capturing the common issues encountered while running Machmeter.

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}


## I get "Caller is missing IAM permission spanner.sessions.create" error while running `setup` command. What should I do?

This happens when the service account used does not have the adequate permissions to call Spanner APIs in the GCP
project you are using. Ensure that:

* Service account has the correct set of permissions. It would be able to access Spanner APIs and GKE APIs.
* Ensure that the `service_account_json` value is correctly configured in the [gkeConfig](https://cloudspannerecosystem.dev/machmeter/setup.html#configuration-file-definition)

## I get `dial tcp [::1]:8080: connect: connection refused` error while trying to run `execute` command. What should I do?

This is because the GKE cluster's auth is not correctly setup. Run the following to resolve it.

Debian:
```shell
sudo apt-get install google-cloud-sdk-gke-gcloud-auth-plugin
```

Component manager:
```shell
gcloud components install gke-gcloud-auth-plugin
```

