package de.ubweb.taskplaner.task.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.ubweb.taskplaner.main.application.MySQLCon;
import de.ubweb.taskplaner.task.model.Category;
import de.ubweb.utils.database.QueryBuilder;

public class CategoryDAO {
	private static final String TABLE_NAME = "categories";
	private static final String PRIMARY_KEY = "category_id";
	private static final String COL_NAME = "category_name";
	private static final String FK_CATEGORY = "parent_category";

	private static final String RETRIEVE = QueryBuilder.buildSimpleSelectStatement(TABLE_NAME);

	private static Map<Integer, Category> categoryMap;

	public List<Category> retrieveCategories() {
		if (categoryMap == null) {
			categoryMap = initCategories();
		}

		List<Category> categories = new ArrayList<>();

		for (Map.Entry<Integer, Category> entry : categoryMap.entrySet()) {
			categories.add(entry.getValue());

		}

		Collections.sort(categories);

		return categories;
	}

	public Category retrieveById(int categoryId) {
		if (categoryMap == null) {
			categoryMap = initCategories();
		}

		return categoryMap.get(categoryId);
	}

	public List<Category> retrieveMainCategories() {
		if (categoryMap == null) {
			categoryMap = initCategories();
		}

		List<Category> mainCategories = new ArrayList<>();
		for (Map.Entry<Integer, Category> entry : categoryMap.entrySet()) {
			if (entry.getValue().getParentCategoryId() == null) {
				mainCategories.add(entry.getValue());
			}

		}
		Collections.sort(mainCategories);

		return mainCategories;
	}

	private static Map<Integer, Category> initCategories() {
		Map<Integer, Category> categoryMap = new HashMap<>();

		try (PreparedStatement stmt = MySQLCon.getInstance().prepareStatement(RETRIEVE)) {
			ResultSet rs = stmt.executeQuery();

			Category category;
			while (rs.next()) {
				category = new Category();
				category.setCategoryId(rs.getInt(PRIMARY_KEY));
				category.setCategoryName(rs.getString(COL_NAME));
				category.setParentCategoryId(rs.getInt(FK_CATEGORY));

				categoryMap.put(category.getCategoryId(), category);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		//now relations can be set since all categories are retrieved
		for (Map.Entry<Integer, Category> entry : categoryMap.entrySet()) {
			if (entry.getValue().getParentCategoryId() != null) {
				entry.getValue().setParentCategory(categoryMap.get(entry.getValue().getParentCategoryId()));
			}

		}

		return categoryMap;
	}
}
