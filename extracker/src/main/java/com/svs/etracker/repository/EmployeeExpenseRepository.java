package com.svs.etracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.svs.etracker.model.EmployeeExpense;

@Repository
public interface EmployeeExpenseRepository extends CrudRepository<EmployeeExpense, Integer> {

	@Query("select e.empId from Employee e where e.fullName = :fullName")
	public int getIdForEmployeeName(@Param("fullName") String fullName);

	@Query("FROM EmployeeExpense e WHERE e.empId = :empId")
	public List<EmployeeExpense> getExpenses(@Param("empId") int empId);

	@Query("SELECT e.expenseName, e.amount, e.createdDate, e.comments, e.empExpenseId, c.category FROM EmployeeExpense e, Category c WHERE c.categoryId = e.categoryId AND e.empId = :empId")
	public List<Object[]> getExpenseAndCategoryDetails(@Param("empId") int empId);

	@Query("FROM EmployeeExpense e WHERE e.empId = :empId")
	public List<EmployeeExpense> getListOfEmployeeExpenseForEmpId(@Param("empId")int empId);

}
