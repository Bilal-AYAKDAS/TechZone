package com.developerteam.techzone.dataAccess.abstracts;

import com.developerteam.techzone.entities.concreates.UserAdress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserAdressRepository extends JpaRepository<UserAdress, Integer> {

    List<UserAdress> findByUserId(int userId);
}
