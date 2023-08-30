package com.blogging.app.services;

import com.blogging.app.payload.CategoryDto;
import com.blogging.app.repositories.CategoryRepo;

import java.util.List;

public interface CategoryService {

    public CategoryDto createCategory(CategoryDto cd);

    public CategoryDto updateCategory(CategoryDto cd,Integer id);

    public void deleteCategory(Integer id);

    public List<CategoryDto> getAllCategory();

    public CategoryDto getByCategoryId(Integer id);
}
