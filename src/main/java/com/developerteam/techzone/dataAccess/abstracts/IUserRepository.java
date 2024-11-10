package com.developerteam.techzone.dataAccess.abstracts;

import com.developerteam.techzone.entities.concreates.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User,Integer> {

    User findByEmail(String email);

    User findByEmailAndPassword(String email, String password);
}
