package com.mynk.service;

import com.mynk.dto.SalonDTO;
import com.mynk.modal.Category;
import com.mynk.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category, SalonDTO salonDTO) {

        Category newCategory = new Category();
        newCategory.setName(category.getName());
        newCategory.setImage(category.getImage());
        newCategory.setSalonId(category.getSalonId());

        return categoryRepository.save(newCategory);
    }

    @Override
    public Set<Category> getAllCategoriesBySalon(Long id) {
        return categoryRepository.findBySalonId(id);
    }

    @Override
    public Category getCategoryById(Long id) {

        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not exists with id : "+id));
    }

    @Override
    public void deleteCategoryById(Long id, Long salonId) {
        Category category = getCategoryById(id);

        if(category.getSalonId().equals(salonId)){
            categoryRepository.deleteById(id);
        }else {
            throw new RuntimeException("You do not have permission to delete this category");
        }

    }
}
