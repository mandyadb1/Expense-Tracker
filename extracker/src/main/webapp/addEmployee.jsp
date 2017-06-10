<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Employee</title>
<h2>Add New Employee</h2>
</head>

<body>
<form:errors path="employee.*" />
<form action="/addEmployee" method="post">
<table>
		<tr><td>Full Name :   		</td>  <td>       <input type="text" name="firstName"/>    </td> </tr>
		
		<tr><td>Phone Number : 					</td>  <td>       <input type="text" name="phoneNumber" />   </td> </tr>
		<tr><td>Email ID :    	    	</td>  <td>       <input type="text" name="emailId" />      </td> </tr>
		
		
		
	
										
		
		<tr><td><input type="submit" value="Add Employee" /></td></tr>
	</table>
	</form>
	
</body>
</html>