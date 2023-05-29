package com.employee;

import java.util.List;


public interface EmployeeService {

	String createEmployee(Employee emp);

	List<EmployeeTax> getEmployees();
    
}