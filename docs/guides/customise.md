---
layout: default
title: Adding a New Usecase
parent: Guides
nav_order: 1
---

# Adding a new usecase
{: .no_toc }

Using the existing templates to add to a new usecase to Machmeter.

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}


## Customizing Use-case

Users can customize the sample use-cases to imitate the requirements. This 
requires cloning the use-case that is closest to their requirements and modifying
its components.

Machmeter currently provides three pre-built templates for you to get started:

- [Ledger](https://github.com/cloudspannerecosystem/machmeter/blob/master/machmeter/usecases/finance/ledger/)
- [Movies Database](https://github.com/cloudspannerecosystem/machmeter/tree/master/machmeter/usecases/entertainment/movies-database)
- [Ecommerce Cart](https://github.com/cloudspannerecosystem/machmeter/tree/master/machmeter/usecases/shopping/cart)


## Components of a Use-case

To demonstrate this, we will be using [Ledger](https://github.com/cloudspannerecosystem/machmeter/blob/master/machmeter/usecases/finance/ledger/) as a
sample use-case.

- [Schema](https://github.com/cloudspannerecosystem/machmeter/blob/master/machmeter/usecases/finance/ledger/templates/schema.sql):
This describes the database structure and should contain all your DDL commands.
This needs to be modified containing the DDL command of the respective application
to be tested.
- [Data load Jmeter Script](https://github.com/cloudspannerecosystem/machmeter/blob/master/machmeter/usecases/finance/ledger/templates/data-load.jmx):
This Jmeter script performs 2 tasks. One it inserts randomized data into the spanner database
and second it creates CSV files containing keys required in queries in perf execution.
  - For these, there are 2 thread groups. First thread group, `Insert Data` is responsible
  for inserting data into the database. This contains `JDBC Requests` which have
  `insert` statements for all the tables needed to be populated.
  - For any computed data within the tables, `update` statements post all the insert statements
  to be added.
  - Number of Rows to be added can be controlled by `users` and `iterations`. 
  `users` control the number of threads. `iterations` control the number of iterations
  within a single thread. So, the total rows added will be `users * iterations`.
  - Second thread group, `Create Sampled CSV Data` is responsible for querying all
  the keys required in the perf test and dumping them into CSV files.
- [Perf Test Jmeter Script](https://github.com/cloudspannerecosystem/machmeter/blob/master/machmeter/usecases/finance/ledger/templates/finance-ledger-perf.jmx):
This has the following components:
  - `create spanner client(setup Thread Group)`: Spanner connections are created in this group.
  - `Master Thread Group`: this group contains all the read/write queries for the test.
  - `Throughput Controller`: these controllers control the percentage of queries in total
  queries sent to the database.
  - `CSV Data Set Config`: these configs import the CSV files created in earlier scripts.
