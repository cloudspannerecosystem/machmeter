# How to Ledger Experiment

## Pre-requisites 
How to the user gets authenticated for running:
1. JMeter
2. Terraform

## Setup Environment variables??

## Setup Cloud Spanner using Terraform
<----Steps to use infrastrucure/ folder---->

## Create database inside Cloud Spanner instance
e.g Specify the Cloud Spanner instance name used earlier in the JMeter script. 
<----Steps to use spanner-interactions/spanner-schema folder---->

## Load initial data into Spanner Database
<----Steps to use spanner-interactions/data-load folder---->

## Running the perf-test
<----Steps to use spanner-interactions/perf-test folder---->

## Monitoring the perf-test
<----Grafana monitoring (load-test specific metrics)---->
<----Pantheon monitoring (spanner-specific metrics)--->
