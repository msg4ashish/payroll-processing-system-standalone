package com.ppc.payroll.processor;

import com.opencsv.exceptions.CsvException;
import com.ppc.payroll.ApplicationConstants;
import com.ppc.payroll.ApplicationTestConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CSVFileRecordProcessorTest {

    private static final Logger logger = LogManager.getLogger(CSVFileRecordProcessorTest.class);
    private String filePath;

    @BeforeEach
    public void setUp() {
        filePath = ApplicationTestConstants.TEST_FILE;
    }

    @Test
    public void testReadCSVFile() throws IOException, CsvException {
        CSVFileRecordProcessor csvFileRecordProcessor = new CSVFileRecordProcessor();
        csvFileRecordProcessor.processCSVFile(filePath);
        assertEquals(12, csvFileRecordProcessor.getTotalRecordsCount().intValue());
        assertEquals(0, csvFileRecordProcessor.getInvalidRecords().size());
    }


    @Test
    public void testNonExistentFile() {
        CSVFileRecordProcessor csvFileRecordProcessor = new CSVFileRecordProcessor();
        assertThrows(FileNotFoundException.class, () -> {
            csvFileRecordProcessor.processCSVFile("invalid/file.csv");
        });
    }
}
