package com.ppc.payroll.processor;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import com.ppc.payroll.ApplicationConstants;
import com.ppc.payroll.dto.EmployeeEventRecord;
import com.ppc.payroll.dto.OnboardEmployeeRecord;
import com.ppc.payroll.mapper.PayrollRecordMapper;
import com.ppc.payroll.validator.RecordValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Responsible for processing CSV files. Extends BaseFileProcessor which has all common methods
 * Reads CSV file, validates each record, and maps it to the appropriate record class
 */
public class CSVFileRecordProcessor extends BaseFileProcessor implements ICSVFileProcessor {

    private static final Logger logger = LogManager.getLogger(CSVFileRecordProcessor.class);

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
        //check if the record is valid or not
        if (RecordValidator.validateRecord(row)) {
            //payroll file basically contains 2 types of records. First type if when an employee gets onboarded
            //(onboard employee record). Second type is when an event happens for an employee. (Employee event record)
            // An event could be bonus, exit, salary etc.
            //In the payroll file, structure of both these different record types is different.
            //For example, onboard employee record has 9 fields while employee event has 6 fields
            //So, we need to find out the record type to map it to appropriate DTO
            if (isOnboardEmployeeEvent(row)) {
                OnboardEmployeeRecord onboardEmployeeRecord = PayrollRecordMapper.mapToAddEmployeeEventRecord(row);
                logger.info(onboardEmployeeRecord.toString());
            } else {
                EmployeeEventRecord employeeEventRecord = PayrollRecordMapper.mapToUpdateEmployeeEvent(row);
                logger.info(employeeEventRecord.toString());
            }
        } else {
            //add invalid record to list and log the sequence number
            invalidRecords.add(row);
            logger.error("Invalid record at seq number: {}", row[0]);
        }
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
}
