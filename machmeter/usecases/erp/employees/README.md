### Erp Employteesi

Implements a erp employees database with containing these tables - `departments` , `dept_manager` , `employees` , `dept_emp`, `salaries` , `titles` 

`departments` table stores list of all departments. 
`dept_manager` table stores managers of a department and duration they served. 
`employees` table stores list of all employees.
`dept_emp` stores correlation of an employee with its department.
`salaries` table stores salaries of an employee for different durations.
`titles` table stores titles of an employee during different intervals of time.
`titles`,`salaries` and `dept_emp` are interleaved on parent employees.
This template demonstrates loading data into tables via JMeter. The database is sampled and a CSV data set is created for the performance test
template to use in its run.

For performance testing, we showcase read queries.

