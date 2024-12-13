package com.developerteam.techzone.dataAccess.abstracts;

import com.developerteam.techzone.entities.concreates.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
    User findByFirstName(String firstName);
    User findByEmailAndPassword(String email, String password);
}
