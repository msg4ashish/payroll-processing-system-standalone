package com.ppc.payroll.validator;

import com.ppc.payroll.ApplicationConstants;
import com.ppc.payroll.enums.OnboardEmployeeRecordFields;
import com.ppc.payroll.enums.EventType;
import com.ppc.payroll.enums.EmployeeEventRecordFields;

import org.apache.commons.lang3.EnumUtils;

/**
 * Validator class to validate records based on their content.
 * Checks if the record is valid by checking it against some pre-defined rules.
 * Modify this class if any new rule gets added or there is any change in existing rule.
 * */
public class RecordValidator {

    /**
     * Validates a record based on its length and then on event type.
     * @param record Array of strings representing the record to be validated.
     * @return true if the record is valid
     */
	public static boolean validateRecord(String[] record) {
        if(record == null) {
            return false;
        }
        //both types of records have at least a defined minimum number of fields
        //"Event" field comes at different position in the 2 record types, so the logic (field position)
        // to extract that is slightly different
        if(record.length > ApplicationConstants.MINIMUM_NUM_FIELDS) {
            return isValidEventType(record[OnboardEmployeeRecordFields.EVENT.getFieldPosition()]);
        }
        else if (record.length == ApplicationConstants.MINIMUM_NUM_FIELDS) {
            return isValidEventType(record[EmployeeEventRecordFields.EVENT.getFieldPosition()]);
        }
        else {
            //invalid if record.length is < MINIMUM_NUM_FIELDS
            return false;
        }
    }

    /**
     * Checks if the provided event type is valid by comparing it against the EventType enum.
     * This enum is of predefined valid event types
     *
     * @param eventType The event type to be validated.
     * @return true if its valid event type.
     */
    private static boolean isValidEventType(String eventType) {
        return EnumUtils.isValidEnum(EventType.class, eventType.trim());
    }
	
}
