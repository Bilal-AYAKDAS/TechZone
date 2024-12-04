package com.developerteam.techzone.business.abstracts;

import com.developerteam.techzone.entities.concreates.Category;
import com.developerteam.techzone.entities.dto.DtoCategory;

import java.util.List;

public interface ICategoryService {

    List <Category> getAll();
    DtoCategory getById(int id);
    DtoCategory add(DtoCategory dtoCategory);
    DtoCategory update(int id,DtoCategory dtoCategory);
    void delete(int id);


}
