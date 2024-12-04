package com.developerteam.techzone.business.abstracts;

import com.developerteam.techzone.entities.concreates.Brand;
import com.developerteam.techzone.entities.dto.DtoBrand;

import java.util.List;

public interface IBrandService {
    List <Brand> getAll();
    DtoBrand getById(int id); // Belirli bir markayı getirir.
    DtoBrand add(DtoBrand dtoBrand); // Yeni marka ekler.
    DtoBrand update(int id, DtoBrand dtoBrand); // Mevcut bir markayı günceller.
    void delete(int id); // Mevcut bir markayı siler.

}
