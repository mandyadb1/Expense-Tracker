package com.svs.etracker.model;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;


@Entity
@XmlRootElement
@Cacheable(false)
public class Expense {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int expenseId;

	@NotEmpty(message="Expense name cannot be empty")
	private String expenseName;

	@Min(1)
	private double amount;


	@Past(message="Date cannot be in the future")
	private Date createdDate;

	@Size(min=3, max=200, message = "comment size should be between {min} and {max}")
	private String comments;



	private int categoryId;

	private int imageId;


	public Expense() {
		// TODO Auto-generated constructor stub
	}


	public Expense(int expenseId, String expenseName, double amount, Date createdDate, String comments) {
		super();
		this.expenseId = expenseId;
		this.expenseName = expenseName;
		this.amount = amount;
		this.createdDate = createdDate;
		this.comments = comments;
	}


	public int getExpenseId() {
		return expenseId;
	}


	public void setExpenseId(int expenseId) {
		this.expenseId = expenseId;
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












}