package com.svs.etracker.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svs.etracker.model.Category;
import com.svs.etracker.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	public CategoryRepository categoryRepository;

	@Autowired

	public List<String> getOnlyCategories(){

		return categoryRepository.getOnlyCategories();
	}

	public List<Category> getCategory(){
		List<Category> categories = new ArrayList<Category>();
		categoryRepository.findAll()
		.forEach(categories::add);
		return categories;
	}

	public void addCategory(Category category){
		categoryRepository.save(category);
	}

	public int getCategoryId(String category){
		return categoryRepository.getIdForCategory(category);
	}

	public void deleteCategory(String category){

		Category deleteCategory = categoryRepository.findByCategory(category);
		if(deleteCategory==null){

		}
		categoryRepository.delete(deleteCategory.getCategoryId());
	}

	public int saveOrgetCategory(String category){
		int id=0;
		category.trim();

		/*id = categoryRepository.getIdForCategory(category);
		if(id==0){
			Category obj = new Category();
			obj.setCategory(category);
			addCategory(obj);
			return categoryRepository.getIdForCategory(category);
		}
		else{
			return id;
		}*/

		List<String> categories = getOnlyCategories();
		if(categories.contains(category)){
			id = categoryRepository.getIdForCategory(category);
			return id;
		}
		else{
			Category obj = new Category();
			obj.setCategory(category);
			addCategory(obj);
			return categoryRepository.getIdForCategory(category);
		}
	}

	public Category getCategoryById(int id){
		return categoryRepository.findOne(id);
	}


	public Map<String, Double> getPieData(){

		List<Object[]> object=categoryRepository.getPieData();
		Map<String, Double> pie = new HashMap<String, Double>();
		for (int i=0; i<object.size(); i++){
			Object[] pieRow = object.get(i);
			String category= (String)pieRow[0];
			Double amount = (Double)pieRow[1];
			pie.put(category,amount);
		}
		return pie;
	}

	public Map<String, Double> getPieData(Date fDate, Date tDate){

		List<Object[]> object=categoryRepository.getPieDataByDate(fDate, tDate);
		Map<String, Double> pie = new HashMap<String, Double>();
		for (int i=0; i<object.size(); i++){
			Object[] pieRow = object.get(i);
			String category= (String)pieRow[0];
			Double amount = (Double)pieRow[1];
			pie.put(category,amount);
		}
		return pie;
	}


}
