package com.svs.etracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.svs.etracker.model.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

	@Query("SELECT e.fullName FROM Employee e")
	public List<String> getEmployeeNames();

	@Query("SELECT e.empId FROM Employee e WHERE e.fullName = :empName")
	public int getEmployeeId(@Param("empName")String empName);
}
