package com.example.parkingmanagement.repo;

import com.example.parkingmanagement.model.User;

public interface UserRepository {
    User findById(Long userId);
    User save(User user);
    void delete(User user);
    User findByUsername(String username); 
	boolean existsById(Long userId);

}
