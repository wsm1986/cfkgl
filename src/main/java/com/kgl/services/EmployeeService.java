package com.kgl.services;

import java.util.List;

import com.kgl.models.Employee;


public interface EmployeeService {
	
	public List<Employee> getAllEmployees();
	public Employee getEmployeeById(long id);
	
}
