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

> Note: Machmeter is currently supported on Linux and MacOS platforms.

Run the following steps to start using Machmeter:

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
      "users": 1,
      "iterations": 1
    }
}
```

Run the following steps to start using Machmeter:

```bash
# Running machmeter execute to load sample data
$ java -jar target/machmeter/machmeter.jar execute profile-load.json 
```

Run the following steps to start using Machmeter:

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

Run the following steps to start using Machmeter:

```bash
# Running delete in Spanner Query Console to remove existing data 
DELETE FROM device_profile WHERE true ;
DELETE FROM transaction_limit WHERE true ;
DELETE FROM user_preference WHERE true ;
DELETE FROM user_profile WHERE true ;
DELETE FROM user_quick_actions WHERE true ;
DELETE FROM user_quick_actions_history WHERE true ;
```

## Documentation

Detailed documentation on how to use Machmeter is available at: https://cloudspannerecosystem.github.io/machmeter

## Contribution

- [CONTRIBUTING docs](./docs/contributing.md)

## License

This is [Apache 2.0 License](./LICENSE)

## Note
This is not an officially supported Google product.
