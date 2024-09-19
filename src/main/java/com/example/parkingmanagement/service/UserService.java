package com.example.parkingmanagement.service;

import com.example.parkingmanagement.model.User;

public interface UserService {
	User getUserById(User user);

	User saveUser(User user);

	void deleteUser(Long userId);

	User authenticateUser(String username, String password);

	User getUserById(Long userId);

	User updateUser(Long userId, User user);

	
}
