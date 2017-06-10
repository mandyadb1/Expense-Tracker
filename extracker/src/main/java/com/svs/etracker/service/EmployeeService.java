package com.svs.etracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svs.etracker.model.Employee;
import com.svs.etracker.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public void addEmployee(Employee emp){
		employeeRepository.save(emp);
	}

	public List<String> getFullName(){
		List<String> employees = employeeRepository.getEmployeeNames();
		if(employees!=null){
			return employees;
		}
		return null;
	}

	public int getEmployeeId(String empName){
		return employeeRepository.getEmployeeId(empName);
	}

	public void delteEmployee(String empName){
		employeeRepository.delete(getEmployeeId(empName));
	}
}
