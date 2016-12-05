package com.employeeinfoportal.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.employeeinfoportal.dto.Employee;
import com.employeeinfoportal.service.CustomerService;

@RestController
public class EmployeeInfoController {
	private static final Logger logger = Logger.getLogger(EmployeeInfoController.class);

	
	  @Autowired
	  CustomerService custService;  
	 
	    @RequestMapping(value = "/user/", method = RequestMethod.GET)
	    public ResponseEntity<List<Employee>> listAllUsers() {
	    	if(logger.isDebugEnabled()){
				logger.debug("listAllUsers is executed!");
			}
	        List<Employee> users = custService.retrieveAllEmployeeInfo();
	        if(users.isEmpty()){
	            return new ResponseEntity<List<Employee>>(HttpStatus.NO_CONTENT);
	        }
	        return new ResponseEntity<List<Employee>>(users, HttpStatus.OK);
	    }
	     
	    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	    public ResponseEntity<Employee> deleteUser(@PathVariable("id") String id) {
	        custService.deleteSingleUser(id);
	        return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
	    }
	 
	    @RequestMapping(value = "/user/", method = RequestMethod.POST)
	    public ResponseEntity<Void> createUser(@RequestBody Employee emp, UriComponentsBuilder ucBuilder) {
	        custService.createNewEmployee(emp);
	 
	        HttpHeaders headers = new HttpHeaders();
	        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(emp.get_id()).toUri());
	        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	    }
	     
	    @RequestMapping(value = "/user/", method = RequestMethod.PUT)
	    public ResponseEntity<Employee> updateUser(@RequestBody Employee emp) {
	        custService.updateEmployeeInfo(emp);
	        return new ResponseEntity<Employee>(emp, HttpStatus.OK);
	    }
}
