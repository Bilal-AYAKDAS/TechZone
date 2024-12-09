package com.developerteam.techzone.business.abstracts;

import com.developerteam.techzone.entities.dto.DtoFavoriProduct;
import com.developerteam.techzone.entities.dto.DtoFavoriProductIU;

import java.util.List;


public interface IFavoriProductService {

    List<DtoFavoriProduct> getAll();
    DtoFavoriProduct add(DtoFavoriProductIU dtoFavoriProductIU);
    void delete(int id);



}
