package com.developerteam.techzone.dataAccess.abstracts;

import com.developerteam.techzone.entities.concreates.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBrandRepository extends JpaRepository <Brand,Integer>{
}
