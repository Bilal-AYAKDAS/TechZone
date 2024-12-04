package com.developerteam.techzone.business.abstracts;

import com.developerteam.techzone.entities.concreates.FavoriProduct;
import com.developerteam.techzone.entities.concreates.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IFavoriProductService {

    List<FavoriProduct> getAll();
    FavoriProduct getById(int id);
    List<FavoriProduct>  getByUser(User user);
    FavoriProduct add(FavoriProduct favoriProduct);
    void delete(int id);



}
