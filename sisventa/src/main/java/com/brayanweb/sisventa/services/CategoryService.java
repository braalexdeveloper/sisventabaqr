package com.brayanweb.sisventa.services;

import com.brayanweb.sisventa.dtos.CategoryRequest;
import com.brayanweb.sisventa.dtos.CategoryResponse;
import com.brayanweb.sisventa.exceptions.ResourceNotFoundException;
import com.brayanweb.sisventa.models.Category;
import com.brayanweb.sisventa.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
    }
    
        
    public List<CategoryResponse> getCategories(){
        List<CategoryResponse> categories=categoryRepository.findAll().stream()
                .map(this::convertToCategoryResponse).collect(Collectors.toList());
        return categories;
    }
    
    public CategoryResponse getCategory(Long id){
        Category category=categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Categoria no encontrada"));
        return convertToCategoryResponse(category);
    }
    
    public CategoryResponse create(CategoryRequest categoryRequest){
        Category category=categoryRepository.save(convertToCategory(new Category(),categoryRequest));
        return convertToCategoryResponse(category);
    }
    
    @Transactional
    public CategoryResponse update(Long id,CategoryRequest categoryRequest){
        Category updatedCategory=categoryRepository.findById(id).map(categoryFound->{
         return categoryRepository.save(convertToCategory(categoryFound,categoryRequest));
        }).orElseThrow(()->new ResourceNotFoundException("Categoria no encontrada"+id));
        
        return convertToCategoryResponse(updatedCategory);
    }
    
    @Transactional
    public String delete(Long id){
        if(!categoryRepository.existsById(id)){
            throw new ResourceNotFoundException("Categoria no encontrada");
        }
        categoryRepository.deleteById(id);
        return "Categoria eliminada correctamente";
        
    }
    
    private Category convertToCategory(Category category,CategoryRequest categoryRequest){
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        return category;
    }
    
    private CategoryResponse convertToCategoryResponse(Category category){
        CategoryResponse categoryResponse=new CategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        categoryResponse.setDescription(category.getDescription());
        return categoryResponse;
    }
}
