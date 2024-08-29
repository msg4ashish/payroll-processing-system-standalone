package com.ppc.payroll.processor;

import com.opencsv.exceptions.CsvException;

import java.io.IOException;

public interface ICSVFileProcessor {
    public void processCSVFile(String filePath) throws IOException, CsvException;
}
