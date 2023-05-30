package com.employee;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;



@RestController
@RequestMapping(value={"/employee"})
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;
    Logger logger = LogManager.getLogger(EmployeeController.class);

    /**
     * Create Employee
     * @param emp
     * @return
     */
    @PostMapping(value="/createEmployee")
    public ResponseEntity<String> createEmployee(@Valid @RequestBody  Employee emp){
    	logger.info("Creating Employee "+emp.toString());
        try {
            if(emp.getSalary() > 0 && emp.getPhoneNumber() > 0) {
                String status = employeeService.createEmployee(emp);
                return ResponseEntity.ok(status);
            } else {
                throw new Exception("Salary and Phone number are mandatory fileds");
            }
        } catch(Exception e) {
            logger.error(e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Get Employee with Tax calculation
     * @return
     */
    @GetMapping(value = "/getEmployees")
    public ResponseEntity<Object> getEmployees() {
    	logger.info("Get Employees and tax details ");
        try {
             List<EmployeeTax> emp = employeeService.getEmployees();
                if(!CollectionUtils.isEmpty(emp)) {
                    return ResponseEntity.ok(emp);
                }
                return ResponseEntity.ok("No Employees found");
        } catch(Exception e) {
            logger.error(e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
