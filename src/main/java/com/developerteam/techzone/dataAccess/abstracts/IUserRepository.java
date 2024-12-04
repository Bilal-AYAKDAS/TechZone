package com.developerteam.techzone.dataAccess.abstracts;

import com.developerteam.techzone.entities.concreates.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User,Integer> {

    User findByEmail(String email);

<<<<<<< HEAD
=======
    User findByFirstName(String firstName);
    
>>>>>>> fa31834 (feat: Implement DTO objects and exception handling architecture)
    User findByEmailAndPassword(String email, String password);
}
