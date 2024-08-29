package com.ppc.payroll.validator;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RecordValidatorTest {


    @Test
    public void testValidRecords() {
        String[] validRow = new String[] {"1", "emp101", "Bill", "Gates", "SE", "ONBOARD", "1-11-2022", "10-11-2022", "Bill Gates is going to join soon"};
        assertTrue(RecordValidator.validateRecord(validRow));

        String[] validRowEmpEvent = new String[] {"3", "emp101", "BONUS", "1000", "10-10-2022", "Performance bonus of year 2022"};
        assertTrue(RecordValidator.validateRecord(validRowEmpEvent));
    }

    @Test
    public void testInvalidRecords() {
        String[] inValidRow = new String[] {"1", "emp101", "Bill", "Gates"};
        assertFalse(RecordValidator.validateRecord(inValidRow));
    }

    @Test
    public void testInvalidEvent() {
        String[] invalidEventRow = new String[] {"1", "emp101", "Bill", "Gates", "SE", "INVALID", "1-11-2022", "10-11-2022", "Bill Gates is going to join soon"};
        assertFalse(RecordValidator.validateRecord(invalidEventRow));
    }
}
