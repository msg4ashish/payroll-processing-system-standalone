package com.ppc.payroll.generator;

import com.ppc.payroll.dto.EmployeeEventRecord;
import com.ppc.payroll.utils.ReportUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BaseReportDataGenerator {

    /**
     * Filters a list of EmployeeEventRecord objects based on a specific event type.
     *
     * @param employeeEventRecordsList
     * @param event
     * @return a list of EmployeeEventRecord objects that match the provided event type.
     */
    List<EmployeeEventRecord> filterRecordsByEvent(List<EmployeeEventRecord> employeeEventRecordsList,
                                                   String event) {
        return employeeEventRecordsList.stream().filter(x -> x.event().equals(event)).collect(Collectors.toUnmodifiableList());
    }

    /**
     * Updates provided list of EmployeeEventRecord objects by extracting month from the event date field
     * and using it to replace the existing event date.
     *
     * @param employeeEventRecordsList
     * @return
     */
    List<EmployeeEventRecord> updateEmpEventRecordListWithExtractedMonth(List<EmployeeEventRecord> employeeEventRecordsList) {
        //Assumption here is that the date will always come in pre-defined format. May be it will be better to do a
        //validation also here to check for invalid date format
        //update records list with updated event value.
        return employeeEventRecordsList.stream()
                .map(x -> new EmployeeEventRecord(
                                x.sequenceNum(),
                                x.empId(),
                                x.event(),
                                x.eventValue(),
                                ReportUtils.extractMonth(x.eventDate()),
                                x.notes()))
                        .collect(Collectors.toUnmodifiableList());
    }

    /**
     * Updates provided list of EmployeeEventRecord objects by extracting month from the event date field
     * and using it to replace the existing event date
     *
     * @param
     * @return
     */
    List<EmployeeEventRecord> updateEmpEventRecordListWithExtractedYear(List<EmployeeEventRecord> employeeEventRecordsList) {
        //Assumption here is that the date will always come in pre-defined format. May be it will be better to do a
        //validation also here to check for invalid date format
        //update records list with updated event value.
        return employeeEventRecordsList.stream().map(x ->
                new EmployeeEventRecord(x.sequenceNum(), x.empId(), x.event(), x.eventValue(),
                        ReportUtils.extractYear(x.eventDate()), x.notes())).collect(Collectors.toUnmodifiableList());
    }
}
