package com.ppc.payroll.enums;

/**
 * Defines all possible valid events that can come in payroll file
 * If there is a new event that comes in future, then add it in this enum
 */
public enum EventType {
    ONBOARD,
    SALARY,
    BONUS,
    EXIT,
    REIMBURSEMENT
}
