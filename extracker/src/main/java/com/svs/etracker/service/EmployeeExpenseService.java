package com.svs.etracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svs.etracker.model.EmployeeExpense;
import com.svs.etracker.repository.EmployeeExpenseRepository;

@Service
public class EmployeeExpenseService {

	@Autowired
	private EmployeeExpenseRepository employeeExpenseRepository;

	public void saveEmployeeExpense(EmployeeExpense expense){
		employeeExpenseRepository.save(expense);
	}

	public int getEmployeeId(String fullName){
		return employeeExpenseRepository.getIdForEmployeeName(fullName);
	}

	public List<EmployeeExpense> getExpenses(int empId){
		return employeeExpenseRepository.getExpenses(empId);
	}

	public List<Object[]> getExpenseAndCategoryDetails(int empId){
		return employeeExpenseRepository.getExpenseAndCategoryDetails(empId);
	}

	public void deleteExpenses(int[] ids){
		for (int i : ids) {
			employeeExpenseRepository.delete(i);
		}
	}

	public void deteEmployeeExpense(String empName){
		int empId = getEmployeeId(empName);
		List<EmployeeExpense> expenses = employeeExpenseRepository.getListOfEmployeeExpenseForEmpId(empId);
		for (EmployeeExpense employeeExpense : expenses) {
			employeeExpenseRepository.delete(employeeExpense);
		}

	}
}
