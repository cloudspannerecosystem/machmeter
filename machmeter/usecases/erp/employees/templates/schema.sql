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
