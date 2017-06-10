package com.svs.etracker.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.svs.etracker.model.Category;
import com.svs.etracker.model.Expense;
import com.svs.etracker.model.UploadImage;
import com.svs.etracker.service.CategoryService;
import com.svs.etracker.service.ExpenseService;
import com.svs.etracker.service.ImageService;
import com.svs.etracker.util.PieChart;
import com.svs.etracker.util.WriteExcel;

@Controller
public class TrackerController {

	@Autowired
	private PieChart pieChart;

	@Autowired
	private WriteExcel writeExcel;

	@Autowired
	private ExpenseService expenseService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ImageService imageService;


	@Value("${excelFilePath}")
	private String excelFilePath;


	@Value("${rootPath}")
	private String rootPath;


	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields(new String[] { "category" });
		binder.setDisallowedFields(new String[] { "image" });
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, "createdDate", new CustomDateEditor(format, false));
	}

	@RequestMapping(value = "/tracker", method = RequestMethod.GET)
	public String trackerPage(Model model,HttpServletResponse response) throws IOException {

		List<String> categories = categoryService.getOnlyCategories();
		Map<String, Double> pieData = categoryService.getPieData();
		pieChart.createAndSavePieChart(pieData);

		model.addAttribute("categories", categories);
		return "tracker";
	}

	@RequestMapping(value = "/dateTracker", method = RequestMethod.POST)
	public String trackViaDate(Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("tillDate") String tillDate, HttpServletResponse response) throws ParseException, IOException {

		double total = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date fDate = format.parse(fromDate);
		Date tDate = format.parse(tillDate);
		List<Object[]> object = expenseService.getbydate(fDate, tDate);

		Map<String, Double> pieData = categoryService.getPieData(fDate, tDate);
		pieChart.createAndSavePieChart(pieData);




		for (int i=0; i<object.size(); i++){
			Object[] row = object.get(i);
			double one = (double) row[1];
			total = total +one;
		}

		//String excelFilePath = "C:/Users/Balaji/Desktop/Download/excel.xls";
		writeExcel.writeExcel(object, excelFilePath);
		System.out.println("Success!!!!!!");


		model.addAttribute("total", total);
		model.addAttribute("from", fDate);
		model.addAttribute("to", tDate);
		model.addAttribute("expense", object);
		return "dataFromDate";
	}

	@RequestMapping(value = "/categoryTracker", method = RequestMethod.POST)
	public String trackViaCategory(Model model, @RequestParam("trackCategory") String category) throws IOException {
		double total = 0;
		List<Object[]> expenses = expenseService.getbyCategory(category);

		for (int i=0; i<expenses.size(); i++){
			Object[] row = expenses.get(i);
			double one = (double) row[1];
			total = total +one;
		}



		//String excelFilePath = "C:/Users/Balaji/Desktop/Download/excel.xls";
		writeExcel.writeExcel(expenses, excelFilePath);
		System.out.println("Success!!!!!!");


		model.addAttribute("total", total);
		model.addAttribute("category", category);
		model.addAttribute("expense", expenses);
		return "dataFromCategory";
	}

	@RequestMapping(value = "/dateAndCategoryTracker", method = RequestMethod.POST)
	public String trackViaDateAndCategory(Model model, @RequestParam("trackCategory") String category,
			@RequestParam("fromDate") String fromDate, @RequestParam("tillDate") String tillDate)
					throws ParseException, IOException {
		double total = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date fDate = format.parse(fromDate);
		Date tDate = format.parse(tillDate);
		List<Object[]> cdexpense = expenseService.getByCategoryAndDate(fDate, tDate, category);
		for (int i=0; i<cdexpense.size(); i++){
			Object[] row = cdexpense.get(i);
			double one = (double) row[1];
			total = total +one;
		}

		//String excelFilePath = "C:/Users/Balaji/Desktop/Download/excel.xls";
		writeExcel.writeExcel(cdexpense, excelFilePath);
		System.out.println("Success!!!!!!");

		model.addAttribute("expense", cdexpense);
		model.addAttribute("total", total);
		model.addAttribute("from", fDate);
		model.addAttribute("to", tDate);
		model.addAttribute("category", category);
		return "DataFromCategoryAndDate";
	}

	@RequestMapping(value = "/deleteExpenseFromCategory", method = RequestMethod.POST)
	public String deleteExpenseCategory(Model model, @RequestParam("checkboxgroup") String[] expenseIds) {
		int[] result = new int[expenseIds.length];
		for (int i = 0; i < expenseIds.length; i++) {
			result[i] = Integer.parseInt(expenseIds[i]);
		}
		expenseService.deleteExpenses(result);
		imageService.deleteUploadImage(result);
		return "redirect:/tracker";
	}

	@RequestMapping(value = "/deleteExpenseFromDate", method = RequestMethod.POST)
	public String deleteExpenseDate(Model model, @RequestParam("checkboxgroup") String[] expenseIds) {
		int[] result = new int[expenseIds.length];
		for (int i = 0; i < expenseIds.length; i++) {
			result[i] = Integer.parseInt(expenseIds[i]);
		}
		expenseService.deleteExpenses(result);
		imageService.deleteUploadImage(result);
		return "redirect:/tracker";
	}

	@RequestMapping(value = "/downloadExcel", method = RequestMethod.GET)
	public void getFile(HttpServletResponse response) {
		try {
			// get your file as InputStream
			InputStream initialFile = new FileInputStream(excelFilePath);
			response.setHeader("Content-disposition", "attachment;filename=myExcel.xls");
			response.setContentType("application/vnd.ms-excel");
			// copy it to response's OutputStream
			org.apache.commons.io.IOUtils.copy(initialFile, response.getOutputStream());
			response.flushBuffer();
		} catch (IOException ex) {

			throw new RuntimeException("IOError writing file to output stream");
		}

	}

	@RequestMapping(value = "/imageDisplay", method = RequestMethod.GET)
	public void showImage(@RequestParam("id") Integer expenseId, HttpServletResponse response,HttpServletRequest request)
			throws ServletException, IOException{


		UploadImage ui = imageService.getUploadImage(expenseId);
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		response.getOutputStream().write(ui.getImage());


		response.getOutputStream().close();
	}

	@RequestMapping(value = "/pieChart", method = RequestMethod.GET)
	public void showPieChart(HttpServletResponse response)throws ServletException, IOException{
		File file = new File(rootPath+"/PieChart.png");
		response.setContentType("image/png");
		BufferedImage bi = ImageIO.read(file);
		OutputStream out = response.getOutputStream();
		ImageIO.write(bi, "png", out);
		out.close();

	}



	@RequestMapping(value = "/edit/{expenseId}", method = RequestMethod.GET)
	public String EditController(@PathVariable("expenseId") String expId, Model model) {
		int expenseId = Integer.parseInt(expId);
		Expense expense = expenseService.getExpenseById(expenseId);
		Category category = categoryService.getCategoryById(expense.getCategoryId());
		UploadImage image = imageService.getUploadImage(expenseId);
		List<String> categories = categoryService.getOnlyCategories();
		model.addAttribute("expense", expense);
		model.addAttribute("categories", categories);
		model.addAttribute("StringCategory", category.getCategory());
		model.addAttribute("onlyImage",image.getImage());

		return "edit";
	}

	@RequestMapping(value = "/UpdateExpenseSubmission/{expenseId}", method = RequestMethod.POST)
	public String UpdateExpenseController(@PathVariable("expenseId") String expId, Model model,
			@ModelAttribute Expense expense, @RequestParam("category") String category,
			BindingResult result, @RequestParam(value = "image", required=false) MultipartFile file) throws IOException {

		if(result.hasErrors()){
			int expenseId = Integer.parseInt(expId);
			return "redirect:/edit/"+expenseId;
		}

		else{
			int categoryId = categoryService.getCategoryId(category);
			expense.setCategoryId(categoryId);
			expenseService.addexpense(expense);
			if(file!=null){
				UploadImage image = new UploadImage();
				image.setImage(file.getBytes());
				image.setExpenseId(expense.getExpenseId());
				imageService.saveImage(image);
			}
			return "redirect:/tracker";
		}
	}}
