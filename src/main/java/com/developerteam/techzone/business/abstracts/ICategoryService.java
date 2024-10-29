package com.developerteam.techzone.business.abstracts;

import com.developerteam.techzone.entities.concreates.Category;
import java.util.List;

public interface ICategoryService {

    List <Category> getAll();
    Category getById(int id);
    Category add(Category category);
    Category update(int id,Category category);
    void delete(int id);


}
