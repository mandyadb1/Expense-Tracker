package com.svs.etracker.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.svs.etracker.model.Employee;
import com.svs.etracker.model.EmployeeExpense;
import com.svs.etracker.service.CategoryService;
import com.svs.etracker.service.EmployeeExpenseService;
import com.svs.etracker.service.EmployeeService;
import com.svs.etracker.util.EmailSender;
import com.svs.etracker.util.ExcelToCsv;
import com.svs.etracker.util.WriteExcel;

@Controller
public class EmployeeController {

	@Autowired
	private ExcelToCsv exceltocsv;

	@Autowired
	private EmailSender emailSender;

	@Autowired
	private WriteExcel writeExcel;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private EmployeeExpenseService employeeExpenseService;

	@Value("${address}")
	private String address;

	@Value("${excelFilePath}")
	private String excelFilePath;

	@Value("${rootPath}")
	private String rootPath;

	private List<String> empNames;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields(new String[] { "category" });
		binder.setDisallowedFields(new String[] { "checkboxgroup" });
		binder.setDisallowedFields(new String[] { "employee" });


		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, "createdDate", new CustomDateEditor(format, false));
	}

	@RequestMapping(value = "/trackEmployee", method = RequestMethod.GET)
	public String trackEMployee(Model model){

		empNames =  employeeService.getFullName();

		model.addAttribute("employees", empNames);
		List<String> categories = categoryService.getOnlyCategories();
		model.addAttribute("categories", categories);
		String addressAddEmployee = address+"addEmployee";
		model.addAttribute("addEmployee", addressAddEmployee);
		String addEmployeeUrl = address+"addEmployee";
		model.addAttribute("address", addEmployeeUrl);
		String view = address+"EmployeeTrackData";
		model.addAttribute("viewPage",view );
		String group = address+"GroupExpense";
		model.addAttribute("group", group);
		return "trackEmployee";
	}

	@RequestMapping(value = "/GroupExpense", method = RequestMethod.GET)
	public String group(Model model){
		List<String> employees = employeeService.getFullName();
		List<String> categories = categoryService.getOnlyCategories();
		model.addAttribute("categories", categories);
		model.addAttribute("employees", employees);
		return "GroupExpense";
	}

	@RequestMapping(value = "/getGroupData", method = RequestMethod.POST)
	public String getGroupData(Model model,
			@RequestParam("checkboxgroup") String[] names,
			@RequestParam("category") String category,
			@RequestParam("createdDate") String date,
			@RequestParam("comments") String comments,
			@RequestParam("amount") double amount,
			@RequestParam("expenseName") String expenseName) throws ParseException{

		ArrayList<Integer> empIds = new ArrayList<Integer>();
		int categoryId = categoryService.getCategoryId(category);
		for (String string : names) {
			empIds.add(employeeExpenseService.getEmployeeId(string));
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date sdate = format.parse(date);
		int splitAmount = (int) (amount/empIds.size());
		for (Integer integer : empIds) {
			EmployeeExpense expense = new EmployeeExpense();
			expense.setEmpId(integer);
			expense.setAmount(splitAmount);
			expense.setCategoryId(categoryId);
			expense.setComments(comments);
			expense.setExpenseName(expenseName);
			expense.setCreatedDate(sdate);

			employeeExpenseService.saveEmployeeExpense(expense);
		}

		return "redirect:/trackEmployee";
	}

	@RequestMapping(value = "/EmployeeTrackData", method = RequestMethod.GET)
	public String viewPage(Model model){
		empNames =  employeeService.getFullName();

		model.addAttribute("employees", empNames);
		return "EmployeeTrackData";
	}


	@RequestMapping(value = "/addEmployee", method = RequestMethod.GET)
	public String addEmployeePage(Model model){
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "addEmployee";
	}

	@RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
	public String hello(@Valid @ModelAttribute Employee emp, Model model, BindingResult result) {

		if(result.hasErrors()){
			return "addEmployee";
		}

		employeeService.addEmployee(emp);
		return "redirect:/trackEmployee";
	}

	@RequestMapping(value = "/deleteEmployee", method = RequestMethod.POST)
	public String delteEmployee(@RequestParam("deleteEmployee") String employee){
		employeeExpenseService.deteEmployeeExpense(employee);
		employeeService.delteEmployee(employee);
		return "redirect:/trackEmployee";
	}

	@RequestMapping(value = "/employeeExpenseSubmission", method = RequestMethod.POST)
	public String hello(@Valid @ModelAttribute EmployeeExpense employeeExpense, Model model, BindingResult result,
			@RequestParam("employee") String fullName, @RequestParam("category") String category) {

		if(result.hasErrors()){
			return "trackEmployee";
		}

		int EmpId = employeeExpenseService.getEmployeeId(fullName);
		employeeExpense.setEmpId(EmpId);
		int categoryId = categoryService.getCategoryId(category);
		employeeExpense.setCategoryId(categoryId);
		employeeExpenseService.saveEmployeeExpense(employeeExpense);
		return "redirect:/trackEmployee";
	}

	@RequestMapping(value = "/EmployTracker", method = RequestMethod.POST)
	public String etracker(@RequestParam("trackEmployee") String name, Model model) throws IOException {
		double total = 0;
		int EmpId = employeeExpenseService.getEmployeeId(name);
		//List<EmployeeExpense> expenses = employeeExpenseService.getExpenses(EmpId);

		List<Object[]> list = employeeExpenseService.getExpenseAndCategoryDetails(EmpId);


		model.addAttribute("customList", list);
		//model.addAttribute("listOfExpense", expenses);
		for (int i=0; i<list.size(); i++){
			Object[] row = list.get(i);
			//System.out.println("Element "+i+Arrays.toString(row));
			double one = (double) row[1];
			total = total +one;
		}

		//String excelFilePath = "C:/Users/Balaji/Desktop/Download/excel.xls";
		writeExcel.writeExcel(list, excelFilePath);
		System.out.println("Success!!!!!!");

		model.addAttribute("name", name);
		model.addAttribute("total",total);
		return "EmployeeExpenseByname";
	}

	@RequestMapping(value = "/deleteExpenseFromEmployee", method = RequestMethod.POST)
	public String deleteExpenseDate(Model model, @RequestParam("checkboxgroup") String[] expenseIds){
		int[] result = new int[expenseIds.length];
		for (int i = 0; i < expenseIds.length; i++) {
			result[i] = Integer.parseInt(expenseIds[i]);
		}
		employeeExpenseService.deleteExpenses(result);
		return "redirect:/trackEmployee";
	}

	@SuppressWarnings("finally")
	@RequestMapping(value = "/sendEmail" , method = RequestMethod.POST)
	public String sendMail(@RequestParam("email") String mailTo){

		String message = "Please Download the attachment";
		try {
			emailSender.sendEmailWithAttachments(mailTo,
					"SVS Expense Sheet", message);
			System.out.println("Email sent.");

		} catch (Exception ex) {
			System.out.println("Could not send email.");
			ex.printStackTrace();

		}

		finally{
			return "redirect:/trackEmployee";
		}
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public String uploadXls(@RequestParam("name") String name,@RequestParam("trackEmployee") String empName,
			@RequestParam("file") MultipartFile file, Model model){

		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				//String rootPath ="C:/Users/Balaji/Desktop/Download";
				File dir = new File(rootPath + File.separator + "Server Files");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + name+".xls");
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				exceltocsv.caller(serverFile,empName);
				model.addAttribute("message", "You successfully uploaded file=" + name);

				return "result";
			} catch (Exception e) {
				model.addAttribute("message", "You failed to upload " + name + " => " + e.getMessage());
				return "result";
			}
		} else {
			model.addAttribute("message", "You failed to upload " + name
					+ " because the file was empty.");
			return "result";
		}

	}

}
