package com.financetracker.managers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

import com.financetracker.exceptions.InvalidTransactionException;
import com.financetracker.models.Category;

public class CategoryManager {
    private static final String CATEGORIES_FILE = "categories.txt";
    private Map<String, Category> categories;
    private Set<String> defaultCategoryNames;

    public CategoryManager() {
        this.categories = new HashMap<>();
        this.defaultCategoryNames = new HashSet<>();
        initializeDefaultCategories();
        loadCategories();
    }

    private void initializeDefaultCategories() {
        Category[] defaults = {
            // Income categories
            new Category("Salary", "Regular employment income", Category.CategoryType.INCOME_CATEGORY, true),
            new Category("Freelance", "Freelance work income", Category.CategoryType.INCOME_CATEGORY, true),
            new Category("Investment", "Investment returns", Category.CategoryType.INCOME_CATEGORY, true),
            new Category("Other Income", "Miscellaneous income", Category.CategoryType.INCOME_CATEGORY, true),
            
            // Expense categories
            new Category("Food", "Groceries and dining", Category.CategoryType.EXPENSE_CATEGORY, true),
            new Category("Transportation", "Car, gas, public transport", Category.CategoryType.EXPENSE_CATEGORY, true),
            new Category("Housing", "Rent, mortgage, utilities", Category.CategoryType.EXPENSE_CATEGORY, true),
            new Category("Entertainment", "Movies, games, hobbies", Category.CategoryType.EXPENSE_CATEGORY, true),
            new Category("Healthcare", "Medical expenses", Category.CategoryType.EXPENSE_CATEGORY, true),
            new Category("Bills", "Utilities, phone, internet", Category.CategoryType.EXPENSE_CATEGORY, true),
            new Category("Shopping", "Clothes, personal items", Category.CategoryType.EXPENSE_CATEGORY, true),
            new Category("Education", "Books, courses, training", Category.CategoryType.EXPENSE_CATEGORY, true),
            new Category("Other", "Miscellaneous expenses", Category.CategoryType.BOTH, true)
        };

        for(Category category: defaults) {
            categories.put(category.getName(), category);
            defaultCategoryNames.add(category.getName());
        }
    }

    //Category CRUD Operations
    public void addCategory(String name, String description, Category.CategoryType type) throws InvalidTransactionException{
        if(categories.containsKey(name)) {
            throw new InvalidTransactionException("Category already exists");
        }
        Category category = new Category(name, description, type);
        categories.put(name, category);
        saveCategories();
    }

    //Getters
    public Category getCategory(String name) {
        return categories.get(name);
    }
    
    public List<Category> getAllCategories() {
        return new ArrayList<>(categories.values());
    }

    public List<Category> getCategoriesForType(Category.CategoryType type) {
        return categories.values().stream().filter(c -> c.getType() == type || c.getType() == Category.CategoryType.BOTH).sorted(Comparator.comparing(Category::getName)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public boolean categoryExists(String name) {
        return categories.containsKey(name);
    }
    
    public void removeCategory(String name) throws InvalidTransactionException {
        Category category = categories.get(name);
        if (category == null) {
            throw new InvalidTransactionException("Category does not exist: " + name);
        }
        
        if (category.isDefault()) {
            throw new InvalidTransactionException("Cannot delete default category: " + name);
        }
        
        categories.remove(name);
        saveCategories();
    }

    // File operations
    private void saveCategories() {
        
    }
}
