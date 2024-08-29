package com.ppc.payroll.processor;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Base class for all file process related operations
 * This is the parent class and contains the common methods which are applicable for all child processors
 * For each type of files, a different processor class can be created (if needed).
 * Each child class will have implementation which will be specific to its own file type.
 */
public class BaseFileProcessor implements IBaseFileProcessor {

    //List to hold all invalid records
    public List<String[]> invalidRecords = new ArrayList<>();

    //stores total number of records in a given file
    public Integer totalRecordsCount = 0;

    /**
     * Loads file from the provided file path and returns FileReader object
     * @param filePath
     * @return
     * @throws FileNotFoundException
     */
    public FileReader readFile(String filePath) throws FileNotFoundException {
        FileReader fileReader = new FileReader(filePath);
        return fileReader;
    }

    /**
     *
     * @return List of all invalid records
     */
    public List<String[]> getInvalidRecords() {
        return this.invalidRecords;
    }

    public Integer getTotalRecordsCount() {
        return totalRecordsCount;
    }
}
