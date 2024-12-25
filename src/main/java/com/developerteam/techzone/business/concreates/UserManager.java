package com.developerteam.techzone.business.concreates;

import com.developerteam.techzone.business.abstracts.IUserService;
import com.developerteam.techzone.dataAccess.abstracts.IUserRepository;
import com.developerteam.techzone.entities.concreates.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserManager implements IUserService {

    private IUserRepository userRepository;

    @Override
    public List<User> getAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User getById(int id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Override
    public User getByEmail(String email) {
        return this.userRepository.findByEmail(email).get();
    }

    @Override
    public User getByEmailAndPassword(String email, String password) {
        return this.userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User add(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public User update(int id, User user) {
        Optional <User> existingUser = this.userRepository.findById(id);
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setEmail(user.getEmail());
            updatedUser.setPassword(user.getPassword());
            updatedUser.setFirstName(user.getFirstName());
            updatedUser.setLastName(user.getLastName());
            updatedUser.setPhoneNumber(user.getPhoneNumber());
            return this.userRepository.save(updatedUser);
        }
        return null;
    }

    @Override
    public void delete(int id) {
        this.userRepository.deleteById(id);
    }
}
