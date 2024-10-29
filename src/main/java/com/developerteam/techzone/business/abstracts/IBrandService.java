package com.developerteam.techzone.business.abstracts;

import com.developerteam.techzone.entities.concreates.Brand;

import java.util.List;

public interface IBrandService {
    List <Brand> getAll();
    Brand getById(int id); // Belirli bir markayı getirir.
    Brand add(Brand brand); // Yeni marka ekler.
    Brand update(int id, Brand brand); // Mevcut bir markayı günceller.
    void delete(int id); // Mevcut bir markayı siler.

}
