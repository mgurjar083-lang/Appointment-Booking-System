package com.mynk.service;

import com.mynk.dto.SalonDTO;
import com.mynk.modal.Category;

import java.util.Set;

public interface CategoryService {

    Category saveCategory(Category category, SalonDTO salonDTO);

    Set<Category> getAllCategoriesBySalon(Long id);

    Category getCategoryById(Long id);

    void deleteCategoryById(Long id, Long salonId);
}
