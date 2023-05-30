package com.employee;

import java.util.List;

public interface EmployeeDao {
	public String createEmployee(Employee emp) throws Exception;

	public List<Employee> getEmployees() throws Exception;
}