package com.makingiants.liveview.funny.model.sounds;

import java.util.ArrayList;

import android.content.Context;

import com.makingiants.liveview.funny.model.dao.CategoryDAO;

/**
 * Manage the list of categories
 *
 */
public class CategoryManager {
	
	// ****************************************************************
	// Attributes
	// ****************************************************************
	
	// Category 
	private ArrayList<Category> categories;
	private int actualCategory;
	
	// ****************************************************************
	// Constructor
	// ****************************************************************
	
	public CategoryManager(final Context context) {
		
		this.categories = CategoryDAO.getCategories(context);
		this.actualCategory = 0;
		
	}
	
	// ****************************************************************
	// Accessor Methods
	// ****************************************************************
	
	public int getActualSoundNumber() {
		return categories.get(actualCategory).getActualSoundNumber();
	}
	
	public int getActualCategoryNumber() {
		return actualCategory + 1;// To avoid 0 value
	}
	
	public int getCategoriesLength() {
		return categories.size();
	}
	
	public int getSoundsLength() {
		return categories.get(actualCategory).getSoundsLength();
	}
	
	public Sound getActualSound() {
		Category category = categories.get(actualCategory);
		return category.getActualSound();
	}
	
	public String getActualCategory() {
		return categories.get(actualCategory).getName();
	}
	
	// ****************************************************************
	// Jump Methods
	// ****************************************************************
	
	public String moveNextSound() {
		return categories.get(actualCategory).moveToNextSound().getName();
	}
	
	public String movePreviousSound() {
		return categories.get(actualCategory).moveToPreviousSound().getName();
	}
	
	public String moveNextCategory() {
		actualCategory++;
		if (actualCategory >= categories.size()) {
			actualCategory = 0;
		}
		return categories.get(actualCategory).getName();
		
	}
	
	public String movePreviousCategory() {
		actualCategory--;
		if (actualCategory < 0) {
			actualCategory = categories.size() - 1;
		}
		return categories.get(actualCategory).getName();
		
	}
	
}
