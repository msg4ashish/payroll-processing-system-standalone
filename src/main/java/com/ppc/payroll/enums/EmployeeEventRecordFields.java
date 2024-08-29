package com.ppc.payroll.enums;

/**
 * Each record that comes in payroll file has multiple fields
 * This enum holds the position number of each field in a row.
 * For example, sequence number comes at first position (0 index)
 * Hence, this acts as a one place configuration where the field position for employee event record is configured
 * In future, if there is any change in the field position or there is any addition of new field then we only
 * need to change this file
 */
public enum EmployeeEventRecordFields {

    SEQUENCE_NUMBER("0"),
    EMPLOYEE_ID("1"),
    EVENT ("2"),
    EVENT_VALUE ("3"),
    EVENT_DATE ("4"),
    NOTES ("5");

    public final String fieldPosition;

    EmployeeEventRecordFields(String position) {
        this.fieldPosition = position;
    }

    public int getFieldPosition() {
        return Integer.valueOf(this.fieldPosition);
    }

}
