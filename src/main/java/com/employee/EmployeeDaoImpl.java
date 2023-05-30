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

    /**
     * Create Employee in DB
     */
    @Override
    public String createEmployee(Employee emp) throws Exception {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.save(emp);
              return "Employee created successfully";
        } catch(Exception e) {
            throw new Exception("Unable to save Employee : "+e.getMessage());
        }
        
    }

    /**
     * Get Employees from DB
     */
    @Override
    public List<Employee> getEmployees() throws Exception {
        try {
            Session session = sessionFactory.getCurrentSession();
            List<Employee> list= session.createCriteria(Employee.class).list();
            return list;
        } catch(Exception e) {
            throw new Exception("Unable to get Employees from DB : "+e.getMessage());
        }
    }
   

}
