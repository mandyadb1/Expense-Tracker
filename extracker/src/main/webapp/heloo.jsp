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
<body>
<form:errors path="expense.*" />
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

 
<h3 class="sansserif">Add New Expense</h3>
<form action="/expenseSubmission" method="post" enctype="multipart/form-data">
<table>
		<tr><td>Expense Name :   		</td>  <td>       <input type="text" name="expenseName"/>    </td> </tr>
		<tr><td>Amount :  				</td>  <td>       <input type="text" name="amount" /> 		 </td> </tr>
		<tr><td>Date : 					</td>  <td>       <input type="date" name="createdDate" />   </td> </tr>
		<tr><td>Comment :    	    	</td>  <td>       <input type="text" name="comments" />      </td> </tr>
		
		
		
		<tr><td>Category :   			<select name="category">
 											 <c:forEach items="${categories}" var="databaseValue">
  												  <option value="${databaseValue}">
     													   ${databaseValue}
    											  </option>
 											 </c:forEach>
										</select>
										
		<tr><td>Upload Receipt(Optional) :  </td> <td>       <input type="file" name="image"/>    </td> </tr>
										
		
		<tr><td><input type="submit" value="Submit" /></td></tr>
	</table>
	</form>
	
	

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

<a href="${address}"> TRACK EXPENSE </a></br></br><a href="${empaddress}"> EMPLOYEE EXPENSE TRACKER </a>

</body>
</html>
