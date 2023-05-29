package com.employee;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value={"/employee"})
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    /**
     * Create Employee
     * @param emp
     * @return
     */
    @PostMapping(value="/createEmployee")
    public ResponseEntity<String> createEmployee(@Valid @RequestBody  Employee emp){
        System.out.println("Creating Employee "+emp.toString());
        String status = employeeService.createEmployee(emp);
        return ResponseEntity.ok(status);
    }
    
    /**
     * Get Employee with Tax calculation
     * @return
     */
    @GetMapping(value = "/getEmployees")
    public ResponseEntity<Object> getEmployees() {
        List<EmployeeTax> emp = employeeService.getEmployees();
        if(!CollectionUtils.isEmpty(emp)) {
        	return ResponseEntity.ok(emp);
        }
        return ResponseEntity.ok("No Employees found");
    }

}
