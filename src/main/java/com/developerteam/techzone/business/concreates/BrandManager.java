package com.developerteam.techzone.business.concreates;

import com.developerteam.techzone.business.abstracts.IBrandService;
import com.developerteam.techzone.dataAccess.abstracts.IBrandRepository;
import com.developerteam.techzone.entities.concreates.Brand;
import com.developerteam.techzone.entities.dto.DtoBrand;
import com.developerteam.techzone.entities.dto.DtoBrandIU;
import com.developerteam.techzone.exception.BaseException;
import com.developerteam.techzone.exception.ErrorMessage;
import com.developerteam.techzone.exception.MessageType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BrandManager implements IBrandService {

    @Autowired
    private IBrandRepository brandRepository;

    @Override
    public List<DtoBrand> getAll() {
        List<Brand> brands = brandRepository.findAll();
        List<DtoBrand> dtoBrands = new ArrayList<>();
        for (Brand brand : brands) {
            DtoBrand dtoBrand = new DtoBrand();
            BeanUtils.copyProperties(brand, dtoBrand);
            dtoBrands.add(dtoBrand);
        }

        return dtoBrands;
    }

    @Override
    public DtoBrand getById(int id ){
        Brand dbBrand = findBrandOrThrow(id);
        DtoBrand response = new DtoBrand();
        BeanUtils.copyProperties(dbBrand, response);
        return response;
    }

    @Override
    public DtoBrand add(DtoBrandIU dtoBrandIU){
        Brand brand = new Brand();
        BeanUtils.copyProperties(dtoBrandIU,brand);
        Brand dbStudent = brandRepository.save(brand);
        DtoBrand response = new DtoBrand();
        BeanUtils.copyProperties(dbStudent,response);
        return response;
    }

    @Override
    public DtoBrand update(int id,DtoBrandIU dtoBrandIU){

        Brand existingBrand = findBrandOrThrow(id);
        existingBrand.setName(dtoBrandIU.getName());
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
