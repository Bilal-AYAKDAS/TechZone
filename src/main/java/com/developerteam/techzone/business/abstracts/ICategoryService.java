package com.developerteam.techzone.business.abstracts;

import com.developerteam.techzone.entities.concreates.Category;
import com.developerteam.techzone.entities.dto.DtoCategory;
import com.developerteam.techzone.entities.dto.DtoCategoryIU;

import java.util.List;

public interface ICategoryService {

    List <DtoCategory> getAll();
    DtoCategory getById(int id);
    DtoCategory add(DtoCategoryIU dtoCategoryIU);
    DtoCategory update(int id, DtoCategoryIU dtoCategoryIu);
    void delete(int id);


}
