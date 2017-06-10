package com.svs.etracker.controller;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.validation.Valid;

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

@Controller
public class ExpenseController {

	@Autowired
	private ExpenseService expenseService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ImageService imageService;

	@Autowired
	public EntityManager entityManager;

	@Value("${address}")
	private String address;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields(new String[] { "category" });
		binder.setDisallowedFields(new String[] { "image" });
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, "createdDate", new CustomDateEditor(format, false));
	}

	@RequestMapping(value = "/expense", method = RequestMethod.GET)
	public String hello(Model model) {

		List<String> categories = categoryService.getOnlyCategories();
		model.addAttribute("categories", categories);
		String addressAddEmployee = address+"trackEmployee";
		String addressTracker = address+"tracker";
		model.addAttribute("empaddress", addressAddEmployee);
		model.addAttribute("address", addressTracker);
		return "heloo";
	}

	@RequestMapping(value = "/addCategories", method = RequestMethod.POST)
	public String addCategories(@RequestParam("addcategory") String newCategory) {
		Category category = new Category();
		category.setCategory(newCategory);
		categoryService.addCategory(category);

		return "redirect:/expense";
	}

	@RequestMapping(value = "/deleteCategories", method = RequestMethod.POST)
	public String deleteCategory(@RequestParam("deleteCategory") String deleteCategory) {
		categoryService.deleteCategory(deleteCategory);
		return "redirect:/expense";
	}

	@RequestMapping(value = "/expenseSubmission", method = RequestMethod.POST)
	public String greetingSubmit(@Valid @ModelAttribute Expense expense, @RequestParam("category") String category,
			BindingResult result, Model model, @RequestParam(value = "image", required=false) MultipartFile file) throws IOException {

		if (result.hasErrors()) {
			return "heloo";
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
			else{
				UploadImage image = new UploadImage();
				File imageFile = new File("C:/Users/Balaji/Desktop/Download/Server Files/No-Image-Available.jpg");
				BufferedImage bufferedImage = ImageIO.read(imageFile);

				// get DataBufferBytes from Raster
				WritableRaster raster = bufferedImage .getRaster();
				DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();

				image.setImage(data.getData());
				image.setExpenseId(expense.getExpenseId());
				imageService.saveImage(image);
			}
			model.addAttribute("Saved", "Expense Saved");
			return "redirect:/expense";
		}
	}
}
