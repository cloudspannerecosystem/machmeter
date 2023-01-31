---
layout: default
title: Monitoring
nav_order: 5
description: "Monitoring a POC."
---

# Setup
{: .no_toc }

Once the performance POC is in progress, use the following steps and resources to monitor the performance.

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

## Server Side

You can use the [Cloud Monitoring](https://cloud.google.com/monitoring/charts/metrics-selector) for server side monitoring.

## Client Side

Machmeters sets up a [Grafana](https://grafana.com/) dashboard for client side monitoring. This dashboard looks like the following:
-![](./grafana-dashboard.png)

### How to connect to grafana dashboard

```bash
# SSH port-forwording to grafana
$ kubectl port-forward -n spanner-test $(kubectl get po -n spanner-test | grep jmeter-grafana | awk '{print $1}') 3001:3000

# Grafana Dashboard
$ open http://localhost:3001
```
