package com.ppc.payroll.generator;

import com.ppc.payroll.dto.EmployeeEventRecord;
import com.ppc.payroll.dto.OnboardEmployeeRecord;
import com.ppc.payroll.utils.ReportUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class which generates report data for employees
 * Includes methods to generate the total number of employees and financial reports on employee wise basis.
 */
public class EmployeeReportDataGenerator extends BaseReportDataGenerator {

    private static final Logger logger = LogManager.getLogger(MonthlyPaymentReportDataGenerator.class);

    /**
     * Generates total number of unique employees in the organization.
     *
     * @param onboardEmployeeRecordsList
     * @return
     */
    public List<String[]> generateNumTotalEmployeesData(List<OnboardEmployeeRecord> onboardEmployeeRecordsList,
                                                        List<EmployeeEventRecord> employeeEventRecordList) {
        //get total employees
        // Combine distinct employee IDs from both lists and count unique employees
        long numEmployees = Stream.concat(
                                onboardEmployeeRecordsList.stream().map(OnboardEmployeeRecord::empId),
                                employeeEventRecordList.stream().map(EmployeeEventRecord::empId))
                            .distinct().count();

        List<String[]> numEmployeesList = new ArrayList<>();
        numEmployeesList.add(new String[]{String.valueOf(numEmployees)});
        return numEmployeesList;
    }

    /**
     * Generates a financial report with employee-wise payout details, includes salary, bonus, reimbursement
     *
     * Processes two lists:
     * 1. A list of employee event records, which contains various events including payment events.
     * 2. A list of onboarding employee records, which contains employee personal details.
     *
     * It filters the payment events, aggregates total payments for each employee, and combines this data
     * with employee personal details to generate a report.
     *
     * @param employeeEventRecordsList A list of employee event records containing various events including payments.
     * @param onboardEmployeeRecordsList A list of onboarding employee records containing personal details of employees.
     * @return A list of string arrays where each array represents an employee's ID, first name, last name, and total amount paid.
     */
    public List<String[]> generatePerEmployeeFinancialReportData(List<EmployeeEventRecord> employeeEventRecordsList,
                                                                 List<OnboardEmployeeRecord> onboardEmployeeRecordsList) {
        // Filter payment events and group by employee ID
        Map<String, Long> empTotalPaymentMap = employeeEventRecordsList.stream()
                .filter(record -> ReportUtils.isPaymentEvent(record.event()))
                .collect(Collectors.groupingBy(
                        EmployeeEventRecord::empId,
                        Collectors.summingLong(record -> Long.parseLong(record.eventValue()))
                ));

        //Group onboarding records by empId
        Map<String, List<OnboardEmployeeRecord>> employeeOnboardRecordMap =
                onboardEmployeeRecordsList.stream().collect(Collectors.groupingBy(OnboardEmployeeRecord::empId));

        logger.info("EmployeeId, Name, Surname, Total amount paid");
        List<String[]> employeePayoutDetailsStr = new ArrayList<>();

        //iterate over payment map and fill in emp details from employeeOnboardRecordMap
        empTotalPaymentMap.forEach((employeeId, totalAmount) -> {
            String firstName = employeeOnboardRecordMap.get(employeeId).get(0).firstName();
            String lastName = employeeOnboardRecordMap.get(employeeId).get(0).lastName();
            logger.info(String.format("%s,%s,%s,%d", employeeId, firstName, lastName, totalAmount));
            employeePayoutDetailsStr
                    .add(new String[]{employeeId, firstName, lastName, String.valueOf(totalAmount)});
        });
        return employeePayoutDetailsStr;
    }

}
