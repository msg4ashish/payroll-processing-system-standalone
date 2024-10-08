# Payroll Processing standalone application

## Overview

Standalone java app to process payroll file. Takes
file location as input, parses and processes the file
and logs results in console/log file. Also generates below csv reports at path configured in ApplicationConstants file

1. Total number of employees in an organization.
2. Month wise following details
a. Total number of employees joined the organization with employee details like emp id,
designation, name, surname.
b. Total number of employees exit organization with employee details like name, surname.
3. Monthly salary report in following format
a. Month, Total Salary, Total employees
4. Employee wise financial report in the following format
a. Employee Id, Name, Surname, Total amount paid
5. Monthly amount released in following format
a. Month, Total Amount (Salary + Bonus + REIMBURSEMENT), Total employees
6. Yearly financial report in the following format
a. Event, Emp Id, Event Date, Event value

## Pre-requisite
- Java 17 or above
- Maven
- Java IDE (preferably Intellij), but eclipse or any other will also work

## How to build and run
Project can be build using any editor or of your choice.
- Import project into your IDE and do build.
- To run on Intellij: Click on Run -> Edit Configurations
Enter file location path in Program Argument.
If we don't supply any argument then default value will be used.

- An example file is present under main/src/test/resources/testfile,csv

## Running tests
From Intellij right click on project and click on "Run All Tests"

Alternately, the program can also be build and run from command line via maven commands like:
mvn clean compile, mvn clean test

## Output
Application generates required 9 reports (csv file) at the path configured in ApplicationConstants file.
public static final String OUTPUT_REPORTS_LOCATION = "C:\\TempData\\";

Please change this value if we want to generate report at any other location.

## Code coverage
Code coverage report can be viewed after running tests.
Code coverage plugin comes pre-installed with Intellij, so
once the tests are run, coverage report can be viewed.
To run tests with code coverage report:
Project > More Run/Debug > Run 'All Tests' with coverage

## V2 Enhancements
The application simply parses the supplied file (in argument), validates 
each record and logs it.
It also logs few stats such as number of invalid records and total number of records

Further enhancement in V2 version can be done to store these records in DB.




