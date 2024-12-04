package com.developerteam.techzone.business.concreates;

import com.developerteam.techzone.business.abstracts.IBrandService;
import com.developerteam.techzone.dataAccess.abstracts.IBrandRepository;
import com.developerteam.techzone.entities.concreates.Brand;
import com.developerteam.techzone.entities.dto.DtoBrand;
import com.developerteam.techzone.exception.BaseException;
import com.developerteam.techzone.exception.ErrorMessage;
import com.developerteam.techzone.exception.MessageType;
import org.springframework.beans.BeanUtils;
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
    public DtoBrand getById(int id ){
        Brand dbBrand = findBrandOrThrow(id);
        DtoBrand response = new DtoBrand();
        BeanUtils.copyProperties(dbBrand, response);
        return response;
    }

    @Override
    public DtoBrand add(DtoBrand dtoBrand){
        Brand brand = new Brand();
        BeanUtils.copyProperties(dtoBrand,brand);
        Brand dbStudent = brandRepository.save(brand);
        DtoBrand response = new DtoBrand();
        BeanUtils.copyProperties(dbStudent,response);
        return response;
    }

    @Override
    public DtoBrand update(int id,DtoBrand dtoBrand){

        Brand existingBrand = findBrandOrThrow(id);
        existingBrand.setName(dtoBrand.getName());
        Brand dbBrand = brandRepository.save(existingBrand);
        DtoBrand response = new DtoBrand();
        BeanUtils.copyProperties(dbBrand,response);
        return response;
    }

    @Override
    public void delete(int id){
        findBrandOrThrow(id);
        brandRepository.deleteById(id);
    }

    private Brand findBrandOrThrow(int id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, Integer.toString(id))));
    }


}
