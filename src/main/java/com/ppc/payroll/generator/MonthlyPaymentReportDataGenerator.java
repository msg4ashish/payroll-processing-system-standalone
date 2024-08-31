package com.ppc.payroll.generator;

import com.ppc.payroll.ApplicationConstants;
import com.ppc.payroll.dto.EmployeeEventRecord;
import com.ppc.payroll.utils.ReportUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Generates monthly payment reports based on employee event records.
 */
public class MonthlyPaymentReportDataGenerator extends BaseReportDataGenerator {

    private static final Logger logger = LogManager.getLogger(MonthlyPaymentReportDataGenerator.class);

    /**
     * Generates a monthly salary report based on employee event records.
     * @param employeeEventRecordsList
     * @return
     */
    public List<String[]> generateMonthlySalaryReportData(List<EmployeeEventRecord> employeeEventRecordsList) {
        List<EmployeeEventRecord> salaryEvents = filterRecordsByEvent(employeeEventRecordsList, "SALARY");
        return generateMonthlyPaymentReportData(salaryEvents, ApplicationConstants.MONTHLY_SALARY_HEADER_ROW);
    }

    /**
     * Generates a monthly payout report based on employee event records.
     * @param employeeEventRecordsList
     * @return
     */
    public List<String[]> generateMonthlyPayoutReportData(List<EmployeeEventRecord> employeeEventRecordsList) {
        List<EmployeeEventRecord> allPaymentEvents =
                employeeEventRecordsList.stream().filter(x -> ReportUtils.isPaymentEvent(x.event())).collect(Collectors.toUnmodifiableList());
        return generateMonthlyPaymentReportData(allPaymentEvents,  ApplicationConstants.MONTHLY_PAYOUT_HEADER_ROW);
    }

    /**
     * Generates monthly payment report data
     * @param allPaymentEvents
     * @param header
     * @return
     */
    private List<String[]> generateMonthlyPaymentReportData(List<EmployeeEventRecord> allPaymentEvents,
                                                            String header[]) {
        List<EmployeeEventRecord> salaryEventsUpd = updateEmpEventRecordListWithExtractedMonth(allPaymentEvents);

        // Group employees by month and collect unique employee IDs per month
        Map<String, Set<String>> monthWiseNumEmployeeMap = salaryEventsUpd.stream()
                .collect(Collectors.groupingBy(
                        EmployeeEventRecord::eventDate,
                        Collectors.mapping(EmployeeEventRecord::empId, Collectors.toSet())
                ));

        Map<String, Integer> monthEmpCountMap = monthWiseNumEmployeeMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().size()
                ));

        //logger.info("Month empl map:" + monthEmpCountMap);
        // Calculate total salary for each month and prepare a map with key as month and value as total
        Map<String, Long> monthSalaryMap = salaryEventsUpd.stream()
                .collect(Collectors.toMap(
                        EmployeeEventRecord::eventDate,
                        record -> Long.parseLong(record.eventValue()),
                        Long::sum
                ));

        //logger.info("Monthly salary map:" + monthSalaryMap);
        List<String[]> monthlySalaryStr = new ArrayList<>();
        logger.info("Month" + "," + header + "," + "Number of employees");
        monthSalaryMap.forEach((month, salary) -> {
            Integer empCount = monthEmpCountMap.get(month);
            logger.info(String.format("%s,%d,%d", month, salary, empCount != null ? empCount : 0));
            monthlySalaryStr.add(new String[]{ month, salary.toString(), empCount != null ? empCount.toString() : String.valueOf(0)});
        });
        return monthlySalaryStr;
    }


}
