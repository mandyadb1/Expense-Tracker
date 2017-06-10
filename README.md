# Expense-Tracker

This is an Web Application to Track Expenses developed using :

1. Java 1.8
2. Spring Boot 1.5.1
3. Spring Data JPA
4. Spring Security


Authentication:

Authentication is provided using spring security. The user is allowed to create a new account and track the expense. The password will be stored in the database in encrypted format. An option is provided to logout of his account in every page.

Database:

Currently the application is using Oracle database, all the credentails can be changed in application properties. No need to create database or tables, everything will be created for you, all you have to do is change the credentails.

Server:

Runs in any tomcat 8.0 or higher versions.

Application Features:

1. The application lets you enter the expenses and submit it. You can even upload a receipt for every record of expense.
2. If the category is not present, you will be allowed to add a new category.
3. If you feel the category is not necessary anymore, you can delete it.
4. The TRACK EXPENSE button will take you to the page where you can track the expenses based on 
   Date
   Category
   Combination(Date + Category) and also the application makes use of JFREE CHART with which you will be able to see the pie chart of the expenses base on category.
5. On tracking the expense based on above categories, you will be having below features
   - select one or many and delete the expense
   - edit any expense
   - hover over the receipt for zoom view
   - download the expense as excel format.Used Apache Poi to write into excel sheet.
   - see the total expense
   - enter the email and clicking on send button, an email will be sent to the entered email with an attachment od excel sheet with you      your expense.
6. EMPLOYEE EXPENSE TRACKER, its an other part of the same application with some additional features
   - An expense can be added to a perticular employee
   - If the employee is not present in the drop down menu, a new employee can be added
   - Expense can be tracked based on a employee name
   - If the there are multiple expenses, instead of entering all the expenses, option has been provided to upload the excel sheet with        multiple entries of expenses(entries can be written anywhere in the excel sheet as long as it has all the 5 fields, all the              expenses can be recorded, if any new category is scanned, the applcation will create a new category and put the expense under that      category) for the perticaular employee
7. Add Expense To Group Of Employee, helps to split the amount among multiple employees.
   - Option will be provided to select the employees and add expenses to selected employees and the amount will be split among the            selected employees
   
Thanks,
Balaji
   
  




