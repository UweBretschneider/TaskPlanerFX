package de.ubweb.taskplaner.task.model;

import java.io.Serializable;

public class Category implements Serializable, Comparable<Category> {
	private static final long serialVersionUID = 1L;

	private int categoryId;
	private String categoryName;
	private Integer parentCategoryId;
	private Category parentCategory;
	//
	//	public static List<Category> getAllCategories() {
	//		CategoryDAO categoryDAO = new CategoryDAO();
	//		return categoryDAO.retrieveCategories();
	//	}

	public int getCategoryId() {
		return categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Category getParentCategory() {
		return parentCategory;
	}

	public Integer getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(Integer parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}

	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		return categoryName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + categoryId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (categoryId != other.categoryId)
			return false;
		return true;
	}

	@Override
	public int compareTo(Category other) {
		if (this.categoryId < other.getCategoryId()) {
			//this one is "smaller"
			return -1;
		} else if (this.categoryId > other.getCategoryId()) {
			return 1;
		} else {
			return 0;
		}
	}

}
