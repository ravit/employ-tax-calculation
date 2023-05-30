package com.employee;

import java.util.List;


public interface EmployeeService {

	String createEmployee(Employee emp) throws Exception;

	List<EmployeeTax> getEmployees() throws Exception;
    
}