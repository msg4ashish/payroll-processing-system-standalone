package com.ppc.payroll.generator.writer;

import com.opencsv.CSVWriter;
import com.ppc.payroll.ApplicationConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVFileWriter {

    private static final Logger logger = LogManager.getLogger(CSVFileWriter.class);

    public void generateCSVReport(String fileName, String[] headerRow, List<String[]> reportData) {

        // default all fields are enclosed in double quotes
        // default separator is a comma
        try (CSVWriter writer = new CSVWriter(new FileWriter(ApplicationConstants.OUTPUT_REPORTS_LOCATION + fileName))) {
            writer.writeNext(headerRow);
            writer.writeAll(reportData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
