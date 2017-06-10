<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="java.util.*" %>
<%@page import="java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h3 class="sansserif">Update Expense</h3>
<form action="/UpdateExpenseSubmission/${expense.expenseId}" method="POST" enctype="multipart/form-data">
<table>
		<tr><td>Expense Name :   		</td>  <td>       <input type="text" name="expenseName" placeholder="${expense.expenseName}" />    </td> </tr>
		<tr><td>Amount :  				</td>  <td>       <input type="text" name="amount" placeholder="${expense.amount}" /> 		 </td> </tr>
		<tr><td>Date : 					</td>  <td>       <input type="date" name="createdDate" placeholder="${expense.createdDate}"/> Before Date:<fmt:formatDate value="${expense.createdDate}" pattern="MM/dd/yyyy" />   </td> </tr>
		<tr><td>Comment :    	    	</td>  <td>       <input type="text" name="comments" placeholder="${expense.comments}" />      </td> </tr>
		
		
		
		<tr><td>Category :   			<select name="category">
 											 <c:forEach items="${categories}" var="databaseValue">
  												  <option value="${databaseValue}" selected="${StringCategory}">
     													   ${databaseValue}
    											  </option>
 											 </c:forEach>
										</select>
										
		<tr><td>Upload Receipt(Optional) :  </td> <td>       <input type="file" name="image"/>    </td> </tr>
										
		
		<tr><td><input type="submit" value="Submit" /></td></tr>
	</table>
	</form>
</body>
</html>