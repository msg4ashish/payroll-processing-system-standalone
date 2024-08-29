package com.ppc.payroll.mapper;

import com.ppc.payroll.dto.EmployeeEventRecord;
import com.ppc.payroll.dto.OnboardEmployeeRecord;
import com.ppc.payroll.enums.*;

/**
 * Mapper file to map array record to DTO object
 * Uses the field configuration defined in Enums for mapping
 */
public class PayrollRecordMapper {

    /**
     * Mapper method to map array record to OnboardEmployeeRecord DTO object
     * Uses the field configuration defined in OnboardEmployeeRecordFields Enum for mapping
     * @param row
     * @return
     */
	public static OnboardEmployeeRecord mapToAddEmployeeEventRecord(String[] row) {
        return new OnboardEmployeeRecord(
                row[OnboardEmployeeRecordFields.SEQUENCE_NUMBER.getFieldPosition()],
                row[OnboardEmployeeRecordFields.EMPLOYEE_ID.getFieldPosition()],
                row[OnboardEmployeeRecordFields.FIRST_NAME.getFieldPosition()],
                row[OnboardEmployeeRecordFields.LAST_NAME.getFieldPosition()],
                row[OnboardEmployeeRecordFields.DESIGNATION.getFieldPosition()],
                row[OnboardEmployeeRecordFields.EVENT.getFieldPosition()],
                row[OnboardEmployeeRecordFields.EVENT_VALUE.getFieldPosition()],
                row[OnboardEmployeeRecordFields.EVENT_DATE.getFieldPosition()],
                row[OnboardEmployeeRecordFields.NOTES.getFieldPosition()]
        );
    }

    /**
     * Mapper method to map array record to EmployeeEventRecord DTO object
     * Uses the field configuration defined in EmployeeEventRecordFields Enum for mapping
     * @param row
     * @return
     */
    public static EmployeeEventRecord mapToUpdateEmployeeEvent(String[] row) {
        return new EmployeeEventRecord(
                row[EmployeeEventRecordFields.SEQUENCE_NUMBER.getFieldPosition()],
                row[EmployeeEventRecordFields.EMPLOYEE_ID.getFieldPosition()],
                row[EmployeeEventRecordFields.EVENT.getFieldPosition()],
                row[EmployeeEventRecordFields.EVENT_VALUE.getFieldPosition()],
                row[EmployeeEventRecordFields.EVENT_DATE.getFieldPosition()],
                row[EmployeeEventRecordFields.NOTES.getFieldPosition()]
        );
    }

}
