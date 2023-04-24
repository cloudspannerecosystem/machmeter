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
git clone https://github.com/cloudspannerecosystem/machmeter.git
cd machmeter/machmeter

# Building the maven project
mvn clean package -P assembly

# Authenticate with gCloud
gcloud auth login

# Install the gcloud gke plugin
gcloud components install gke-gcloud-auth-plugin
```

## Documentation

Detailed documentation on how to use Machmeter is available at: https://cloudspannerecosystem.github.io/machmeter

## Contribution

- [CONTRIBUTING docs](./docs/contributing.md)

## License

This is [Apache 2.0 License](./LICENSE)

## Note
This is not an officially supported Google product.
