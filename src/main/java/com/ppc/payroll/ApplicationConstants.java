package com.ppc.payroll;

/**
 * Class to hold all application specific constants
 */
public class ApplicationConstants {
	
	public static final int MINIMUM_NUM_FIELDS = 6;

	public static final String OUTPUT_REPORTS_LOCATION = "C:\\TempData\\";

	//Define report name and header of file here
	public static final String NUM_EMPLOYEE_REPORT_NAME = "NumEmployees.csv";
	public static final String[] NUM_EMPLOYEE_HEADER_ROW = new String[]{"Number of employees"};

	public static final String EMPLOYEE_PAYOUT_DETAILS_REPORT_NAME = "EmployeePayoutDetails.csv";
	public static final String[] EMPLOYEE_PAYOUT_DETAILS_HEADER_ROW = new String[]{"EmployeeId, Name, Surname, Total amount paid"};

	public static final String MONTHLY_SALARY_REPORT_NAME = "MonthlySalaryReport.csv";
	public static final String[] MONTHLY_SALARY_HEADER_ROW = new String[]{"Month", "Total salary", "Number of employees"};

	public static final String MONTHLY_PAYOUT_REPORT_NAME = "MonthlyPayoutReport.csv";
	public static final String[] MONTHLY_PAYOUT_HEADER_ROW = new String[]{"Month", "Total Amount (Salary + Bonus + REIMBURSEMENT)", "Number of employees"};

	public static String MONTHLY_ONBOARD_NUM_EMP_REPORT_NAME = "MonthlyOnboardedNumEmployee.csv";
	public static String[] MONTHLY_ONBOARD_NUM_EMP_HEADER_ROW = new String[]{"Month", "Number of employees onboarded"};

	public static String MONTHLY_ONBOARD_EMP_LIST_REPORT_NAME = "MonthlyOnboardedEmployeeList.csv";
	public static String[] MONTHLY_ONBOARD_EMP_LIST_HEADER_ROW = new String[]{"Month", "EmpId", "First Name", "Last Name"};

	public static String MONTHLY_EXIT_NUM_EMP_REPORT_NAME = "MonthlyExitedNumEmployee.csv";
	public static String[] MONTHLY_EXIT_NUM_EMP_HEADER_ROW = new String[]{"Month", "Number of employees exited"};

	public static String MONTHLY_EXIT_EMP_LIST_REPORT_NAME = "MonthlyExitEmployeeList.csv";
	public static String[] MONTHLY_EXIT_EMP_LIST_HEADER_ROW = new String[]{"Month", "EmpId"};

	public static String YEARLY_FINANCIAL_REPORT_NAME = "YearlyFinancialReport.csv";
	public static String[] YEARLY_FINANCIAL_HEADER_ROW = new String[]{"Year", "Event", "EmpId", "Event Date", "Event Value"};


}
