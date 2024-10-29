package com.developerteam.techzone.business.concreates;

import com.developerteam.techzone.business.abstracts.ICategoryService;
import com.developerteam.techzone.dataAccess.abstracts.ICategoryRepository;
import com.developerteam.techzone.entities.concreates.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryManager implements ICategoryService {

    private ICategoryRepository categoryRepository;

    @Autowired
    public CategoryManager(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getById(int id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category add(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category update(int id, Category category) {
        Optional<Category> existingCategory = categoryRepository.findById(id);
        if(existingCategory.isPresent()){
            Category updateCategory = existingCategory.get();
            updateCategory.setName(category.getName());
            return categoryRepository.save(updateCategory);
        }
        return null;
    }

    @Override
    public void delete(int id) {
        categoryRepository.deleteById(id);
    }

}
