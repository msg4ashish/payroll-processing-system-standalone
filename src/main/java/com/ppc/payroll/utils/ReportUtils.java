package com.ppc.payroll.utils;

public class ReportUtils {

    //The assumption here is that the data will come in dd-mm-yyyy format
    public static String extractMonth(String fullDateStr) {
        return fullDateStr.split("-")[1];
    }

    //The assumption here is that the data will come in dd-mm-yyyy format
    public static String extractYear(String fullDateStr) {
        return fullDateStr.split("-")[2];
    }

    public static boolean isPaymentEvent(String event) {
        return (event.equals("SALARY") || event.equals("BONUS") || event.equals("REIMBURSEMENT"));
    }
}
