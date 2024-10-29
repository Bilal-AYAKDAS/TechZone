package com.developerteam.techzone.dataAccess.abstracts;

import com.developerteam.techzone.entities.concreates.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category,Integer> {
}
