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
 * This class generates yearly financial reports based on employee event records.
 */
public class YearlyReportDataGenerator extends BaseReportDataGenerator {

    private static final Logger logger = LogManager.getLogger(YearlyReportDataGenerator.class);

    /**
     * Generates yearly financial report data from a list of employee event records.
     * @param employeeEventRecordsList
     * @return
     */
    public List<String[]> generateYearlyFinancialReportData(List<EmployeeEventRecord> employeeEventRecordsList) {

        List<EmployeeEventRecord> allPaymentEvents =
                employeeEventRecordsList.stream().filter(x -> ReportUtils.isPaymentEvent(x.event())).collect(Collectors.toUnmodifiableList());

        List<EmployeeEventRecord> allPaymentEventsUpdated = updateEmpEventRecordListWithExtractedYear(allPaymentEvents);

        Map<String, List<EmployeeEventRecord>> yearlyFinDataMap = allPaymentEventsUpdated
                .stream().collect(Collectors.groupingBy(EmployeeEventRecord::eventDate));

        List<String[]> yearlyFinData = new ArrayList<>();
        logger.info(String.format("%s,%s,%s,%s,%s", ApplicationConstants.YEARLY_FINANCIAL_HEADER_ROW[0],
                                                 ApplicationConstants.YEARLY_FINANCIAL_HEADER_ROW[1],
                                                 ApplicationConstants.YEARLY_FINANCIAL_HEADER_ROW[2],
                                                 ApplicationConstants.YEARLY_FINANCIAL_HEADER_ROW[3],
                                                 ApplicationConstants.YEARLY_FINANCIAL_HEADER_ROW[4]));

        List<String[]> empYearlyDetailsList = convertMapToList(yearlyFinDataMap);
        empYearlyDetailsList.forEach(array -> {
            logger.info(String.join(",", array));
        });

        /*yearlyFinDataMap.forEach((year, yearlyData) -> {
            logger.info(String.format("%s,%s", year, yearlyData.toString()));
            yearlyFinData.add(new String[]{year, yearlyData.toString()});
        });*/
        return empYearlyDetailsList;
    }


    private List<String[]> convertMapToList(Map<String, List<EmployeeEventRecord>> empMapByYear) {
        return empMapByYear.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream()
                        .map(emp -> new String[]{
                                entry.getKey(),
                                emp.event(),
                                emp.empId(),
                                emp.eventDate(),
                                emp.eventValue()
                        }))
                .collect(Collectors.toList());
    }

}
