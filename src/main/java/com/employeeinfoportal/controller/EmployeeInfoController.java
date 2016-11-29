package com.employeeinfoportal.controller;

import java.util.List;

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
	
	  @Autowired
	  CustomerService custService;  //Service which will do all data retrieval/manipulation work
	 
	    
	    //-------------------Retrieve All Users--------------------------------------------------------
	     
	    @RequestMapping(value = "/user/", method = RequestMethod.GET)
	    public ResponseEntity<List<Employee>> listAllUsers() {
	        List<Employee> users = custService.retrieveAllEmployeeInfo();
	        if(users.isEmpty()){
	            return new ResponseEntity<List<Employee>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
	        }
	        return new ResponseEntity<List<Employee>>(users, HttpStatus.OK);
	    }
	 
	    //------------------- Delete a User --------------------------------------------------------
	     
	    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	    public ResponseEntity<Employee> deleteUser(@PathVariable("id") String id) {
	        System.out.println("Fetching & Deleting User with id " + id);
	        custService.deleteSingleUser(id);
	        return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
	    }
	 
	    
	    //-------------------Retrieve Single User--------------------------------------------------------
	     
	   /* @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<Employee> getUser(@PathVariable("id") long id) {
	        System.out.println("Fetching User with id " + id);
	        Employee user = custService.findById(id);
	        if (user == null) {
	            System.out.println("User with id " + id + " not found");
	            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
	        }
	        return new ResponseEntity<Employee>(user, HttpStatus.OK);
	    }*/
	 
	     
	     
	    //-------------------Create a User--------------------------------------------------------
	     
	    @RequestMapping(value = "/user/", method = RequestMethod.POST)
	    public ResponseEntity<Void> createUser(@RequestBody Employee emp,    UriComponentsBuilder ucBuilder) {
	        System.out.println("Creating User " + emp.getName());
	        custService.createNewEmployee(emp);
	 
	        HttpHeaders headers = new HttpHeaders();
	        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(emp.get_id()).toUri());
	        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	    }
	 
	    
	     
	    //------------------- Update a User --------------------------------------------------------
	     
	    @RequestMapping(value = "/user/", method = RequestMethod.PUT)
	    public ResponseEntity<Employee> updateUser(@RequestBody Employee emp) {
	        System.out.println("Updating User " + emp.get_id());
	         
	        custService.updateEmployeeInfo(emp);
	        return new ResponseEntity<Employee>(emp, HttpStatus.OK);
	    }
}
