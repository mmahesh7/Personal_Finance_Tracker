package com.financetracker.models;

import java.util.Objects;

public class Category {

    public enum CategoryType{
        INCOME_CATEGORY,
        EXPENSE_CATEGORY,
        BOTH
    }

    private String name;
    private String description;
    private CategoryType type;
    private boolean isDefault;

    public Category(String name, String description, CategoryType type, boolean isDefault) {
        if(name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }
        this.name = name.trim();
        this.description = description != null ? description.trim() : "";
        this.type = type != null ? type : CategoryType.BOTH;
        this.isDefault = isDefault;
    }

    //Convenience Constructor
    public Category(String name, String description, CategoryType type) {
        this(name, description, type, false);
    }

    //Getters
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public CategoryType getType() {
        return type;
    }
    public boolean isDefault() {
        return isDefault;
    }

    // Setters (only for non-default categories)
    public void setName(String name) {
        if(isDefault) {
            throw new IllegalStateException("Cannot modify default category");
        }
        if(name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }
        this.name = name.trim();
    }

    public void setDescription(String description) {
        if(isDefault) {
            throw new IllegalStateException("Cannot modify default category");
        }
        this.description = description != null ? description.trim() : "";
    }

    //Business logic
    public boolean canBeUsedFor(String transactionType) {
        switch (type) {
            case INCOME_CATEGORY:
                return "INCOME".equals(transactionType);
            case EXPENSE_CATEGORY:
                return "OUTCOME".equals(transactionType);
            case BOTH:
                return true;
            default:
                return false;
        }
    }

    //File persistance
    public String toFileFormat() {
        String safeName = name.replace("|","\\|");
        String safeDesc = description.replace("|","\\|");
        return String.join("|", safeName, safeDesc, type.name(), Boolean.toString(isDefault));
    }

    public static Category fromFileFormat(String line) {
        // split on unescaped '|' (simple approach: split and then unescape)
        String[] parts = line.split("\\|", -1); // use -1 to keep empty fields
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid category file format: " + line);
        }

        String name = parts[0].replace("\\|", "|").trim();
        String desc = parts[1].replace("\\|", "|").trim();
        CategoryType type = CategoryType.valueOf(parts[2].trim());
        boolean isDefault = Boolean.parseBoolean(parts[3].trim());
        return new Category(name, desc, type, isDefault);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Category category = (Category) obj;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", name, type.toString().replace("_", " "));
    }
}
