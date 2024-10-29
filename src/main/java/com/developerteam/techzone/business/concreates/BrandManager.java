package com.developerteam.techzone.business.concreates;

import com.developerteam.techzone.business.abstracts.IBrandService;
import com.developerteam.techzone.dataAccess.abstracts.IBrandRepository;
import com.developerteam.techzone.entities.concreates.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandManager implements IBrandService {

    private IBrandRepository brandRepository;

    @Autowired
    public BrandManager(IBrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public List<Brand> getAll() {
        return brandRepository.findAll();
    }

    @Override
    public Brand getById(int id ){
        return brandRepository.findById(id).orElse(null);
    }

    @Override
    public  Brand add(Brand brand){
        return brandRepository.save(brand);
    }

    @Override
    public Brand update(int id,Brand brand){

        Optional <Brand> existingBrand = brandRepository.findById(id);
        if(existingBrand.isPresent()){
            Brand updateBrand = existingBrand.get();
            updateBrand.setName(brand.getName());
            return brandRepository.save(updateBrand);
        }
        return null;
    }

    @Override
    public void delete(int id){
        brandRepository.deleteById(id);
    }

}
