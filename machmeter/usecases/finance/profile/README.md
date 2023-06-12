# Machmeter
[![Java CI](https://github.com/cloudspannerecosystem/machmeter/actions/workflows/ci.yaml/badge.svg)](https://github.com/cloudspannerecosystem/machmeter/actions/workflows/ci.yaml)
[![pages-build-deployment](https://github.com/cloudspannerecosystem/machmeter/actions/workflows/pages/pages-build-deployment/badge.svg)](https://github.com/cloudspannerecosystem/machmeter/actions/workflows/pages/pages-build-deployment)

## Overview of Profile Schema
> Note: This Profile schema was orginally from the real customer and was modified to keep customer's confidentiality and to suit for use case experimentation .

Most of Web/Mobile application requires to gather customer-related information e.g. name, birth date, address, email etc. These customer-related information is gernally stored in relational database for easily read/update/insert/join operations. The database access charateristics are heavily focus on read (e.g. 80%-90%) than update/insert (e.g. 10%-20%). In addition, it may not requires strong consistency read after insert/update  

## Overview of Configurations 

> Note: 

| Config | Template | Description |
| --- | --- | --- |
| `profile-infra-config.json` | profile.sql | Configuration file for provisioning infra & spanner resources as well as populate schema into spanner database |
| `profile-load-data.json` | profile-load-data.jmx |Configuration file for loading sample data into spanner database based on the the schema and create CSV file into the container to be used during the performance test <br />**[Default]** Sampling & create 1000 records/rows in userId.csv and deviceId.csv. |
| `profile-init-csv.json` | profile-init-csv.jmx |  [Optinal] Configuration file for creating CSV file into the container without populating any new data into spanner  |
| `profile-perf.json` | profile-perf.jmx | Configuration file for running performance load test based on the use case (e.g. read/write ratio). It may require pre-populated CSV file in the containers prior running. <br />**[Default]** <br/>- Use Case - 80% Stale Read, 10% Insert + Stale Read, 10% Update <br/> - maxSamplingSize = 1000 (matching with sampling size). This is Maximum numbers of Jmeter Variables dynamically created & stored new keys from insert | 

## Deploying Environment

> Note: 

Machmeter provides a capabilities to easily deploy performance load test environment. There are 2 key components which needs to be addressed (1) Jmeter running on GKE - this is used for running custom load test based on Jmeter template (2) Spanner - this is used for running custom schema and operate database operations based on template. 


Run the following steps to start deploying environment:

```bash
# Review configuration for loading sample data
cat profile-infra-config.json 
{
    "infraConfig": {
      "spannerInstanceConfig": {
        "projectId" : "jaru-ktb-spanner-ext",
        "instanceId": "jaru-spanner-profile",
        "dbName": "jaru-profile-db",
        "configuration": "regional-asia-southeast1",
        "displayName": "jaru-spanner-profile",
        "processingUnits": 1000,
        "environment": "dev"
      },
      "gkeConfig": {
        "clusterName": "jaru-mm-jmeter",
        "namespace": "machmeter",
        "machine_type": "c2-standard-4",
        "region": "asia-southeast1",
        "node_locations": "asia-southeast1-a,asia-southeast1-b,asia-southeast1-c",
        "min_count": 1,
        "max_count": 5,
        "network": "jaru-mm-network",
        "subnetwork": "jaru-mm-subnet",
        "ipRangePodsName": "jaru-ip-range-pods",
        "ipRangeServicesName": "jaru-ip-range-services",
        "google_service_account": "machmeter-test-gsa",
        "kube_service_account": "machmeter-test-ksa",
      }
    },
    "ddlConfig" : {
      "spannerInstanceConfig" : {
        "instanceId" : "jaru-spanner-profile",
        "dbName" : "jaru-profile-db",
        "configuration": "regional-asia-southeast1",
        "projectId" : "jaru-ktb-spanner-ext"
      },
      "schemaFilePath" : "usecases/finance/profile/templates/profile-schema.sql"
    }
  }
```

```bash
# Running machmeter setup to deploy environment
java -jar target/machmeter/machmeter.jar setup profile-infra-config.json 
```

## Loading Sample Data into Profile

> Note: Before loading sample data, you may want to (1) know how many rows in each table so that when the new data will be sum of .

**[Optional#1]** Before loading, you may want to know how many rows existed so that you can settle the total rows with a new data. While loading, you may also want to know whether the template is working as expected, if it works then every time you run the count statement, the number is getting increased until completion:

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

**[Optional#2]** Prior loading, you may want to cleanup all rows in each table so that it would be easy to validate and count based on the new data only. Total new rows added should be aligned with the configurations:

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
cat profile-load.json 
{
    "namespace": "spanner-test",
    "jMeterTemplatePath" : "usecases/finance/profile/templates/profile-load-data.jmx",
    "jMeterParams" : {
      "project": "jaru-ktb-spanner-ext",
      "instance": "spanner-profile",
      "database": "profile-db",
      "connections": 1000,
      "grpc_channel": 10,
      "threads": 10,
      "iterations": 10
    }
}
```

```bash
# Running machmeter execute to load sample data and generate CSV stored in each container (while running, you may run [Option#1] to check whether there is any new rows added into tables )
java -jar target/machmeter/machmeter.jar execute profile-load-data.json 

# Validate whether there is CSV generated in container 
kubectl get pods -n spanner-test | grep slave | awk '{print $1}' | xargs -i kubectl -n spanner-test exec -i {} -- ls /data/

# The following CSVs must be generated
deviceId.csv
lost+found
userId.csv
deviceId.csv
lost+found
userId.csv
```

**[Optional#3]** Run the following steps to only generate CSV from existing spanner data. This will not create additional data/rows in the spanner:

```bash
# Running machmeter execute to ONLY generate CSV stored in each container
java -jar target/machmeter/machmeter.jar execute profile-init-csv.json 

# Validate whether there is CSV generated in container 
kubectl get pods -n spanner-test | grep slave | awk '{print $1}' | xargs -i kubectl -n spanner-test exec -i {} -- ls /data/

# The following CSVs must be generated
deviceId.csv
lost+found
userId.csv
deviceId.csv
lost+found
userId.csv
```

## Runnning Performance Load Test to Profile

> Note: You should run performance load test with less concurrent users first and gradually increase in order to avoid other issues e.g. template error, insufficent resources etc. The numbers of concurrent users could be controlled (1) number of jmeter slave replicas/workers (2) number of threads defined in JSON configuration files. For the very first data loading, you should warmup Spanner by running load test for 15-30 minutes. 

 The performance load test template is configured for Use Case - 80% Stale Read, 10% Insert + Stale Read, 10% Update. <br/>- The first 80% Stale Read is using data from pre-populated CSV files during the loading data step. <br/>- 10% Update will also use data from pre-populated CSV files. We use Transaction to perform update operations (instead of Mutation) for easily get a specific row based on a given userId/deviceId key prior update<br/>- 10% Insert + Stele Read, however, create news records for userId and deviceId and store them in the Jmeter Variables with maximum of 1000 records. Any new records beyond 1000 will be rotated into the existing records. The insert operations is using Mutation. Finally, it will perform stale read using the new records. This will ensure the dynamic read (than using static CSV records) throughout the test.

```bash
# Review configuration for loading sample data
cat profile-perf.json 
{
    "namespace": "spanner-test",
    "jMeterTemplatePath" : "usecases/finance/profile/templates/profile-perf.jmx",
    "jMeterParams" : {
      "project": "jaru-ktb-spanner-ext",
      "instance": "spanner-profile",
      "database": "profile-db",
      "startUp_Delay": 0,
      "rampUp_Time": 5,
      "duration": 300,
      "connections": 500,
      "threads": 2,
      "grpc_channel": 50
    }
}
```


```bash
# Running machmeter execute to performance load test
java -jar target/machmeter/machmeter.jar execute profile-perf.json 

```

```bash
# Validate whether there is any error in one of jmeter slave container 
kubectl get pods -n spanner-test | grep slave | awk '{print $1}' | xargs -i kubectl -n spanner-test exec -it {} -- tail -f jmeter-server.log
```

## Scaling Performance Load Test to Profile

> Note: If you increase Spanner Node, it is also recommended to warmup Spanner by performing load test for 15-30 minutes 

The goal of performance load test is to achieve the best results in terms of (1) QPS and (2) Latency. Given the same Spanner configurations, it may provide different performance results based on the use cases. The maximum QPS for 1 x Spanner node (or 1000PU) could achive maximum 10K read and 1K write. In order to scale performance load test, it could be managed by changing (1) number of threads defined in JSON configuration files (2) number of jmeter slave replicas/workers (3) number of Spanner nodes, until you satisfy with the results.


**[Number of Threads]** Threads add more concurent users for a give Jmeter Slave. These threads are running in parallel. However, the more threads, the more resources (CPU & Memory) are required per a given Jmeter Slave. Since the Jmeter is running in Java, consideration in memory requirement is cricical. Having more threads for complex & huge templates is challenges. It is recommended to slowly increase number of threads to see the maxium one.

```bash
# Number of 'Threads' in the template configurations
cat profile-perf.json 
{
    "namespace": "spanner-test",
    "jMeterTemplatePath" : "usecases/finance/profile/templates/profile-perf.jmx",
    "jMeterParams" : {
      "project": "jaru-ktb-spanner-ext",
      "instance": "spanner-profile",
      "database": "profile-db",
      "startUp_Delay": 0,
      "rampUp_Time": 5,
      "duration": 300,
      "connections": 500,
      "threads": 2,  <== This is number of threads per slave
      "grpc_channel": 50
    }
}
```

**[Number of Jmeter Slaves]** It is required to scale number of Jmeter Slaves (staefulsets replica) running in GKE to have more workers to perfom distributed load test for a given template. **Number of Jmeter Slaves will multiply with Number of Threds to form Total Concurrent Users**. Consequently, scaling more staefulsets replicas will require more GKE nodes. 
> Note: You can monitor the GKE & Jmeter Slave resources consumption via GKE Cluster/Workload Monitoring Console 

```bash
# Number of 'Jmeter Slaves' running in GKE
kubectl get statefulsets -n spanner-test
NAME            READY   AGE
jmeter-slaves   2/2     25h


# Scale number of 'Jmeter Slaves' 
kubectl -n spanner-test scale statefulsets jmeter-slaves --replicas=4

kubectl get statefulsets -n spanner-test
NAME            READY   AGE
jmeter-slaves   4/4     26h
```

**[Number of Spanner Node]** Once you cannot get more QPS by increasing (1) Number of Threads and (2) Number of Slaves, then it is time to increase the size of Spanner Node. This could be done by using gcloud command or GCP Spanner Console which will immediate update the size in seconds. 

> Note: You can monitor the Spanner resources consumption & Spanner metrics (e.g. Operations per Second, Latency ) via Spanner **System Insight** Monitoring Console

```bash
# Increase Spanner Node to 2 x Nodes (or 2000 PUs)
gcloud spanner instances update spanner-profile --nodes=2
Updating instance...done.

```

## Cleanup Environment

> Note: 

Machmeter provides a capabilities to easily cleanup performance load test environment and it reuses the same configuration as deployment  


Run the following steps to start cleanup/destroy environment:

```bash
# Running machmeter setup to deploy environment
java -jar target/machmeter/machmeter.jar cleanup profile-infra-config.json 
```

## Documentation

Detailed documentation on how to use Machmeter is available at: https://cloudspannerecosystem.github.io/machmeter

## Contribution

- [CONTRIBUTING docs](./docs/contributing.md)

## License

This is [Apache 2.0 License](./LICENSE)

## Note
This is not an officially supported Google product.
