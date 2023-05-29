package com.employee;

import java.util.List;

public interface EmployeeDao {
	public String createEmployee(Employee emp);

	public List<Employee> getEmployees();
}