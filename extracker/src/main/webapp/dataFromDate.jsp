<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link href="<c:url value="/form.css" />" rel="stylesheet">
<style type="text/css">

.pic{
	height="40";
	width="40";
	
}

.picbig{
	
	position:absolute;
	width:0px;
	-webkit-transition:width:0.3s linear 0s;
	transition:width:0.3s linear 0s;
	 -moz-transition: background 0.3s linear; /* Firefox 4 */
    -webkit-transition: background 0.3s linear; /* Safari and Chrome */
    -o-transition: background 0.3s linear; /* Opera */
    -ms-transition: background 0.3s linear; /* Explorer 10 */
	z-index:10;
}

.pic:hover+ .picbig, .pic:active+ .picbig{
	left:600px;
	width:500px;
	height:400px;
}
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

#customers {
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
	border-collapse: collapse;
	width: 100%;
}

#customers td, #customers th {
	border: 1px solid #ddd;
	padding: 8px;
}

#customers tr:nth-child(even) {
	background-color: #f2f2f2;
}

#customers tr:hover {
	background-color: #ddd;
}

#customers th {
	padding-top: 12px;
	padding-bottom: 12px;
	text-align: left;
	background-color: #4CAF50;
	color: white;
}
</style>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>EXPENSES</title>
</head>
<body>
	<div class="container">

		<c:if test="${pageContext.request.userPrincipal.name != null}">
			<form id="logoutForm" method="POST" action="${contextPath}/logout">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form>

			<h2>
				${pageContext.request.userPrincipal.name} | <a
					onclick="document.forms['logoutForm'].submit()">Logout</a>
			</h2>

		</c:if>

	</div>
	<!-- /container -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
	<h4 class="sansserif">
		From
		<fmt:formatDate value="${from}" pattern="yy-MMM-dd" />
		till
		<fmt:formatDate value="${to}" pattern="yy-MMM-dd" />
	</h4>
	<form action="/deleteExpenseFromCategory" method="POST">
		<TABLE id="customers" BORDER="1">
			<TR>
				<TH>Expense Name</TH>
				<TH>Comments</TH>
				<TH>Date</TH>
				<TH>Amount</TH>
				<TH>Category</TH>
				<TH>Receipt</TH>
				<TH>Delete</TH>
				<th>Edit</th>
			</TR>

			<c:forEach items="${expense}" var="element">


				<TR>
					<TD>${element[0]}</td>
					<TD>${element[3]}</TD>
					<TD><fmt:formatDate value="${element[2]}" pattern="yy-MMM-dd" /></TD>
					<TD>${element[1]}</TD>
					<TD>${element[5]}</TD>

					<td><a><img class="pic" src="/imageDisplay?id=${element[4]}" width="40" height="40" />
						<img class="picbig" src="/imageDisplay?id=${element[4]}" />
						</a></td>
				

					<td align="center"><input type="checkbox" name="checkboxgroup"
						value="${element[4]}" /></td>
						
				     <td> <a href = "<c:url value="/edit/${element[4]}" />" >EDIT</a></td>
				</TR>
			</c:forEach>
		</TABLE>
		<input type="submit" value="Delete Expenses" />
		<h3 class="sansserif">Total Expenditure :$ ${total}</h3>
	</form>

	<div align="left"><img src="/pieChart" title = "PieChart" align="right" height="315" width="430"/>
		<h1>Download Excel Format</h1>
		<h3>
			<a href="/downloadExcel">Download Excel Document</a>
		</h3>
	</div>

	<h2>Send as Email</h2>
	<form action="/sendEmail" method="post">
		<tr>
			<td>Email ID :</td>
			<td><input type="text" name="email" /></td>
		</tr>
		<tr>
			<td><input type="submit" value="Send Email" /></td>
		</tr>
	</form>


</body>
</html>