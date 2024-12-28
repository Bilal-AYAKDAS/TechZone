package com.developerteam.techzone.business.abstracts;

import com.developerteam.techzone.entities.concreates.Product;
import com.developerteam.techzone.entities.dto.DtoProduct;
import com.developerteam.techzone.entities.dto.DtoProductIU;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductService {

    List<DtoProduct> getAll();
    DtoProduct getById(int id);
    List<DtoProduct> getByCategoryId(int categoryId);
    List<DtoProduct> getByBrandId(int brandId);
    List<DtoProduct> getByCategoryIdAndBrandId(int categoryId,int brandId);
    List<DtoProduct> getByProductName(String productName);
    DtoProduct add(DtoProductIU dtoProductIU, MultipartFile multipartFile);
    DtoProduct update(int id,DtoProductIU dtoProductIU,MultipartFile multipartFile);
    void delete(int id);


}
