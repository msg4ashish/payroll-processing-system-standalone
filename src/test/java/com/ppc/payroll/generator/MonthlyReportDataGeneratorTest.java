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


public class MonthlyReportDataGeneratorTest {

    private static final Logger logger = LogManager.getLogger(MonthlyReportDataGeneratorTest.class);
    private String filePath = ApplicationTestConstants.TEST_FILE;

    @Test
    public void testGenerateMonthlyOnboardingNumberReportData() {
        CSVFileRecordProcessor csvFileRecordProcessor = new CSVFileRecordProcessor();
        try {
            csvFileRecordProcessor.processCSVFile(filePath);
            MonthlyReportDataGenerator monthlyReportDataGenerator = new MonthlyReportDataGenerator();
            List<String[]> data =
                    monthlyReportDataGenerator.generateMonthlyOnboardingNumberReportData(csvFileRecordProcessor.getOnboardEmployeeRecordsList());

            //Add more assertions for more robust validation
            Assert.assertNotNull(data);
            Assert.assertEquals(2, data.size());
        } catch (CsvException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    @Test
    public void testGenerateMonthlyOnboardingDetailedReportData() {
        CSVFileRecordProcessor csvFileRecordProcessor = new CSVFileRecordProcessor();
        try {
            csvFileRecordProcessor.processCSVFile(filePath);
            MonthlyReportDataGenerator monthlyReportDataGenerator = new MonthlyReportDataGenerator();
            List<String[]> data =
                    monthlyReportDataGenerator.generateMonthlyOnboardingDetailedReportData(csvFileRecordProcessor.getOnboardEmployeeRecordsList());

            //Add more assertions for more robust validation
            Assert.assertNotNull(data);
            Assert.assertEquals(4, data.size());
        } catch (CsvException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    @Test
    public void testGenerateMonthlyExitNumberReportData() {
        CSVFileRecordProcessor csvFileRecordProcessor = new CSVFileRecordProcessor();
        try {
            csvFileRecordProcessor.processCSVFile(filePath);
            MonthlyReportDataGenerator monthlyReportDataGenerator = new MonthlyReportDataGenerator();
            List<String[]> data =
                    monthlyReportDataGenerator.generateMonthlyExitNumberReportData(csvFileRecordProcessor.getEmployeeEventRecordsList());

            //Add more assertions for more robust validation
            Assert.assertNotNull(data);
            Assert.assertEquals(1, data.size());
        } catch (CsvException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    @Test
    public void testGenerateMonthlyExitDetailedReportData() {
        CSVFileRecordProcessor csvFileRecordProcessor = new CSVFileRecordProcessor();
        try {
            csvFileRecordProcessor.processCSVFile(filePath);
            MonthlyReportDataGenerator monthlyReportDataGenerator = new MonthlyReportDataGenerator();
            List<String[]> data =
                    monthlyReportDataGenerator.
                                generateMonthlyExitDetailedReportData(csvFileRecordProcessor.getEmployeeEventRecordsList(),
                                csvFileRecordProcessor.getOnboardEmployeeRecordsList());

            //Add more assertions for more robust validation
            Assert.assertNotNull(data);
            Assert.assertEquals(1, data.size());
        } catch (CsvException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }
    }

}
