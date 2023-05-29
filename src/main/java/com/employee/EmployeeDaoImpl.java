package com.employee;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDaoImpl implements EmployeeDao{
    @Autowired
    private SessionFactory sessionFactory;

	@Override
	public String createEmployee(Employee emp) {
		Session session = sessionFactory.getCurrentSession();
		session.save(emp);
      	return "Employee created successfully";
    }

	@Override
	public List<Employee> getEmployees() {
		Session session = sessionFactory.getCurrentSession();
		List<Employee> list= session.createCriteria(Employee.class).list();
		return list;
	}
   

}
