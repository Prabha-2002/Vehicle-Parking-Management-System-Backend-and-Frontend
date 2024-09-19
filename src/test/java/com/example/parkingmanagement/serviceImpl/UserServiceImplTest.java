package com.example.parkingmanagement.serviceImpl;

import com.example.parkingmanagement.model.User;
import com.example.parkingmanagement.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserById() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(user);

        User foundUser = userService.getUserById(userId);
        assertEquals(userId, foundUser.getId());
    }

    @Test
    void testSaveUser() {
        User user = new User();
        user.setId(1L);
        
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.saveUser(user);
        assertNotNull(savedUser);
        assertEquals(user.getId(), savedUser.getId());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUserWhenExists() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        
        when(userRepository.existsById(userId)).thenReturn(true);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = userService.updateUser(userId, user);
        assertNotNull(updatedUser);
        assertEquals(userId, updatedUser.getId());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUserWhenNotExists() {
        Long userId = 1L;
        User user = new User();
        
        when(userRepository.existsById(userId)).thenReturn(false);

        User updatedUser = userService.updateUser(userId, user);
        assertNull(updatedUser);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        
        when(userRepository.findById(userId)).thenReturn(user);

        userService.deleteUser(userId);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void testDeleteUserWhenNotFound() {
        Long userId = 1L;
        
        when(userRepository.findById(userId)).thenReturn(null);

        userService.deleteUser(userId);
        verify(userRepository, never()).delete(any(User.class));
    }

    @Test
    void testAuthenticateUserSuccessful() {
        String username = "testUser";
        String password = "testPassword";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        
        when(userRepository.findByUsername(username)).thenReturn(user);

        User authenticatedUser = userService.authenticateUser(username, password);
        assertNotNull(authenticatedUser);
        assertEquals(username, authenticatedUser.getUsername());
    }

    @Test
    void testAuthenticateUserFailed() {
        String username = "testUser";
        String password = "wrongPassword";
        User user = new User();
        user.setUsername(username);
        user.setPassword("testPassword");

        when(userRepository.findByUsername(username)).thenReturn(user);

        User authenticatedUser = userService.authenticateUser(username, password);
        assertNull(authenticatedUser);
    }
}
