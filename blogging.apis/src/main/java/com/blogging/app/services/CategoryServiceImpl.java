package com.blogging.app.services;

import com.blogging.app.entity.Category;
import com.blogging.app.exceptions.ResourceNotFoundException;
import com.blogging.app.payload.CategoryDto;
import com.blogging.app.repositories.CategoryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto cd) {

        return this.categoryToCategoryDto(this.categoryRepo.save(this.categoryDtoToCategory(cd)));
    }

    @Override
    public CategoryDto updateCategory(CategoryDto cd, Integer id) {
        Category category=this.categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category"," ID ",id));
        category.setCategoryTitle(cd.getCategoryTitle());
        category.setCategoryDescription(cd.getCategoryDescription());
        this.categoryRepo.save(category);
        return this.categoryToCategoryDto(category);
    }

    @Override
    public void deleteCategory(Integer id) {
     Category cd=this.categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category"," ID ",id));
     this.categoryRepo.delete(cd);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> li=this.categoryRepo.findAll();
        return li.stream().map(category -> this.categoryToCategoryDto(category)).collect(Collectors.toList());
    }

    @Override
    public CategoryDto getByCategoryId(Integer id) {
        Category cd=this.categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category"," ID ",id));
        return this.categoryToCategoryDto(cd);
    }

    public Category categoryDtoToCategory(CategoryDto cd){
        Category category=this.modelMapper.map(cd, Category.class);
        return category;
    }
    public CategoryDto categoryToCategoryDto(Category cd){
        CategoryDto cdt=this.modelMapper.map(cd,CategoryDto.class);
        return cdt;
    }
}
