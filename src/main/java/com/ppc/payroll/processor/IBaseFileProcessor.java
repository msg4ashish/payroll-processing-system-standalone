package com.ppc.payroll.processor;

import java.io.FileNotFoundException;
import java.io.FileReader;

public interface IBaseFileProcessor {
    public FileReader readFile(String filePath) throws FileNotFoundException;
}
