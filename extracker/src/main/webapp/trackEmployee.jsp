<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.sql.*" %>
<%@page import="java.util.*" %>
<%ResultSet resultset =null;%>
<html>
<head>
<style>

h1.sansserif {
    font-family: Arial, Helvetica, sans-serif;
    }
h2.sansserif {
    font-family: Arial, Helvetica, sans-serif;
    }
h3.sansserif {
    font-family: Arial, Helvetica, sans-serif;
    }
h4.sansserif {
    font-family: Arial, Helvetica, sans-serif;
}
</style>
<link href="<c:url value="/form.css" />" rel="stylesheet">
<link href="bootstrap.css" type="text/css" rel="stylesheet">
<h1 class="sansserif"><center>SVS Technologies</center></h1>
<h2 class="sansserif"><center>Expense tracker</center></h2>
</head>
<title>Track Employee Expense</title>
</head>
<body>
<div class="container">

    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h2>Welcome ${pageContext.request.userPrincipal.name} | <a onclick="document.forms['logoutForm'].submit()">Logout</a></h2>

    </c:if>

</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>

<h2>Track Employee Expense</h2>

<form action="/employeeExpenseSubmission" method="post">
<table>
		<tr><td>Expense Name :   		</td>  <td>       <input type="text" name="expenseName"/>    </td> </tr>
		<tr><td>Amount :  				</td>  <td>       <input type="text" name="amount" /> 		 </td> </tr>
		<tr><td>Date : 					</td>  <td>       <input type="date" name="createdDate" />   </td> </tr>
		<tr><td>Comment :    	    	</td>  <td>       <input type="text" name="comments" />      </td> </tr>
		
		<tr><td>Employee :   			<select name="employee">
 											 <c:forEach items="${employees}" var="databaseValue">
  												  <option value="${databaseValue}">
     													   ${databaseValue}
    											  </option>
 											 </c:forEach>
										</select>
		
		<tr><td>Category :   			<select name="category">
 											 <c:forEach items="${categories}" var="databaseValue">
  												  <option value="${databaseValue}">
     													   ${databaseValue}
    											  </option>
 											 </c:forEach>
										</select>
										
										
		
		<tr><td><input type="submit" value="Submit" /></td></tr>
	</table>
	</form>
	
	<form action="/deleteEmployee" method="post">
<tr><td><h3 class="sansserif">Delete Employee : </h3>	    	
										</td>  <td>      <select name="deleteEmployee">
 											 <c:forEach items="${employees}" var="databaseValue">
  												  <option value="${databaseValue}">
     													   ${databaseValue}
    											  </option>
 											 </c:forEach>
										</select>
										<input type="submit" value="Delete Employee" />     </td> </tr>
</form>
	
<a href="${address}"> Add New Employee </a>

<form action="/addCategories" method="post">
<tr><td><h3 class="sansserif">Add Category : </h3>   	    	</td>  <td>       <input type="text" name="addcategory" /> <input type="submit" value="Add Category" />     </td> </tr>
</form>

<form action="/deleteCategories" method="post">
<tr><td><h3 class="sansserif">Delete Category : </h3>	    	
										</td>  <td>      <select name="deleteCategory">
 											 <c:forEach items="${categories}" var="databaseValue">
  												  <option value="${databaseValue}">
     													   ${databaseValue}
    											  </option>
 											 </c:forEach>
										</select><input type="submit" value="Delete Category" />     </td> </tr>
</form>

<a href="${viewPage}"> Track Employee Expense </a></br></br>
<a href="${group}"> Add Expense To Group Of Employees </a>

</body>

</html>