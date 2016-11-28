package com.employeeinfoportal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employeeinfoportal.dao.EmployeeInfoDAO;
import com.employeeinfoportal.dto.Employee;


@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	EmployeeInfoDAO mongoDao;

	@Override
	public void updateEmployeeInfo(final Employee emp) {
		 mongoDao.updateEmployeeInfo(emp);
	}

	@Override
	public List<Employee> retrieveAllEmployeeInfo() {
		return mongoDao.retrieveAllEmployeeInfo();
	}

	@Override
	public void deleteSingleUser(final String id) {
		mongoDao.deleteSingleUser(id);		
	}

	@Override
	public void createNewEmployee(final Employee emp) {
		mongoDao.createNewEmployee(emp);		
	}
}
