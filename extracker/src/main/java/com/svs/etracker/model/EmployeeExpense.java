package com.svs.etracker.model;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@XmlRootElement
@Cacheable(false)
public class EmployeeExpense {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int empExpenseId;


	private String expenseName;


	private double amount;


	private Date createdDate;


	private String comments;

	private int empId;

	private int categoryId;


	public EmployeeExpense() {

	}


	public EmployeeExpense(int expenseId, String expenseName, double amount, Date createdDate, String comments, int empId) {
		super();
		this.empId=empId;
		this.empExpenseId = expenseId;
		this.expenseName = expenseName;
		this.amount = amount;
		this.createdDate = createdDate;
		this.comments = comments;
	}


	public int getExpenseId() {
		return empExpenseId;
	}


	public void setExpenseId(int expenseId) {
		this.empExpenseId = expenseId;
	}


	public String getExpenseName() {
		return expenseName;
	}


	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}


	public int getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}


	public int getEmpId() {
		return empId;
	}


	public void setEmpId(int empId) {
		this.empId = empId;
	}







}