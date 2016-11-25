package com.employeeinfoportal.dao;

import java.util.List;

import com.employeeinfoportal.dto.Employee;

public interface EmployeeInfoDAO {
	
	public boolean updateEmployeeInfo(final Employee emp);
	
	public List<Employee> retrieveAllEmployeeInfo();
	
	public void deleteSingleUser(final String id);
	
	public void createNewEmployee(final Employee emp);

}
