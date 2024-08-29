package com.ppc.payroll.dto;

/**
 * Represents an employee event record. It holds info about a specific event of an employee.
 * Encapsulates the following information:
 * - sequenceNum`: Row num or a unique identifier of record.
 * - empId: Employee Id.
 * - event: Type of event.
 * - eventValue: The value of event.
 * - eventDate: The date of occurrence of event.
 * - notes: Any additional notes related to the event.
 */
public record EmployeeEventRecord(
        String sequenceNum,
        String empId,
        String event,
        String eventValue,
        String eventDate,
        String notes) {
}

