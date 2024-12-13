package com.developerteam.techzone.business.abstracts;

import com.developerteam.techzone.entities.dto.DtoBrand;
import com.developerteam.techzone.entities.dto.DtoBrandIU;

import java.util.List;

public interface IBrandService {
    List <DtoBrand> getAll();
    DtoBrand getById(int id); // Belirli bir markayı getirir.
    DtoBrand add(DtoBrandIU dtoBrandIU); // Yeni marka ekler.
    DtoBrand update(int id, DtoBrandIU dtoBrandIU); // Mevcut bir markayı günceller.
    void delete(int id); // Mevcut bir markayı siler.

}
