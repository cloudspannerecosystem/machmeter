---
layout: default
title: Part I - Setup infrastructure using Machmeter
parent: Adding a new use-case
nav_order: 2
---

# Part I - Setup infrastructure using Machmeter
{: .no_toc }
We will create the infrastructure required to perform a load test. This includes setting up a Cloud Spanner instance,
creating a new database with the provided schema inside it and setting up a GKE cluster for load testing (this will
be used in Part III).

{: .note }
This guide uses informtion from  the following article: [JMeter Spanner Performance Test](https://cloud.google.com/community/tutorials/jmeter-spanner-performance-test)
If you are having trouble following this guide and to develop the JMeter template, please refer to the above link
for additional information.


## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

## Setting up a Fork
* Ensure that you are authenticated and have the right permissions configured before getting started.
  Follow [this](../requirements.md) guide to ensure that all permissions are in place.
* Create a [fork](https://docs.github.com/en/get-started/quickstart/fork-a-repo) of the Machmeter repository.
* [Clone](https://docs.github.com/en/get-started/quickstart/fork-a-repo#cloning-your-forked-repository) the project locally.

## Getting started with Machmeter

### Pre-Requisites
Machmeter uses several tools under the hood. Please ensure the following are installed where Machmeter will run:
- [JDK & JVM](https://openjdk.org/) (auther version >= 8)
- [Terraform Cli](https://developer.hashicorp.com/terraform/downloads) (auther version >= 1.3.5)
- [kubectl](https://kubernetes.io/docs/tasks/tools/) (auther version >= v1.25.4)
- [Maven](https://maven.apache.org/) (auther version >= 3.6.3)

### Installation

Create a clone of the Github repository of Machmeter and create export your gCloud service account credentials as follows:

```bash
$ git clone https://github.com/cloudspannerecosystem/machmeter.git
$ cd machmeter/machmeter

# Building the maven project
$ mvn clean package -P assembly

# You provide the path to service accounts key.
$ export GOOGLE_APPLICATION_CREDENTIALS=~/service-accounts.json
```

## Identify the template to modify

Machmeter has a number of starter templates are present in
the [usecases](https://github.com/cloudspannerecosystem/machmeter/tree/master/machmeter/usecases) directory.
Browse through the list of templates to identify a template closest to what you are looking for.

Each template folder contains a `README.md` file with the description of what the template does.
We will use this template as the basis to create our new template.

## Create a new folder and clone the contents

Create a new folder in the machmeter `usecases` directory, and clone the contents of your picked usecase into it:

```shell
cd machmeter/usecases
 mkdir -p erp/employees
 cp -r finance/ledger/* erp/employees/
 ls 
```

A new usecase folder hierarchy `erp/employees` is created in the `usecases` directory with the contents of the
`finance/ledger` folder. Verify that that it contains two-subfolders: `sample-configs` and `templates`.

## Modify the schema

```shell
cd erp/employees/sample-configs
ls 
```

The [schema](https://github.com/cloudspannerecosystem/machmeter/blob/master/machmeter/usecases/finance/ledger/templates/schema.sql)
file describes the database DDL. Machmeter reads this file to create the DDL on the Cloud Spanner instance you supply
during machmeter `setup` command execution.
For our `erp/employees` use-case, we will replace the existing file contents from `financial/leger` use-case with our
new schema.

Open the `schema.sql` file in the editor of your choice, and replace its contents with the following:

```sql
CREATE TABLE departments (
  dept_no STRING(4) NOT NULL,
  dept_name STRING(40) NOT NULL,
) PRIMARY KEY(dept_no);

CREATE UNIQUE INDEX dept_name ON departments(dept_name);

CREATE TABLE dept_manager (
  dept_no STRING(4) NOT NULL,
  emp_no INT64 NOT NULL,
  from_date DATE NOT NULL,
  to_date DATE NOT NULL,
) PRIMARY KEY(dept_no, emp_no),
  INTERLEAVE IN PARENT departments ON DELETE NO ACTION;

CREATE INDEX dept_no_17 ON dept_manager(dept_no);

CREATE INDEX emp_no_18 ON dept_manager(emp_no);

CREATE TABLE employees (
  emp_no INT64 NOT NULL,
  birth_date DATE NOT NULL,
  first_name STRING(14) NOT NULL,
  last_name STRING(16) NOT NULL,
  gender STRING(MAX) NOT NULL,
  hire_date DATE NOT NULL,
) PRIMARY KEY(emp_no);

CREATE TABLE dept_emp (
  emp_no INT64 NOT NULL,
  dept_no STRING(4) NOT NULL,
  from_date DATE NOT NULL,
  to_date DATE NOT NULL,
) PRIMARY KEY(emp_no, dept_no),
  INTERLEAVE IN PARENT employees ON DELETE NO ACTION;

CREATE INDEX dept_no ON dept_emp(dept_no);

CREATE INDEX emp_no_13 ON dept_emp(emp_no);

CREATE TABLE salaries (
  emp_no INT64 NOT NULL,
  salary INT64 NOT NULL,
  from_date DATE NOT NULL,
  to_date DATE NOT NULL,
) PRIMARY KEY(emp_no, from_date),
  INTERLEAVE IN PARENT employees ON DELETE NO ACTION;

CREATE INDEX emp_no ON salaries(emp_no);

CREATE TABLE titles (
  emp_no INT64 NOT NULL,
  title STRING(50) NOT NULL,
  from_date DATE NOT NULL,
  to_date DATE,
) PRIMARY KEY(emp_no, title, from_date),
  INTERLEAVE IN PARENT employees ON DELETE NO ACTION;

CREATE INDEX emp_no_6 ON titles(emp_no);
```

Save the file.

## Run the machmeter `setup` command

We can now execute the machmeter `setup` commmand to create a Cloud Spanner instance, create the above schema inside
and create a GKE cluster inside it.

Update the configuration in the appropriate values in the `sample-configs/setup.json` file and run the machmeter setup
command.

```bash
$ java -jar target/machmeter/machmeter.jar setup sample-configs/setup.json
```

## Conclusion

At the end of Part I, you should have all the infrastructure needed to start using Machmeter with your new template.
Next, we will discuss how to create and test a Jmeter template locally in [Part II](local-test.md)
