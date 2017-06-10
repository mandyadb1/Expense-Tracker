package com.svs.etracker.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.svs.etracker.model.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {



	@Query("select c.category from Category c")
	public List<String> getOnlyCategories();

	@Query("select c.categoryId from Category c where c.category = :category")
	public int getIdForCategory(@Param("category") String category);


	public Category findByCategory(String category);

	@Query("SELECT c.category, SUM(e.amount) FROM Category c, Expense e WHERE c.categoryId = e.categoryId GROUP BY c.category")
	public List<Object[]> getPieData();

	/*select c.category, SUM(e.amount)
	from CATEGORY c, Expense e
	where c.CATEGORY_ID = e.CATEGORY_ID
	group by c.CATEGORY;*/

	@Query("SELECT c.category, SUM(e.amount) FROM Category c, Expense e WHERE c.categoryId = e.categoryId AND e.createdDate BETWEEN :fDate AND :tDate GROUP BY c.category")
	public List<Object[]> getPieDataByDate(@Param("fDate")Date fDate, @Param("tDate")Date tDate);

}
