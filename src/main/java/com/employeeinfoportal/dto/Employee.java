package com.employeeinfoportal.dto;

public class Employee {

	private String _id;
	private String name;
	private String city;
	private String phoneNumber;
	
	public Employee(){}

	public Employee(String id, String name, String city, String phoneNum) {
		this._id=id;
		this.name=name;
		this.city=city;
		this.phoneNumber=phoneNum;
	}


	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
