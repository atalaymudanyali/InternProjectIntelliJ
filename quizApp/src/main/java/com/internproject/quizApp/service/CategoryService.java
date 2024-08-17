package com.internproject.quizApp.service;


import java.util.Set;
import com.internproject.quizApp.model.exam.Category;

public interface CategoryService {
    public Category addCategory(Category category);
    public Category updateCategory(Category category);
    public Set<Category> getCategories();
    public Category getCategory(Long categoryId);
    public void delete(Long categoryId);
}