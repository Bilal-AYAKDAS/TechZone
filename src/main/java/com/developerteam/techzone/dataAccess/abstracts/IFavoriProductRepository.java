package com.developerteam.techzone.dataAccess.abstracts;

import com.developerteam.techzone.entities.concreates.FavoriProduct;
import com.developerteam.techzone.entities.concreates.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFavoriProductRepository extends JpaRepository<FavoriProduct, Integer> {

    FavoriProduct findById(int id);

    List<FavoriProduct> findByUser(User user);
}
