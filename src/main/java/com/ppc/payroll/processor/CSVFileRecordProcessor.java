package com.ppc.payroll.processor;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import com.ppc.payroll.ApplicationConstants;
import com.ppc.payroll.dto.EmployeeEventRecord;
import com.ppc.payroll.dto.OnboardEmployeeRecord;
import com.ppc.payroll.generator.*;
import com.ppc.payroll.generator.writer.CSVFileWriter;
import com.ppc.payroll.mapper.PayrollRecordMapper;
import com.ppc.payroll.validator.RecordValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Responsible for processing CSV files. Extends BaseFileProcessor which has all common methods
 * Reads CSV file, validates each record, and maps it to the appropriate record class
 */
public class CSVFileRecordProcessor extends BaseFileProcessor implements ICSVFileProcessor {

    private static final Logger logger = LogManager.getLogger(CSVFileRecordProcessor.class);

    List<OnboardEmployeeRecord> onboardEmployeeRecordsList = new ArrayList<>();
    List<EmployeeEventRecord> employeeEventRecordsList = new ArrayList<>();


    /**
     * Loads CSV file from the given path, reads all lines and then processes each record one by one
     * @param filePath
     * @throws CsvException
     * @throws IOException
     */
    public void processCSVFile(String filePath) throws CsvException, IOException {
        //get fileReader object for further processing
        try (FileReader fileReader = readFile(filePath);
             CSVReader csvReader = new CSVReader(fileReader)) {
            //read all lines
            List<String[]> allLines = csvReader.readAll();

            //process each record one by one
            for(String[] line: allLines) {
                processCSVRecord(line);
            }

            //record number of rows present into file
            totalRecordsCount = allLines.size();

            generateReport();
        } catch (CsvException | IOException exception) {
            logger.error("Exception occurred while processing file at {}", filePath, exception);
            throw exception;
        }
    }

    /**
     * Processes single record from the file.
     * Validates the record and then maps it appropriate record object
     * @param row
     */
    private void processCSVRecord(String[] row) {
        final String[] trimmedRow = Arrays.stream(row).map(String::trim).toArray(String[]::new);
        //check if the record is valid or not
        if (RecordValidator.validateRecord(trimmedRow)) {
            //payroll file basically contains 2 types of records. First type if when an employee gets onboarded
            //(onboard employee record). Second type is when an event happens for an employee. (Employee event record)
            // An event could be bonus, exit, salary etc.
            //In the payroll file, structure of both these different record types is different.
            //For example, onboard employee record has 9 fields while employee event has 6 fields
            //So, we need to find out the record type to map it to appropriate DTO
            //logger.info("Processing line*******************:" + trimmedRow[0]);
            if (isOnboardEmployeeEvent(trimmedRow)) {
                OnboardEmployeeRecord onboardEmployeeRecord = PayrollRecordMapper.mapToAddEmployeeEventRecord(trimmedRow);

                onboardEmployeeRecordsList.add(onboardEmployeeRecord);
           } else {
                EmployeeEventRecord employeeEventRecord = PayrollRecordMapper.mapToUpdateEmployeeEvent(trimmedRow);
                employeeEventRecordsList.add(employeeEventRecord);
            }
        } else {
            //add invalid record to list and log the sequence number
            invalidRecords.add(row);
            logger.error("Invalid record at seq number: {}", row[0]);
        }
    }

    public List<OnboardEmployeeRecord> getOnboardEmployeeRecordsList() {
        return onboardEmployeeRecordsList;
    }

    public List<EmployeeEventRecord> getEmployeeEventRecordsList() {
        return employeeEventRecordsList;
    }

    /**
     * Checks what type of record it is.
     * Currently we are relying on number of fields to identify whether it is onboard employee record or
     * employee event record
     * @param row
     * @return true if its onboard employee record
     */
    private boolean isOnboardEmployeeEvent(String[] row) {
        //TODO see if this can be improved
        if (row.length > ApplicationConstants.MINIMUM_NUM_FIELDS) {
            return true;
        }
        return false;
    }

    /**
     *
     */
    //TODO see if this can be simplified and modularized
    private void generateReport() {
        CSVFileWriter csvFileWriter = new CSVFileWriter();

        MonthlyPaymentReportDataGenerator monthlyPaymentReportGenerator = new MonthlyPaymentReportDataGenerator();

        //generate monthly payment report
        //Monthly salary report in following format
        //a. Month, Total Salary, Total employees
        List<String[]> monthlySalaryReportData =
                            monthlyPaymentReportGenerator.generateMonthlySalaryReportData(this.employeeEventRecordsList);
        csvFileWriter.generateCSVReport(ApplicationConstants.MONTHLY_SALARY_REPORT_NAME,
                                        ApplicationConstants.MONTHLY_SALARY_HEADER_ROW, monthlySalaryReportData);

        //. Monthly amount released in following format
        //a. Month, Total Amount (Salary + Bonus + REIMBURSEMENT), Total employees
        List<String[]> monthlyPayoutReportData  =
                            monthlyPaymentReportGenerator.generateMonthlyPayoutReportData(this.employeeEventRecordsList);
        csvFileWriter.generateCSVReport(ApplicationConstants.MONTHLY_PAYOUT_REPORT_NAME,
                                        ApplicationConstants.MONTHLY_PAYOUT_HEADER_ROW, monthlyPayoutReportData);

        //1. Total number of employees in an organization.
        EmployeeReportDataGenerator employeeReportGenerator = new EmployeeReportDataGenerator();
        List<String[]> numTotalEmpData =
                            employeeReportGenerator.generateNumTotalEmployeesData(this.onboardEmployeeRecordsList,
                                                                                  this.employeeEventRecordsList);
        csvFileWriter.generateCSVReport(ApplicationConstants.NUM_EMPLOYEE_REPORT_NAME,
                                        ApplicationConstants.NUM_EMPLOYEE_HEADER_ROW, numTotalEmpData);

        //Employee wise financial report in the following format
        //a. Employee Id, Name, Surname, Total amount paid
        List<String[]> employeePayoutDetailsStr =
                employeeReportGenerator.generatePerEmployeeFinancialReportData(this.employeeEventRecordsList,
                                                                            this.onboardEmployeeRecordsList);

        csvFileWriter.generateCSVReport(ApplicationConstants.EMPLOYEE_PAYOUT_DETAILS_REPORT_NAME,
                                        ApplicationConstants.EMPLOYEE_PAYOUT_DETAILS_HEADER_ROW, employeePayoutDetailsStr);

        //Month wise following details
        //a. Total number of employees joined the organization with employee details like emp id,
          //      designation, name, surname.
        MonthlyReportDataGenerator monthlyReportGenerator = new MonthlyReportDataGenerator();
        List<String[]> monthlyOnbNumData =
                                monthlyReportGenerator.generateMonthlyOnboardingNumberReportData(this.onboardEmployeeRecordsList);
        csvFileWriter.generateCSVReport(ApplicationConstants.MONTHLY_ONBOARD_NUM_EMP_REPORT_NAME,
                                        ApplicationConstants.MONTHLY_ONBOARD_NUM_EMP_HEADER_ROW, monthlyOnbNumData);

        List<String[]> monthlyOnbDetailedData = monthlyReportGenerator
                                                .generateMonthlyOnboardingDetailedReportData(this.onboardEmployeeRecordsList);
        csvFileWriter.generateCSVReport(ApplicationConstants.MONTHLY_ONBOARD_EMP_LIST_REPORT_NAME,
                                        ApplicationConstants.MONTHLY_ONBOARD_EMP_LIST_HEADER_ROW, monthlyOnbDetailedData);

        //b. Total number of employees exit organization with employee details like name, surname.
        List<String[]> monthlyExitNumData = monthlyReportGenerator.generateMonthlyExitNumberReportData(this.employeeEventRecordsList);
        csvFileWriter.generateCSVReport(ApplicationConstants.MONTHLY_EXIT_NUM_EMP_REPORT_NAME,
                                        ApplicationConstants.MONTHLY_EXIT_NUM_EMP_HEADER_ROW, monthlyExitNumData);

        List<String[]> monthlyExitDetailedData = monthlyReportGenerator.generateMonthlyExitDetailedReportData(this.employeeEventRecordsList,
                                                                                                         this.onboardEmployeeRecordsList);
        csvFileWriter.generateCSVReport(ApplicationConstants.MONTHLY_EXIT_EMP_LIST_REPORT_NAME,
                                        ApplicationConstants.MONTHLY_EXIT_EMP_LIST_HEADER_ROW, monthlyExitDetailedData);

        //Yearly financial report in the following format
        //a. Event, Emp Id, Event Date, Event value
        YearlyReportDataGenerator yearlyReportDataGenerator = new YearlyReportDataGenerator();
        List<String[]> yearlyFinData = yearlyReportDataGenerator.generateYearlyFinancialReportData(this.employeeEventRecordsList);

        csvFileWriter.generateCSVReport(ApplicationConstants.YEARLY_FINANCIAL_REPORT_NAME,
                ApplicationConstants.YEARLY_FINANCIAL_HEADER_ROW, yearlyFinData);

    }
}
