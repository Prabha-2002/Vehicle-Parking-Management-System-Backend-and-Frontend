package com.example.parkingmanagement.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.parkingmanagement.model.User;
import com.example.parkingmanagement.repo.UserRepository;
import com.example.parkingmanagement.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User getUserById(Long userId) {
		return userRepository.findById(userId);
	}

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public User updateUser(Long userId, User user) {
		if (userRepository.existsById(userId)) {
			user.setId(userId);
			return userRepository.save(user);
		}
		return null;
	}

	@Override
	public void deleteUser(Long userId) {
		User user = userRepository.findById(userId);
		if (user != null) {
			userRepository.delete(user);
		}
	}

	@Override
	public User authenticateUser(String username, String password) {
		User user = userRepository.findByUsername(username);
		if (user != null && user.getPassword().equals(password)) {
			return user;
		}
		return null;
	}

	@Override
	public User getUserById(User user) {
		return userRepository.save(user);
	}
	
	
	
}
