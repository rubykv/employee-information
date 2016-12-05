package com.employeeinfoportal.util;

public class DAOException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public DAOException(){
		super();
	}
	
	public DAOException(String exception){
		super(exception);
	}

}
