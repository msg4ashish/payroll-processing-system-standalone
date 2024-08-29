package com.ppc.payroll;

import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ppc.payroll.processor.CSVFileRecordProcessor;

/**
 * Main class of the application. Pass payroll file location (full path with file name) as an argument
 * If no argument is supplied then, it uses file from a default location
 *
 */
public class PayrollFileProcessorApp {
    private static final Logger logger = LogManager.getLogger(PayrollFileProcessorApp.class);

    //default location of payroll file to process. This is used if no input is passed as an argument
    static String defaultPayrollFile = "C:\\Personal\\Projects\\PPCFile.csv";

    public static void main(String[] args) {
        //use the input provided, if no input is provided then use the default file
        String payrollFile = (args.length > 0 && args[0] != null) ? args[0] : defaultPayrollFile;
        processFile(payrollFile);
    }

    /**
     * Calls processor class to process file
     * @param payrollFile payroll file to process
     */
    private static void processFile(String payrollFile) {
        CSVFileRecordProcessor fileRecordProcessor = new CSVFileRecordProcessor();
        try {
            //process csv file
            fileRecordProcessor.processCSVFile(payrollFile);

            //get all invalid records
            List<String[]> invalidRecords = fileRecordProcessor.getInvalidRecords();

            //print info
            logger.info("Total number of records: {}", fileRecordProcessor.getTotalRecordsCount());
            logger.info("Number of valid records: {}", (fileRecordProcessor.getTotalRecordsCount() - invalidRecords.size()));
            logger.info("Number of invalid records: {}", invalidRecords.size());
        } catch (IOException e) {
            logger.error("IO Exception occurred while processing file at {}", payrollFile, e);
            throw new RuntimeException(e);
        } catch (CsvException e) {
            logger.error("CSV Exception occurred while processing file at {}", payrollFile, e);
            throw new RuntimeException(e);
        }
    }
}