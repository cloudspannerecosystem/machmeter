# Machmeter
[![Java CI](https://github.com/cloudspannerecosystem/machmeter/actions/workflows/ci.yaml/badge.svg)](https://github.com/cloudspannerecosystem/machmeter/actions/workflows/ci.yaml)
[![pages-build-deployment](https://github.com/cloudspannerecosystem/machmeter/actions/workflows/pages/pages-build-deployment/badge.svg)](https://github.com/cloudspannerecosystem/machmeter/actions/workflows/pages/pages-build-deployment)

## Overview

Machmeter is an open source tool for performance benchmarking of Cloud Spanner.
This repository contains Machmeter code, usage instructions and a few example
use-cases. Users can clone any of these use-case and edit them to imitate their 
specific use-case.

## Installation

> Note: Machmeter is currently supported on Linux and MacOS platforms.

Run the following steps to start using Machmeter:

```bash
$ git clone https://github.com/cloudspannerecosystem/machmeter.git
$ cd machmeter/machmeter

# Building the maven project
$ mvn clean package -P assembly

# You provide the path to service accounts key.
$ export GOOGLE_APPLICATION_CREDENTIALS=~/service-accounts.json

# Install the gcloud gke plugin
gcloud components install gke-gcloud-auth-plugin
```

## Loading Sample Data into Profile

> Note: Before loading sample data, you may want to (1) know how many rows in each table so that when the new data will be sum of .

Before loading, you may want to know how many rows existed so that you can settle the total rows with a new data. While loading, you may also want to know whether the template is working as expected, if it works then every time you run the count statement, the number is getting increased until completion:

```bash
# Running query in Spanner Query Console to validate new data 
select
(select count(*) from device_profile) as device_profile,
(select count(*) from transaction_limit) as transaction_limit,
(select count(*) from user_profile) as user_profile,
(select count(*) from user_preference) as user_preference,
(select count(*) from user_quick_actions) as user_quick_actions,
(select count(*) from user_quick_actions_history) as user_quick_actions_history
```

Prior loading, you may want to cleanup all rows in each table so that it would be easy to validate and count based on the new data only. Total new rows added should be aligned with the configurations:

```bash
# Running delete in Spanner Query Console to remove existing data 
DELETE FROM device_profile WHERE true ;
DELETE FROM transaction_limit WHERE true ;
DELETE FROM user_preference WHERE true ;
DELETE FROM user_profile WHERE true ;
DELETE FROM user_quick_actions WHERE true ;
DELETE FROM user_quick_actions_history WHERE true ;
```

Run the following steps to start loading profile data:

```bash
# Review configuration for loading sample data
$ cat profile-load.json 

{
    "namespace": "spanner-test",
    "jMeterTemplatePath" : "usecases/finance/profile/templates/profile-load-data.jmx",
    "jMeterParams" : {
      "project": "jaru-ktb-spanner-ext",
      "instance": "spanner-profile",
      "database": "profile-db",
      "connections": 1000,
      "channels": 10,
      "users": 10,
      "iterations": 10
    }
}
```

```bash
# Running machmeter execute to load sample data
$ java -jar target/machmeter/machmeter.jar execute profile-load-data.json 
```

## Documentation

Detailed documentation on how to use Machmeter is available at: https://cloudspannerecosystem.github.io/machmeter

## Contribution

- [CONTRIBUTING docs](./docs/contributing.md)

## License

This is [Apache 2.0 License](./LICENSE)

## Note
This is not an officially supported Google product.
