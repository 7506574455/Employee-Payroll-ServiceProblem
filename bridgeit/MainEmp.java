package com.bridgeit;

import java.util.ArrayList;

import java.util.List;

import com.bridgeit.model.services.EmpPayrollService;
import com.bridgeit.model.EmpPayroll;

public class MainEmp {

	public static void main(String[] args) {
	 
	 List<EmpPayroll> empPayrolls = new ArrayList<EmpPayroll>();
	 EmpPayrollService service = new EmpPayrollService(empPayrolls);
	 
	 service.readEmpPayrollData();
    }

}