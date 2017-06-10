<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>

h3.sansserif {
    font-family: Arial, Helvetica, sans-serif;
    }
    </style>
    <link href="<c:url value="/form.css" />" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tracker</title>
<h1><center>SVS Technologies</center></h1>
<h2><center>Expense tracker</center></h2>
</head>

<body>

<div class="container">

    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h2>${pageContext.request.userPrincipal.name} | <a onclick="document.forms['logoutForm'].submit()">Logout</a></h2>

    </c:if>

</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
<h3 class="sansserif">Track based on Date</h3>

<form action="/dateTracker" method="post"><img src="/pieChart" title = "PieChart" align="right" height="315" width="430"/>
<tr><td>From Date : 					</td>  <td>       <input type="date" name="fromDate" />   </td> </tr>
<tr><td>Till Date : 					</td>  <td>       <input type="date" name="tillDate" />   </td> </tr>
<input type="submit" value="Track" />
</form>

<h3 class="sansserif">Track based on Category</h3> 

<form action="/categoryTracker" method="post">
<tr><td>Select Category :    	    	
										</td>  <td>      <select name="trackCategory">
 											 <c:forEach items="${categories}" var="databaseValue">
  												  <option value="${databaseValue}">
     													   ${databaseValue}
    											  </option>
 											 </c:forEach>
										</select><input type="submit" value="Track" />     </td> </tr>
</form>

<h3 class="sansserif">Track based on Date and Category</h3>
<form action="/dateAndCategoryTracker" method="post">
<tr><td>From Date : 					</td>  <td>       <input type="date" name="fromDate" />   </td> </tr>
<tr><td>Till Date : 					</td>  <td>       <input type="date" name="tillDate" />   </td> </tr>
<tr><td>Select Category :    	    	
										</td>  <td>      <select name="trackCategory">
 											 <c:forEach items="${categories}" var="databaseValue">
  												  <option value="${databaseValue}">
     													   ${databaseValue}
    											  </option>
 											 </c:forEach>
										</select><input type="submit" value="Track" />     </td> </tr>
</form>
</body>
</html>