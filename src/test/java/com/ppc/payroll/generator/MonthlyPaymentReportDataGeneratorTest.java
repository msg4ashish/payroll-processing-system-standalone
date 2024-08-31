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


public class MonthlyPaymentReportDataGeneratorTest {

    private static final Logger logger = LogManager.getLogger(MonthlyPaymentReportDataGeneratorTest.class);
    private String filePath = ApplicationTestConstants.TEST_FILE;

    @Test
    public void testGenerateMonthlySalaryReportData() {
        CSVFileRecordProcessor csvFileRecordProcessor = new CSVFileRecordProcessor();
        try {
            csvFileRecordProcessor.processCSVFile(filePath);
            MonthlyPaymentReportDataGenerator monthlyPaymentReportDataGenerator = new MonthlyPaymentReportDataGenerator();
            List<String[]> data =
                    monthlyPaymentReportDataGenerator.generateMonthlySalaryReportData(csvFileRecordProcessor.getEmployeeEventRecordsList());

            //Add more assertions for more robust validation
            Assert.assertNotNull(data);
            Assert.assertEquals(3, data.size());
        } catch (CsvException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    @Test
    public void testGenerateMonthlyPayoutReportData() {
        CSVFileRecordProcessor csvFileRecordProcessor = new CSVFileRecordProcessor();
        try {
            csvFileRecordProcessor.processCSVFile(filePath);
            MonthlyPaymentReportDataGenerator monthlyPaymentReportDataGenerator = new MonthlyPaymentReportDataGenerator();
            List<String[]> data =
                    monthlyPaymentReportDataGenerator.generateMonthlyPayoutReportData(csvFileRecordProcessor.getEmployeeEventRecordsList());

            //Add more assertions for more robust validation
            Assert.assertNotNull(data);
            Assert.assertEquals(3, data.size());
        } catch (CsvException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
