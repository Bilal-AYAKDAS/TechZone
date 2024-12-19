package com.developerteam.techzone.business.concreates;

import com.developerteam.techzone.business.abstracts.IAuthService;
import com.developerteam.techzone.business.abstracts.IFavoriProductService;
import com.developerteam.techzone.dataAccess.abstracts.IFavoriProductRepository;
import com.developerteam.techzone.entities.concreates.FavoriProduct;
import com.developerteam.techzone.entities.concreates.Product;
import com.developerteam.techzone.entities.concreates.User;
import com.developerteam.techzone.entities.dto.DtoFavoriProduct;
import com.developerteam.techzone.entities.dto.DtoFavoriProductIU;
import com.developerteam.techzone.exception.ErrorMessage;
import com.developerteam.techzone.exception.MessageType;
import com.developerteam.techzone.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FavoriProductManager implements IFavoriProductService {

    @Autowired
    private IFavoriProductRepository favoriProductRepository;

    @Autowired
    private IAuthService authService;

    public User findUserOrThrow() {
        Optional<User> optionalUser = authService.getAuthenticatedUser();
        if (optionalUser.isEmpty()){
            throw new UserNotFoundException(
                    new ErrorMessage(MessageType.USER_NOT_FOUND, "User does not exist.")
            );
        }
        return optionalUser.get();
    }

    @Override
    public List<DtoFavoriProduct> getAll() {

        List<FavoriProduct> favoriProductList = favoriProductRepository.findByUser(findUserOrThrow());

        if (favoriProductList.isEmpty()){
            return new ArrayList<>();
        }

        List<DtoFavoriProduct> dtoFavoriProductList = new ArrayList<>();

        for (FavoriProduct favoriProduct : favoriProductList){
            DtoFavoriProduct dtoFavoriProduct = new DtoFavoriProduct();
            dtoFavoriProduct.setId(favoriProduct.getId());
            dtoFavoriProduct.setProduct(favoriProduct.getProduct());
            dtoFavoriProductList.add(dtoFavoriProduct);
        }

        return dtoFavoriProductList;
    }

    @Override
    public DtoFavoriProduct add(DtoFavoriProductIU dtoFavoriProductIU) {
        User user = findUserOrThrow();
        FavoriProduct favoriProduct = new FavoriProduct();
        favoriProduct.setUser(user);
        Product product = new Product();
        product.setId(dtoFavoriProductIU.getProductId());
        favoriProduct.setProduct(product);
        FavoriProduct favoriProductSaved = favoriProductRepository.save(favoriProduct);
        DtoFavoriProduct dtoFavoriProduct = new DtoFavoriProduct();
        dtoFavoriProduct.setId(favoriProductSaved.getId());
        dtoFavoriProduct.setProduct(favoriProductSaved.getProduct());
        return dtoFavoriProduct;
    }

    @Override
    public void delete(int id) {
        User user = findUserOrThrow();
        FavoriProduct favoriProduct = favoriProductRepository.findById(id);
        if (favoriProduct.getUser().getId() != user.getId()){
            return;
        }
        favoriProductRepository.deleteById(id);
    }
}
