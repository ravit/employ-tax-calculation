package com.employee;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * Service to handle Employee info and tax
 * @author Ravi
 *
 */

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeDao empDao;

    /**
     * Method to create Employee
     */
	@Override
	public String createEmployee(Employee emp) {
		return empDao.createEmployee(emp);
	}

	/**
	 * Method to retrieve Employee list with tax calculations
	 */
	@Override
	public List<EmployeeTax> getEmployees() {
		List<EmployeeTax> empTaxs = new ArrayList<>();
		List<Employee> empList = empDao.getEmployees();
		
		if(!CollectionUtils.isEmpty(empList)) {
			// Calculate each employee tax
			empList.forEach(emp -> {
				EmployeeTax et = getEmpTax(emp);
				empTaxs.add(et);
			});
		}
		return empTaxs;
	}

	private EmployeeTax getEmpTax(Employee emp) {
		EmployeeTax et = new EmployeeTax();
		et.setCode(emp.getId());
		et.setFirstName(emp.getFirstName());
		et.setLastName(emp.getLastName());
		long days = getDays(emp.getDateOfJoining());
		// Get yearly salary of employee
		if(days >=365) {
			et.setYearlySalary(emp.getSalary()*12);
		} else {
			double salPerDay = emp.getSalary()/30;
			et.setYearlySalary(salPerDay*days);
		}
		// Tax calculation
		et.setTax(calculateTax(et.getYearlySalary()));
		
		// Calculate Cess
		et.setCess(getCess(et.getYearlySalary()));
		
		return et;
	}

	/**
	 * Calculate Cess
	 * @param sal
	 * @return
	 */
	private double getCess(double sal) {
		if(sal > 2500000) {
			double diffAmount = sal - 2500000;
			// 2% Cess on income more than 2500000
			return diffAmount * 0.02;
		}
		return 0;
	}

	/**
	 * Find days of employment in a financial year
	 * @param dateOfJoining
	 * @return
	 */
	private long getDays(java.sql.Date dateOfJoining) {
		int CurrentYear = Calendar.getInstance().get(Calendar.YEAR);
		int CurrentMonth = (Calendar.getInstance().get(Calendar.MONTH)+1);
		String financiyalYearFrom="";
		String financiyalYearTo="";
		if (CurrentMonth<4) {
		    financiyalYearFrom=(CurrentYear-1)+"-04-01";
		    financiyalYearTo=(CurrentYear)+"-03-31";
		} else {
		    financiyalYearFrom=(CurrentYear)+"-04-01";
		    financiyalYearTo=(CurrentYear+1)+"-03-31";
		}
	    
	    SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
	    long diff = 0;

	    try {
	        Date date1 = myFormat.parse(financiyalYearFrom);
	        Date date2 = myFormat.parse(financiyalYearTo);
	        long diffOfJoin = date1.getTime() - dateOfJoining.getTime();
	      
	        if(diffOfJoin > 0) {
	        	diff = date2.getTime() - date1.getTime();
	        } else {
	        	diff = date2.getTime() - dateOfJoining.getTime();
	        }
	        
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
	
	/**
	 * Calculate tax by salary
	 * @param salary
	 * @return
	 */
	public static double calculateTax(double salary) {
        double taxAmount = 0;
        
        if (salary <= 250000) {
            // No tax for income up to 2,50,000
            taxAmount = 0;
        } else if (salary <= 500000) {
            // 5% tax on income between 2,50,001 to 5,00,000
            taxAmount = (salary - 250000) * 0.05;
        } else if (salary <= 1000000) {
            // 10% tax on income between 5,00,001 to 10,00,000
            taxAmount = (salary - 500000) * 0.10 + 250000 * 0.05;
        } else {
            // 20% tax on income above 10,00,000
            taxAmount = (salary - 1000000) * 0.20 + 500000 * 0.10 + 250000 * 0.05;
        }
        
        return taxAmount;
    }

   

}