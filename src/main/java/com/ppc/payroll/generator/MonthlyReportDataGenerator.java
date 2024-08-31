package com.ppc.payroll.generator;

import com.ppc.payroll.ApplicationConstants;
import com.ppc.payroll.dto.EmployeeEventRecord;
import com.ppc.payroll.dto.OnboardEmployeeRecord;
import com.ppc.payroll.utils.ReportUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class responsible for generating monthly payment reports based on employee event records.
 */
public class MonthlyReportDataGenerator extends BaseReportDataGenerator {

    private static final Logger logger = LogManager.getLogger(MonthlyReportDataGenerator.class);


    /**
     * Generates a monthly report of the number of onboarding employees.
     * @param onboardEmployeeRecordsList
     * @return
     */
    public List<String[]> generateMonthlyOnboardingNumberReportData(List<OnboardEmployeeRecord> onboardEmployeeRecordsList) {
        // Update onboard employee records with extracted month
        List<OnboardEmployeeRecord> onboardEmployeeRecordsUpd = onboardEmployeeRecordsList.stream().map(x ->
                        new OnboardEmployeeRecord(x.sequenceNum(), x.empId(), x.firstName(), x.lastName(),
                                x.designation(), x.event(), ReportUtils.extractMonth(x.eventValue()), x.eventDate(), x.notes()))
                .collect(Collectors.toUnmodifiableList());

        // Group onboard employee records by month
        Map<String, List<OnboardEmployeeRecord>> monthWiseOnbEmployeeMap =
                                onboardEmployeeRecordsUpd.stream().collect(Collectors.groupingBy(OnboardEmployeeRecord::eventValue));

        // Prepare and log month-wise employee count
        List<String[]> onboardMonthWiseNumEmpStr = new ArrayList<>();
        logger.info(String.format("%s,%s", ApplicationConstants.MONTHLY_ONBOARD_NUM_EMP_HEADER_ROW[0],
                                           ApplicationConstants.MONTHLY_ONBOARD_NUM_EMP_HEADER_ROW[1]));

        monthWiseOnbEmployeeMap.forEach((month, onboardEmployeeRecordList) -> {
            logger.info(String.format("%s,%d", month, onboardEmployeeRecordList.size()));
            onboardMonthWiseNumEmpStr.add(new String[]{month, String.valueOf(onboardEmployeeRecordList.size())});
        });

        return onboardMonthWiseNumEmpStr;
    }

    /**
     * Generates a detailed monthly report of onboarding employees.
     * @param onboardEmployeeRecordsList
     * @return
     */
    public List<String[]> generateMonthlyOnboardingDetailedReportData(List<OnboardEmployeeRecord> onboardEmployeeRecordsList) {
        // Update employee records with extracted month
        List<OnboardEmployeeRecord> onboardEmployeeRecordsUpd = onboardEmployeeRecordsList.stream().map(x ->
                        new OnboardEmployeeRecord(x.sequenceNum(), x.empId(), x.firstName(), x.lastName(),
                                x.designation(), x.event(), ReportUtils.extractMonth(x.eventValue()), x.eventDate(), x.notes()))
                .collect(Collectors.toUnmodifiableList());

        // Group onboard employee records by month
        Map<String, List<OnboardEmployeeRecord>> monthWiseOnbEmployeeMap =
                onboardEmployeeRecordsUpd.stream().collect(Collectors.groupingBy(OnboardEmployeeRecord::eventValue));

        logger.info(String.format("%s,%s", ApplicationConstants.MONTHLY_ONBOARD_EMP_LIST_HEADER_ROW));

        List<String[]> empDetailsList = convertMapToList(monthWiseOnbEmployeeMap);
        empDetailsList.forEach(array -> {
            logger.info(String.join(",", array));
        });
        return empDetailsList;
    }

    /**
     * Generates a monthly report of the number of exited employees.
     * @param employeeEventRecordsList
     * @return
     */
    public List<String[]> generateMonthlyExitNumberReportData(List<EmployeeEventRecord> employeeEventRecordsList) {
        //filter exit event records
        List<EmployeeEventRecord> exitEvents = filterRecordsByEvent(employeeEventRecordsList, "EXIT");

        //update records list with updated event value.
        List<EmployeeEventRecord> exitEventsUpdated = updateEmpEventRecordListWithExtractedMonth(exitEvents);

        Map<String, List<EmployeeEventRecord>> monthWiseExitEventRecordsMap
                = exitEventsUpdated.stream().collect(Collectors.groupingBy(EmployeeEventRecord::eventDate));

        List<String[]> exitMonthWiseNumEmpStr = new ArrayList<>();
        logger.info(String.format("%s,%s", ApplicationConstants.MONTHLY_EXIT_NUM_EMP_HEADER_ROW[0],
                                           ApplicationConstants.MONTHLY_EXIT_NUM_EMP_HEADER_ROW[1]));

        monthWiseExitEventRecordsMap.forEach((month, exitEmployeeRecordList) -> {
            logger.info(String.format("%s,%d", month, exitEmployeeRecordList.size()));
            exitMonthWiseNumEmpStr.add(new String[]{month, String.valueOf(exitEmployeeRecordList.size())});
        });
        return exitMonthWiseNumEmpStr;
    }

    /**
     * Generates a detailed monthly report of exited employees.
     * @param employeeEventRecordsList
     * @param onboardEmployeeRecordsList
     * @return
     */
    public List<String[]> generateMonthlyExitDetailedReportData(List<EmployeeEventRecord> employeeEventRecordsList,
                                                                List<OnboardEmployeeRecord> onboardEmployeeRecordsList) {

        //TODO fix this. This report is not correct
        Map<String, List<OnboardEmployeeRecord>> employeeOnboardRecordMap =
                onboardEmployeeRecordsList.stream().collect(Collectors.groupingBy(OnboardEmployeeRecord::empId));

        //filter exit event records
        List<EmployeeEventRecord> exitEvents = filterRecordsByEvent(employeeEventRecordsList, "EXIT");

        //update records list with updated event value.
        List<EmployeeEventRecord> exitEventsUpdated = updateEmpEventRecordListWithExtractedMonth(exitEvents);

        Map<String, List<EmployeeEventRecord>> employeeWiseEventRecordsMap = exitEventsUpdated.stream().collect(Collectors.groupingBy(EmployeeEventRecord::empId));

        List<String[]> exitRecordsListStr = new ArrayList<>();
        logger.info("EmployeeId, Name, Surname, Date of exit");
        employeeWiseEventRecordsMap.forEach((employeeId, exitRecordsList) -> {
            String firstName = employeeOnboardRecordMap.get(employeeId).get(0).firstName();
            String lastName = employeeOnboardRecordMap.get(employeeId).get(0).lastName();
            logger.info(String.format("%s,%s,%s,%s", employeeId, firstName, lastName, exitRecordsList.get(0).eventValue()));
            exitRecordsListStr.add(new String[]{employeeId, firstName, lastName, exitRecordsList.get(0).eventValue()});
        });

        return exitRecordsListStr;
    }


    private List<String[]> convertMapToList(Map<String, List<OnboardEmployeeRecord>> empMapByMonth) {
        return empMapByMonth.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream()
                        .map(emp -> new String[]{
                                entry.getKey(),
                                emp.empId(),
                                emp.firstName(),
                                emp.lastName()
                        }))
                .collect(Collectors.toList());
    }

}
