package com.ppc.payroll.dto;

/**
 * Represents an employee onboarding record with details about a new employee
 * Encapsulates the following information:
 *
 * - sequenceNum: Row num or a unique identifier of record.
 * - empId: Emp Id.
 * - firstName: First name of the employee.
 * - lastName: Last name of the employee.
 * - designation: Designation of the employee.
 * - event: Event name.
 * - eventValue: Event value.
 * - eventDate: The date of occurrence of event.
 * - notes: Any additional notes related to the event.
 *
 * This record is immutable and provides a structured way to capture details
 * about employee onboarding.
 */
public record OnboardEmployeeRecord (String sequenceNum,
        String empId,
        String firstName,
        String lastName,
        String designation,
        String event,
        String eventValue,
        String eventDate,
        String notes) { }
