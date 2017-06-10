package com.svs.etracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.svs.etracker.model.Category;
import com.svs.etracker.service.CategoryService;

@RestController
@RequestMapping("/rest")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@RequestMapping(method = RequestMethod.POST, value = "/category")
	public void addCategory(@RequestBody Category category){
		categoryService.addCategory(category);
	}

	@RequestMapping(method = RequestMethod.GET, value ="/category")
	public List<Category> getCategory(){
		return categoryService.getCategory();
	}

	@RequestMapping(method = RequestMethod.GET, value ="/CategoryList")
	public List<String> getOneCategory(){
		return categoryService.getOnlyCategories();
	}
}
