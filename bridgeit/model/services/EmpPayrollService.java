package com.bridgeit.model.services;

import java.util.Scanner;

import java.util.List;

import com.bridgeit.model.EmpPayroll;

public class EmpPayrollService {
	
	private static List<EmpPayroll> empPayrolls;
	
	private final Scanner scanner = new Scanner(System.in);

	public EmpPayrollService(List<EmpPayroll> empPayRolls) {
		this.empPayrolls = empPayrolls;
	} 

	public void readEmpPayrollData() {
		
		System.out.println("Enter Employee Id");
		int id = scanner.nextInt();
		
		System.out.println("Enter Employee Name");
		String name = scanner.next();
		
		System.out.println("Enter Employee salary");
		double sal = scanner.nextDouble();
		
		EmpPayroll Payroll = new EmpPayroll(id, name, sal);
		empPayrolls.add(Payroll);
	}
	
	public static void writeEmpPayrollData() {
		
		System.out.println("Writing employee payroll data into console : "+ empPayrolls);
	}
	
}	