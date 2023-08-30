package com.blogging.app.controller;

import com.blogging.app.payload.ApiResponse;
import com.blogging.app.payload.CategoryDto;
import com.blogging.app.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/apis/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    private ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto cd){
        return new ResponseEntity<>(this.categoryService.createCategory(cd), HttpStatus.CREATED);
    }
    @PutMapping("/{categoryId}")
    private ResponseEntity<CategoryDto> updatecategory(@Valid @RequestBody CategoryDto cd, @PathVariable("categoryId") Integer id){
        return new ResponseEntity<>(this.categoryService.updateCategory(cd,id),HttpStatus.ACCEPTED);
    }
    @GetMapping("/")
    private ResponseEntity<List<CategoryDto>> getAllCategories(){
        return new ResponseEntity<>(this.categoryService.getAllCategory(),HttpStatus.OK);
    }
    @GetMapping("/{categoryId}")
    private ResponseEntity<CategoryDto> getBycategoryId(@PathVariable("categoryId") Integer id){
        return new ResponseEntity<>(this.categoryService.getByCategoryId(id),HttpStatus.OK);
    }
    @DeleteMapping("/{categoryId}")
    private ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") int id) {
        this.categoryService.deleteCategory(id);
        return new ResponseEntity<>(new ApiResponse("Category deleted successfully", true), HttpStatus.OK);
    }
}
