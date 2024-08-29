package com.ppc.payroll.processor;

import com.opencsv.exceptions.CsvException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CSVFileRecordProcessorTest {

    private static final Logger logger = LogManager.getLogger(CSVFileRecordProcessorTest.class);
    private String filePath = "C:\\Personal\\Projects\\PPCFile.csv";

    @Test
    public void testReadCSVFile() {
        CSVFileRecordProcessor csvFileRecordProcessor = new CSVFileRecordProcessor();
        try {
            csvFileRecordProcessor.processCSVFile(filePath);
            assertEquals(5, csvFileRecordProcessor.getTotalRecordsCount().intValue());
            assertEquals(1, csvFileRecordProcessor.getInvalidRecords().size());

        } catch (IOException e) {
            logger.error(e);
        } catch (CsvException e) {
            logger.error(e);
        }
    }

    @Test(expected = FileNotFoundException.class)
    public void testNonExistentFile() throws IOException, CsvException {
        String filePath = "C:\\Personal\\Projects\\Invalid.csv";
        CSVFileRecordProcessor csvFileRecordProcessor = new CSVFileRecordProcessor();
        csvFileRecordProcessor.processCSVFile(filePath);
    }
}
