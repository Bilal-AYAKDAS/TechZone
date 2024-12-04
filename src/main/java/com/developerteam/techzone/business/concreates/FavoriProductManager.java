package com.developerteam.techzone.business.concreates;

import com.developerteam.techzone.business.abstracts.IFavoriProductService;
import com.developerteam.techzone.dataAccess.abstracts.IFavoriProductRepository;
import com.developerteam.techzone.entities.concreates.FavoriProduct;
import com.developerteam.techzone.entities.concreates.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriProductManager implements IFavoriProductService {

    private IFavoriProductRepository favoriProductRepository;

    @Autowired
    public FavoriProductManager(IFavoriProductRepository favoriProductRepository) {
        this.favoriProductRepository = favoriProductRepository;
    }

    @Override
    public List<FavoriProduct> getAll() {
        return this.favoriProductRepository.findAll();
    }

    @Override
    public FavoriProduct getById(int id) {
        return this.favoriProductRepository.findById(id);
    }

    @Override
    public List<FavoriProduct> getByUser(User user) {
        return this.favoriProductRepository.findByUser(user);
    }

    @Override
    public FavoriProduct add(FavoriProduct favoriProduct) {
        return this.favoriProductRepository.save(favoriProduct);
    }

    @Override
    public void delete(int id) {
        this.favoriProductRepository.deleteById(id);
    }
}
