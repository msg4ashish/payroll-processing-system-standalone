package com.ppc.payroll.generator;

import com.opencsv.exceptions.CsvException;
import com.ppc.payroll.ApplicationTestConstants;
import com.ppc.payroll.processor.CSVFileRecordProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.util.List;


public class MonthlyPaymentReportDataGeneratorTest {

    private static final Logger logger = LogManager.getLogger(MonthlyPaymentReportDataGeneratorTest.class);
    private String filePath;

    @BeforeEach
    public void setUp() {
        filePath = ApplicationTestConstants.TEST_FILE;
    }


    @Test
    public void testGenerateMonthlySalaryReportData() throws CsvException, IOException {
        CSVFileRecordProcessor csvFileRecordProcessor = new CSVFileRecordProcessor();
        csvFileRecordProcessor.processCSVFile(filePath);
        MonthlyPaymentReportDataGenerator monthlyPaymentReportDataGenerator = new MonthlyPaymentReportDataGenerator();
        List<String[]> data =
                monthlyPaymentReportDataGenerator.generateMonthlySalaryReportData(csvFileRecordProcessor.getEmployeeEventRecordsList());

        //Add more assertions for more robust validation
        Assert.assertNotNull(data);
        Assert.assertEquals(3, data.size());
    }

    @Test
    public void testGenerateMonthlyPayoutReportData() throws CsvException, IOException {
        CSVFileRecordProcessor csvFileRecordProcessor = new CSVFileRecordProcessor();
        csvFileRecordProcessor.processCSVFile(filePath);
        MonthlyPaymentReportDataGenerator monthlyPaymentReportDataGenerator = new MonthlyPaymentReportDataGenerator();
        List<String[]> data =
                monthlyPaymentReportDataGenerator.generateMonthlyPayoutReportData(csvFileRecordProcessor.getEmployeeEventRecordsList());

        //Add more assertions for more robust validation
        Assert.assertNotNull(data);
        Assert.assertEquals(3, data.size());
    }
}
