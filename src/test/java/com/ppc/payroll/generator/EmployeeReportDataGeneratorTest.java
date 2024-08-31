package com.ppc.payroll.generator;

import com.opencsv.exceptions.CsvException;
import com.ppc.payroll.ApplicationTestConstants;
import com.ppc.payroll.processor.CSVFileRecordProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;


public class EmployeeReportDataGeneratorTest {

    private static final Logger logger = LogManager.getLogger(EmployeeReportDataGeneratorTest.class);
    private String filePath = ApplicationTestConstants.TEST_FILE;

    @Test
    public void testGenerateNumTotalEmployeesData() {
        CSVFileRecordProcessor csvFileRecordProcessor = new CSVFileRecordProcessor();
        try {
            csvFileRecordProcessor.processCSVFile(filePath);
            EmployeeReportDataGenerator employeeReportDataGenerator = new EmployeeReportDataGenerator();
            List<String[]> numTotalEmpData =
                    employeeReportDataGenerator.generateNumTotalEmployeesData(
                            csvFileRecordProcessor.getOnboardEmployeeRecordsList(),
                            csvFileRecordProcessor.getEmployeeEventRecordsList());

            //Add more assertions for more robust validation
            Assert.assertNotNull(numTotalEmpData);
            Assert.assertEquals(1, numTotalEmpData.size());
        } catch (CsvException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    @Test
    public void testGenerateEmployeeWiseFinancialReportData() {
        CSVFileRecordProcessor csvFileRecordProcessor = new CSVFileRecordProcessor();
        try {
            csvFileRecordProcessor.processCSVFile(filePath);
            EmployeeReportDataGenerator employeeReportDataGenerator = new EmployeeReportDataGenerator();
            List<String[]> numEmpData =
                    employeeReportDataGenerator.generatePerEmployeeFinancialReportData(
                                                csvFileRecordProcessor.getEmployeeEventRecordsList(),
                                                csvFileRecordProcessor.getOnboardEmployeeRecordsList());

            //Add more assertions for more robust validation
            Assert.assertNotNull(numEmpData);
            Assert.assertEquals(3, numEmpData.size());
        } catch (CsvException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
