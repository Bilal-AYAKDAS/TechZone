package com.developerteam.techzone.business.concreates;

import com.developerteam.techzone.business.abstracts.ICategoryService;
import com.developerteam.techzone.dataAccess.abstracts.ICategoryRepository;
import com.developerteam.techzone.entities.concreates.Brand;
import com.developerteam.techzone.entities.concreates.Category;
import com.developerteam.techzone.entities.dto.DtoBrand;
import com.developerteam.techzone.entities.dto.DtoCategory;
import com.developerteam.techzone.entities.dto.DtoCategoryIU;
import com.developerteam.techzone.exception.BaseException;
import com.developerteam.techzone.exception.ErrorMessage;
import com.developerteam.techzone.exception.MessageType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryManager implements ICategoryService {

    @Autowired
    private ICategoryRepository categoryRepository;

    public CategoryManager(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<DtoCategory> getAll() {
        List<Category> categories = categoryRepository.findAll();
        List<DtoCategory> dtoCategories = new ArrayList<>();
        for (Category category : categories) {
            DtoCategory dtoCategory = new DtoCategory();
            BeanUtils.copyProperties(category, dtoCategory);
            dtoCategories.add(dtoCategory);
        }
        return dtoCategories;
    }

    @Override
    public DtoCategory getById(int id) {
        Category dbCategory = findCategoryOrThrow(id);
        DtoCategory response = new DtoCategory();
        BeanUtils.copyProperties(dbCategory, response);
        return response;
    }

    @Override
    public DtoCategory add(DtoCategoryIU dtoCategoryIU) {
        Category category = new Category();
        BeanUtils.copyProperties(dtoCategoryIU, category);
        Category dbCategory = categoryRepository.save(category);
        DtoCategory response = new DtoCategory();
        BeanUtils.copyProperties(dbCategory, response);
        return response;
    }

    @Override
    public DtoCategory update(int id, DtoCategoryIU dtoCategoryIU) {
        Category existingCategory = findCategoryOrThrow(id);
        existingCategory.setName(dtoCategoryIU.getName());
        DtoCategory response = new DtoCategory();
        Category dbCategory = categoryRepository.save(existingCategory);
        BeanUtils.copyProperties(dbCategory, response);

        return response;
    }

    @Override
    public void delete(int id) {
        findCategoryOrThrow(id);
        categoryRepository.deleteById(id);
    }

    private Category findCategoryOrThrow(int id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, Integer.toString(id))));
    }

}
